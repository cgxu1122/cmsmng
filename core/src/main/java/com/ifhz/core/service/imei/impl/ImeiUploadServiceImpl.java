package com.ifhz.core.service.imei.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.base.commons.codec.CodecUtils;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.commons.log.DeviceCommonLog;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.api.ApiUploadService;
import com.ifhz.core.service.api.bean.ImeiStatus;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.service.imei.ImeiUploadService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.Date;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

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
    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;
    @Resource(name = "localDirCacheService")
    private LocalDirCacheService localDirCacheService;

    public Map<ImeiStatus, Integer> processCsvData(String filePath, Long channelId, Date processDate) {
        LOGGER.info("解析CSV文件:{}--------------------开始", filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(filePath)));
            String line = "";
            int i = 1;
            while ((line = reader.readLine()) != null) {
                LOGGER.info("解析CSV文件 line={}", line);
                try {
                    if (StringUtils.isNotBlank(line)) {
                        LOGGER.info("process [{}] line={}", (i++), line);
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
            return null;
        } finally {
            IOUtils.closeQuietly(reader);
            LOGGER.info("解析CSV文件:{}--------------------结束", filePath);
        }

        return null;
    }


    private String decodeFile(String filePath) {
        String path = null;
        try {
            File file = new File(filePath);
            String temp = FileUtils.readFileToString(file, "UTF-8");
            LOGGER.info("decode msg={}", temp);
            LOGGER.info("process encode msg={}", temp);
            String source = CodecUtils.decode(temp).trim();
            LOGGER.info("process decode msg={}", source);
            String localFileName = localDirCacheService.getLocalFileName(file.getName());
            InputStream inputStream = new ByteArrayInputStream(source.getBytes());
            path = localDirCacheService.storeTempFile(inputStream, localFileName);
        } catch (Exception e) {
            LOGGER.error("decodeFile error", e);
        }

        return path;
    }

    @Override
    public Map<ImeiStatus, Integer> processZipData(String filePath, Long channelId, Date processDate) {
        BufferedOutputStream bos = null;
        ZipEntry entry = null;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipFile zipfile = new ZipFile(filePath);

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                } else {
                    InputStreamReader is = new InputStreamReader(zipfile.getInputStream(entry));
                    BufferedReader br = new BufferedReader(is);
                    String con = null;
                    while ((con = br.readLine()) != null) {
                        System.out.println(entry.getName());
                        System.out.println(con);
                    }
                }
            }
            zis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void asyncProcessCsvData(String filePath, Long channelId, Date processDate) {
        if (StringUtils.isNotBlank(filePath)) {
            taskExecutor.execute(new AsyncProcessCsvTask(filePath, channelId, processDate));
        }
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
                    Date processTime = DateFormatUtils.parse(processTimeStamp, "yyyyMMdd");
                    result.setProcessTime(processTime);
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


    private class AsyncProcessCsvTask implements Runnable {

        private String csvFilePath;
        private Long channelId;
        private Date processDate;

        private AsyncProcessCsvTask(String csvFilePath, Long channelId, Date processDate) {
            this.csvFilePath = csvFilePath;
            this.channelId = channelId;
            this.processDate = processDate;
        }

        @Override
        public void run() {
            Map<ImeiStatus, Integer> map = processCsvData(csvFilePath, channelId, processDate);
            LOGGER.info("map={}", JSON.toJSONString(map));
        }
    }
}
