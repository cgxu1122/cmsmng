package com.ifhz.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ifhz.api.constants.ResultType;
import com.ifhz.api.utils.ApiJsonHandler;
import com.ifhz.core.base.commons.codec.CodecUtils;
import com.ifhz.core.base.commons.log.DeviceCommonLog;
import com.ifhz.core.po.DeviceProcessLog;
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

import javax.annotation.Resource;
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
    JSONObject processLog(@RequestParam(value = "data", required = true) String data) {
        LOGGER.info("receive encode processList={}", data);
        DeviceCommonLog.info("receive encode processList={}", data);
        JSONObject result = null;
        try {
            if (StringUtils.isNotEmpty(data)) {
                List<DeviceProcessLog> deviceProcessLogList = Lists.newArrayList();
                List<String> processList = JSON.parseArray(data, String.class);
                if (CollectionUtils.isNotEmpty(processList)) {
                    for (String processLog : processList) {
                        LOGGER.info("receive encode processLog={}", processLog);
                        if (StringUtils.isNotBlank(processLog)) {
                            String source = CodecUtils.decode(processLog).trim();
                            LOGGER.info("receive decode processLog={}", source);
                            DeviceCommonLog.info("receive decode data={}", source);
                            String[] array = StringUtils.split(source, "|");
                            //^手机imei|手机ua|渠道id|加工设备编码|批次号|手机加工时间戳
                            if (array.length == 6 && valid(array)) {
                                DeviceProcessLog log = new DeviceProcessLog();
                                log.setImei(StringUtils.trimToEmpty(array[0]));
                                log.setUa(StringUtils.trimToEmpty(array[1]));
                                log.setChannelId(StringUtils.trimToEmpty(array[2]));
                                log.setDeviceCode(StringUtils.trimToEmpty(array[3]));
                                log.setBatchCode(StringUtils.trimToEmpty(array[4]));
                                log.setProcessTime(StringUtils.trimToEmpty(array[5]));

                                deviceProcessLogList.add(log);
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(deviceProcessLogList)) {
                        apiUploadService.batchSave(deviceProcessLogList);
                        result = ApiJsonHandler.genJsonRet(ResultType.SuccNonUpgrade);
                    }
                }
            }

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


    private boolean valid(String[] list) {
        for (int i = 0; i < list.length; i++) {
            if (StringUtils.trimToNull(list[0]) != null) {
                return true;
            }
        }

        return false;
    }

    /*private static String root_path = GlobalConstants.GLOBAL_CONFIG.get("root.path");
    private static String temp_path = GlobalConstants.GLOBAL_CONFIG.get("temp.path");

    @RequestMapping(value = "/processFile.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject processFile(@RequestParam(value = "dataFile", required = true) File dataFile,
                           HttpServletRequest request) {
        LOGGER.info("receive encode dataFile={}", dataFile.getName());
        JSONObject result = null;
        try {
            File root = new File(root_path);
            if (!root.exists()) {
                root.mkdirs();
            }
            File temp = new File(temp_path);
            if (!temp.exists()) {
                temp.mkdirs();
            }

            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(4096);
            factory.setRepository(temp);
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(100 * 1024 * 1024);
            List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
            Iterator<FileItem> iterator = items.iterator();
            while (iterator.hasNext()) {
                FileItem fi = iterator.next();
                String fileName = fi.getName();
                if (fileName != null) {
                    File fullFile = new File(fi.getName());
                    File savedFile = new File(root, fullFile.getName());
                    fi.write(savedFile);
                }
            }
            System.out.print("upload succeed");
        } catch (Exception e) {
            result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            LOGGER.error("processLog error ", e);
        } finally {
            LOGGER.info("processLog:returnObj={}", result);
        }

        return result;
    }*/
}
