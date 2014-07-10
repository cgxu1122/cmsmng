package com.ifhz.core.service.imei.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.base.commons.log.DeviceCommonLog;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.api.ApiUploadService;
import com.ifhz.core.service.imei.ImeiUploadService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/7
 * Time: 15:30
 */
@Service("imeiUploadService")
public class ImeiUploadServiceImpl implements ImeiUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImeiUploadServiceImpl.class);

    @Resource(name = "apiUploadService")
    private ApiUploadService apiUploadService;

    public boolean processCsvData(String filePath) {
        LOGGER.info("解析CSV文件:{}--------------------开始", filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(filePath)));
            String line = "";
            while ((line = reader.readLine()) != null) {
                LOGGER.info("解析CSV文件 line={}", line);
                try {
                    if (StringUtils.isNotBlank(line)) {
                        String[] data = StringUtils.split(line, "\\|");
                        DataLog dataLog = translateDataLog(data);
                        if (dataLog != null) {
                            apiUploadService.saveDeviceDataLog(dataLog);
                        } else {
                            LOGGER.info("数据转换失败：{}", line);
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("processCsvData_line", e);
                }
            }
        } catch (IOException e) {
            LOGGER.error("processCsvData error", e);
            return false;
        } finally {
            IOUtils.closeQuietly(reader);
            LOGGER.info("解析CSV文件:{}--------------------结束", filePath);
        }

        return true;
    }

    @Override
    public boolean processZipData(String filePath) {
        return false;
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
