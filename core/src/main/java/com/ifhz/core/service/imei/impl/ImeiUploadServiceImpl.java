package com.ifhz.core.service.imei.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.ifhz.core.adapter.BatchInfoAdapter;
import com.ifhz.core.adapter.DeviceInfoAdapter;
import com.ifhz.core.adapter.ModelInfoAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.commons.codec.CodecUtils;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.commons.excel.ExcelHandle;
import com.ifhz.core.base.commons.log.DeviceCommonLog;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.api.ApiUploadService;
import com.ifhz.core.service.api.bean.ImeiStatus;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.service.imei.ImeiUploadService;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.List;
import java.util.Map;

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
    @Resource(name = "deviceInfoAdapter")
    private DeviceInfoAdapter deviceInfoAdapter;
    @Resource(name = "modelInfoAdapter")
    private ModelInfoAdapter modelInfoAdapter;
    @Resource(name = "batchInfoAdapter")
    private BatchInfoAdapter batchInfoAdapter;
    @Resource(name = "channelInfoCacheService")
    private ChannelInfoCacheService channelInfoCacheService;

    @Log
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
                        Splitter splitter = Splitter.on("|");
                        Iterable<String> iterable = splitter.split(line);
                        DataLog dataLog = translateDataLog(iterable);
                        if (dataLog != null) {
                            try {
                                apiUploadService.saveDeviceDataLog(dataLog);
                            } catch (Exception e) {
                                LOGGER.error("saveDeviceDataLog error", e);
                            }
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

    @Log
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
    @Log
    public Map<ImeiStatus, Integer> processImeiExcelData(String filePath, Long channelId, Date processDate) {
        Map<ImeiStatus, Integer> result = Maps.newHashMap();
        if (StringUtils.isBlank(filePath) || channelId == null || processDate == null) {
            return result;
        }
        try {
            List<DataLog> dataLogList = ExcelHandle.readImeiDataFromExcel(filePath);
            if (CollectionUtils.isNotEmpty(dataLogList)) {
                ChannelInfo channelInfo = channelInfoCacheService.getByChannelId(channelId);
                for (DataLog dataLog : dataLogList) {
                    ImeiStatus status;
                    try {
                        dataLog.setProcessTime(processDate);
                        dataLog.setChannelId(channelId);
                        dataLog.setGroupId(channelInfo.getGroupId());
                        dataLog.setDeviceUploadTime(new Date());
                        LOGGER.info("dataLog={}", JSON.toJSONString(dataLog));
                        if (validExcelData(dataLog)) {
                            status = apiUploadService.saveDeviceDataLog(dataLog);
                        } else {
                            status = ImeiStatus.Invalid;
                        }
                    } catch (Exception e) {
                        LOGGER.info("process Row ===>DataLog", e);
                        status = ImeiStatus.Failure;
                    }
                    if (status != null) {
                        if (result.containsKey(status)) {
                            Integer count = result.get(status);
                            result.put(status, count + 1);
                        } else {
                            result.put(status, 1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("processImeiExcelData error", e);
        }

        return result;
    }

    @Override
    @Log
    public void asyncProcessCsvData(String filePath, Long channelId, Date processDate) {
        if (StringUtils.isNotBlank(filePath)) {
            taskExecutor.execute(new AsyncProcessCsvTask(filePath, channelId, processDate));
        }
    }


    @Log
    private boolean validExcelData(DataLog dataLog) {
        //验证非法数据
        if (StringUtils.isBlank(dataLog.getImei())) {
            LOGGER.info("imei invalid");
            return false;
        }
        if (StringUtils.isBlank(dataLog.getUa())) {
            LOGGER.info("ua invalid");
            return false;
        }
        if (StringUtils.isBlank(dataLog.getBatchCode())) {
            LOGGER.info("batchCode invalid");
            return false;
        }
        if (StringUtils.isBlank(dataLog.getDeviceCode())) {
            LOGGER.info("deviceCode invalid");
            return false;
        }
        if (modelInfoAdapter.getByGroupIdAndUa(dataLog.getGroupId(), dataLog.getUa()) == null) {
            LOGGER.info("ModelInfo is null");
            return false;
        }
        if (batchInfoAdapter.queryByGroupIdAndBatchCode(dataLog.getGroupId(), dataLog.getBatchCode()) == null) {
            LOGGER.info("BatchInfo is null");
            return false;
        }
        if (deviceInfoAdapter.queryByDeviceCode(dataLog.getDeviceCode()) == null) {
            LOGGER.info("DeviceInfo is null");
            return false;
        }

        return true;
    }

    @Log
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
        @Log
        public void run() {
            Map<ImeiStatus, Integer> map = processCsvData(csvFilePath, channelId, processDate);
            LOGGER.info("map={}", JSON.toJSONString(map));
        }
    }
}
