package com.ifhz.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.ifhz.api.constants.ResultType;
import com.ifhz.api.utils.ApiJsonHandler;
import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.base.commons.codec.CodecUtils;
import com.ifhz.core.base.commons.log.DeviceCommonLog;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.api.ApiUploadService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

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

    @RequestMapping(value = "/processLog.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject processLog(@RequestParam(value = "id", required = true) String id,
                          @RequestParam(value = "data", required = true) String data) {
        LOGGER.info("receive request id={},data={}", id, data);
        JSONObject result = null;
        try {
            if (StringUtils.isNotEmpty(data)) {
                List<DataLog> dataLogList = Lists.newArrayList();
                List<String> processList = JSON.parseArray(data, String.class);
                if (CollectionUtils.isNotEmpty(processList)) {
                    for (String processLog : processList) {
                        LOGGER.info("receive encode processLog={}", processLog);
                        if (StringUtils.isNotBlank(processLog)) {
                            //解码 手机imei|手机ua|渠道id|加工设备编码|批次号|手机加工时间戳
                            String source = CodecUtils.decode(processLog).trim();
                            LOGGER.info("receive decode processLog={}", source);
                            String[] array = StringUtils.split(source, "|");
                            DataLog dataLog = translateDataLog(array);
                            if (dataLog != null) {
                                dataLogList.add(dataLog);
                            }

                        }
                    }
                    if (CollectionUtils.isNotEmpty(dataLogList)) {
                        apiUploadService.batchSave(dataLogList);
                        result = ApiJsonHandler.genJsonRet(ResultType.SuccNonUpgrade);
                        result.put("id", id);
                    }
                }
            }

            if (result == null) {
                result = ApiJsonHandler.genJsonRet(ResultType.Fail);
                result.put("id", id);
            }
        } catch (Exception e) {
            result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            result.put("id", id);
            LOGGER.error("processLog error ", e);
        } finally {
            LOGGER.info("processLog:returnObj={}", result);
        }

        return result;
    }


    @RequestMapping(value = "/processFile.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject processFile(@RequestParam(value = "dataFile", required = true) MultipartFile dataFile) {
        LOGGER.info("receive request dataFile={}", JSON.toJSONString(dataFile));
        JSONObject result = null;
        if (dataFile == null) {
            result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            return result;
        } else if (dataFile.isEmpty()) {
            result = ApiJsonHandler.genJsonRet(ResultType.SuccNonUpgrade);
            return result;
        }
        String originFileName = dataFile.getOriginalFilename();
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append(MapConfig.getString(GlobalConstants.KEY_LOCAL_STORE_DIR, GlobalConstants.GLOBAL_CONFIG, "/data/app"));
            buffer.append(File.separator);
            buffer.append(new Date().getTime());
            String localDirPath = buffer.toString();
            File localDir = new File(localDirPath);
            if (!localDir.exists()) {
                localDir.mkdirs();
            }
            buffer.append(File.separator);
            buffer.append(originFileName);
            File localFile = new File(buffer.toString());
            ByteStreams.copy(dataFile.getInputStream(), Files.newOutputStreamSupplier(localFile));
            LOGGER.info("用户上传Imei安装文件fileName={},保存到本地成功,路径为{}", localFile.getAbsolutePath());

            //TODO

            if (result == null) {
                result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            }
        } catch (Exception e) {
            result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            LOGGER.error("processLog error ", e);
        } finally {
            LOGGER.info("processLog:returnObj={}", result);
        }

        return result;
    }


    private DataLog translateDataLog(String[] data) {
        //手机imei|手机ua|渠道id|加工设备编码|批次号|手机加工时间戳
        DataLog result = null;
        if (data != null && data.length == 6) {
            try {
                result = new DataLog();
                result.setImei(StringUtils.trimToEmpty(data[0]));
                result.setUa(StringUtils.trimToEmpty(data[1]));
                String channelIdStr = StringUtils.trimToEmpty(data[2]);
                if (StringUtils.isNotBlank(channelIdStr)) {
                    result.setChannelId(Long.parseLong(channelIdStr));
                }

                result.setDeviceCode(StringUtils.trimToEmpty(data[3]));
                result.setBatchCode(StringUtils.trimToEmpty(data[4]));

                String processTimeStamp = StringUtils.trimToEmpty(data[5]);
                if (StringUtils.isNotBlank(processTimeStamp)) {
                    result.setProcessTime(new Date(Long.parseLong(processTimeStamp)));
                }
                result.setDeviceUploadTime(new Date());
            } catch (Exception e) {
                DeviceCommonLog.info("{}", JSON.toJSONString(data));
                LOGGER.error("translateDataLog error ", e);
                return null;
            }
        } else {
            LOGGER.info("数据校验不通过：{}", JSON.toJSONString(data));
        }

        return result;
    }
}
