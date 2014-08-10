package com.ifhz.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.ifhz.api.constants.ResultType;
import com.ifhz.api.utils.ApiJsonHandler;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.commons.log.DeviceCommonLog;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.DeviceInfo;
import com.ifhz.core.service.api.ApiUploadService;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.service.device.DeviceInfoService;
import com.ifhz.core.service.imei.ImeiUploadService;
import com.ifhz.core.vo.DeviceRequestVo;
import com.ifhz.core.vo.DeviceResultVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/20
 * Time: 0:27
 */
@Controller
@RequestMapping("/nzyw/api")
public class DeviceApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceApiController.class);

    @Resource(name = "apiUploadService")
    private ApiUploadService apiUploadService;
    @Resource(name = "localDirCacheService")
    private LocalDirCacheService localDirCacheService;
    @Resource(name = "imeiUploadService")
    private ImeiUploadService imeiUploadService;
    @Resource(name = "deviceInfoService")
    private DeviceInfoService deviceInfoService;

    @RequestMapping(value = "/processData.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject processLog(@RequestParam(value = "data", required = true) String data) {
        LOGGER.info("receive request data={}", data);
        JSONObject result = null;
        try {
            if (StringUtils.isNotEmpty(data)) {
                Map<String, DataLog> dataLogMap = Maps.newHashMap();
                List<DeviceRequestVo> requestVoList = JSON.parseArray(data, DeviceRequestVo.class);
                if (CollectionUtils.isNotEmpty(requestVoList)) {
                    for (DeviceRequestVo requestVo : requestVoList) {
                        LOGGER.info("receive encode requestVo={}", JSON.toJSONString(requestVo));
                        if (requestVo != null && StringUtils.isNotBlank(requestVo.getContent())) {
                            //解码 手机imei|手机ua|渠道id|加工设备编码|批次号|手机加工时间戳
                            LOGGER.info("receive decode content={}", requestVo.getContent());
                            Splitter splitter = Splitter.on("|");
                            Iterable<String> iterable = splitter.split(requestVo.getContent().trim());

                            DataLog dataLog = translateDataLog(iterable);
                            if (dataLog != null) {
                                dataLogMap.put(requestVo.getId(), dataLog);
                            }
                        }
                    }
                    List<DeviceResultVo> dataList = apiUploadService.batchSave(dataLogMap);
                    result = ApiJsonHandler.genJsonRet(ResultType.SuccUpgrade);
                    result.put("dataList", dataList);
                }
            }

            if (result == null) {
                result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            }
        } catch (Exception e) {
            result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            LOGGER.error("processData error ", e);
        } finally {
            LOGGER.info("processData:returnObj={}", result);
        }

        return result;
    }


    @RequestMapping(value = "/processLog.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject processFile(HttpServletRequest request) {
        LOGGER.info("rev msg processFile-------start");
        JSONObject result = null;
        if (StringUtils.equalsIgnoreCase("application/octet-stream", request.getContentType())) {
            try {
                String file = request.getParameter("file");
                String deviceCode = request.getParameter("deviceCode");
                LOGGER.info("file={}", file);
                if (StringUtils.isBlank(file)) {
                    result = ApiJsonHandler.genJsonRet(ResultType.Fail);
                    return result;
                }
                String localFileName = localDirCacheService.getLocalFileName(file);
                if (StringUtils.isNotBlank(deviceCode)) {
                    DeviceInfo deviceInfo = deviceInfoService.queryByDeviceCode(deviceCode);
                    if (deviceInfo == null) {
                        LOGGER.info("deviceCode={}, 没有找到对应的仓库信息", deviceCode);
                    } else {
                        localFileName = String.valueOf(deviceInfo.getChannelId()) + "_" + localFileName;
                    }
                }

                LOGGER.info("localFileName={}", localFileName);
                String localFilePath = localDirCacheService.storeFile(request.getInputStream(), localFileName);
                LOGGER.info("localFilePath = {}", localFilePath);
                if (StringUtils.isNotBlank(localFilePath)) {
                    imeiUploadService.asyncProcessCsvData(localFilePath, null, null);
                } else {
                    LOGGER.info("processFile storeLocalFile failure!");
                }
                result = ApiJsonHandler.genJsonRet(ResultType.SuccUpgrade);
            } catch (Exception e) {
                result = ApiJsonHandler.genJsonRet(ResultType.Fail);
                LOGGER.error("processLog error ", e);
            } finally {
                LOGGER.info("processLog:returnObj={}", result);
            }
        }
        if (result == null) {
            result = ApiJsonHandler.genJsonRet(ResultType.Fail);
        }
        LOGGER.info("rev msg processFile-------end");

        return result;
    }


    private DataLog translateDataLog(Iterable<String> iterable) {
        //手机imei|手机ua|渠道id|加工设备编码|批次号|手机加工时间戳
        DataLog result = null;
        if (iterable != null) {
            try {
                result = new DataLog();
                int i = 0;
                for (String value : iterable) {
                    if (i == 0) {
                        result.setImei(StringUtils.trimToEmpty(value));
                    } else if (i == 1) {
                        result.setUa(StringUtils.trimToEmpty(value));
                    } else if (i == 2) {
                        String channelIdStr = StringUtils.trimToEmpty(value);
                        if (StringUtils.isNotBlank(channelIdStr)) {
                            result.setChannelId(Long.parseLong(channelIdStr));
                        }
                    } else if (i == 3) {
                        result.setDeviceCode(StringUtils.trimToEmpty(value));
                    } else if (i == 4) {
                        result.setBatchCode(StringUtils.trimToEmpty(value));
                    } else if (i == 5) {
                        String processTimeStamp = StringUtils.trimToEmpty(value);
                        if (StringUtils.isNotBlank(processTimeStamp)) {
                            Date processTime = DateFormatUtils.parse(processTimeStamp, "yyyyMMdd");
                            result.setProcessTime(processTime);
                        }
                    }
                    i++;
                }

                result.setDeviceUploadTime(new Date());
            } catch (Exception e) {
                DeviceCommonLog.info("{}", JSON.toJSONString(iterable));
                LOGGER.error("translateDataLog error ", e);
                return null;
            }
        } else {
            LOGGER.info("数据校验不通过：{}", JSON.toJSONString(iterable));
        }

        return result;
    }
}
