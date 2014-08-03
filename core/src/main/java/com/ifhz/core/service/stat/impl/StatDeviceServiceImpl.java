package com.ifhz.core.service.stat.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.adapter.BatchProductRefAdapter;
import com.ifhz.core.adapter.LogStatAdapter;
import com.ifhz.core.adapter.ProductStatAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.enums.GroupType;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.po.ProductStat;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import com.ifhz.core.service.stat.DataLogQueryService;
import com.ifhz.core.service.stat.StatDeviceService;
import com.ifhz.core.service.stat.bean.DataLogRequest;
import com.ifhz.core.service.stat.handle.DateHandler;
import com.ifhz.core.service.stat.handle.StatConvertHandler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 12:10
 */
@Service("statDeviceService")
public class StatDeviceServiceImpl implements StatDeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatDeviceServiceImpl.class);

    @Resource(name = "batchProductRefAdapter")
    private BatchProductRefAdapter batchProductRefAdapter;
    @Resource(name = "logStatAdapter")
    private LogStatAdapter logStatAdapter;
    @Resource(name = "productStatAdapter")
    private ProductStatAdapter productStatAdapter;
    @Resource(name = "dataLogQueryService")
    private DataLogQueryService dataLogQueryService;
    @Resource(name = "channelInfoCacheService")
    private ChannelInfoCacheService channelInfoCacheService;

    @Override
    @Log
    public void updateStat(DataLog dataLog) {
        LOGGER.info("Device Stat ---------开始");
        long start = System.currentTimeMillis();
        Date now = new Date();
        if (dataLog != null) {
            String md5Key = StatConvertHandler.getMd5KeyForLogStat(dataLog);
            LOGGER.info("LogStat md5Key={}", md5Key);
            if (StringUtils.isNotBlank(md5Key)) {
                int logStatNum = 0;
                while (true) {
                    logStatNum++;
                    LogStat logStat = logStatAdapter.getByMd5Key(md5Key);
                    if (logStat == null) {
                        logStat = StatConvertHandler.initDataStat(dataLog);
                        logStat.setMd5Key(md5Key);
                        logStat.setDevicePrsDayNum(logStat.getDevicePrsDayNum() + 1);
                        if (isQueryCount(logStat.getCreateTime(), now)) {
                            DataLogRequest dataLogRequest = getDataLogRequestByLogStat(logStat);
                            //加工日期维度设备上传总数   日期过期后，数值固定
                            long deviceUpdDayNum = dataLogQueryService.queryDeviceUpdDayNum(dataLogRequest);
                            LOGGER.info("{}  的加工设备累计上传数量为：{}", logStat.getMd5Key(), deviceUpdDayNum);
                            if (deviceUpdDayNum > 0) {
                                logStat.setDeviceUpdDayNum(deviceUpdDayNum);
                            }
                        }
                        if (logStat.getGroupId() == GroupType.DB.VALUE || logStat.getGroupId() == GroupType.LW.VALUE) {
                            ChannelInfo channelInfo = null;
                            try {
                                channelInfo = channelInfoCacheService.getByChannelId(logStat.getChannelId());
                            } catch (Exception e) {
                                LOGGER.error("getByChannelId error", e);
                            }
                            if (channelInfo != null && channelInfo.getLaowuId() != null) {
                                logStat.setLaowuId(channelInfo.getLaowuId());
                            }
                        }
                        int num = logStatAdapter.insert(logStat);
                        if (num == 1) {
                            LOGGER.info("LogStat insert success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(dataLog));
                            break;
                        }
                        if (logStatNum == 10) {
                            LOGGER.info("LogStat insert failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(dataLog));
                            break;
                        }
                    } else {
                        logStat.setDevicePrsDayNum(logStat.getDevicePrsDayNum() + 1);
                        if (isQueryCount(logStat.getCreateTime(), now)) {
                            DataLogRequest dataLogRequest = getDataLogRequestByLogStat(logStat);
                            //加工日期维度设备上传总数   日期过期后，数值固定
                            long deviceUpdDayNum = dataLogQueryService.queryDeviceUpdDayNum(dataLogRequest);
                            LOGGER.info("{}  的加工设备累计上传数量为：{}", logStat.getMd5Key(), deviceUpdDayNum);
                            if (deviceUpdDayNum > 0) {
                                logStat.setDeviceUpdDayNum(deviceUpdDayNum);
                            }
                        }
                        int num = logStatAdapter.update(logStat);
                        if (num == 1) {
                            LOGGER.info("LogStat update success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(dataLog));
                            break;
                        }
                        if (logStatNum == 10) {
                            LOGGER.info("LogStat update failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(dataLog));
                            break;
                        }
                    }
                }
            }
            if (StringUtils.isNotBlank(dataLog.getBatchCode())) {
                List<Long> productIdList = batchProductRefAdapter.queryProductIdList(dataLog.getBatchCode());
                if (CollectionUtils.isNotEmpty(productIdList)) {
                    for (Long productId : productIdList) {
                        String productMd5Key = StatConvertHandler.getMd5KeyForProductStat(dataLog, productId);
                        if (StringUtils.isNotBlank(productMd5Key)) {
                            int productStatNum = 1;
                            while (true) {
                                productStatNum++;
                                ProductStat productStat = productStatAdapter.getByMd5Key(productMd5Key);
                                if (productStat == null) {
                                    productStat = StatConvertHandler.initProductStat(dataLog, productId);
                                    productStat.setMd5Key(productMd5Key);
                                    productStat.setProductPrsDayNum(productStat.getProductPrsDayNum() + 1);
                                    if (isQueryCount(productStat.getCreateTime(), now)) {
                                        DataLogRequest request = getDataLogRequestByProductStat(productStat);
                                        long productUpdDayNum = dataLogQueryService.queryProductUpdDayNum(request);
                                        LOGGER.info("{} 的加工设备累计上传数量为{}", productStat.getMd5Key(), productUpdDayNum);
                                        if (productUpdDayNum > 0) {
                                            productStat.setProductUpdDayNum(productUpdDayNum);
                                        }
                                        int num = productStatAdapter.insert(productStat);
                                        if (num == 1) {
                                            LOGGER.info("ProductStat insert success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(dataLog));
                                            break;
                                        }
                                        if (productStatNum == 10) {
                                            LOGGER.info("ProductStat insert failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(dataLog));
                                            break;
                                        }
                                    }
                                } else {
                                    productStat.setProductPrsDayNum(productStat.getProductPrsDayNum() + 1);
                                    if (isQueryCount(productStat.getCreateTime(), now)) {
                                        DataLogRequest request = getDataLogRequestByProductStat(productStat);
                                        long productUpdDayNum = dataLogQueryService.queryProductUpdDayNum(request);
                                        LOGGER.info("{} 的加工设备累计上传数量为{}", productStat.getMd5Key(), productUpdDayNum);
                                        if (productUpdDayNum > 0) {
                                            productStat.setProductUpdDayNum(productUpdDayNum);
                                        }
                                    }

                                    int num = productStatAdapter.update(productStat);
                                    if (num == 1) {
                                        LOGGER.info("ProductStat update success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(dataLog));
                                        break;
                                    }
                                    if (productStatNum == 10) {
                                        LOGGER.info("ProductStat update failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(dataLog));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        long end = System.currentTimeMillis();
        LOGGER.info("Device Stat ---------结束");
        LOGGER.info("Device Stat totalTime={} ---------结束", (end - start));
    }

    @Log
    private DataLogRequest getDataLogRequestByProductStat(ProductStat productStat) {
        DataLogRequest dataLogRequest = new DataLogRequest();
        dataLogRequest.setDate(productStat.getProcessDate());
        Date uploadStartTime = DateHandler.getStartTime(productStat.getProcessDate());
        Date uploadEndTime = DateHandler.getEndTime(productStat.getProcessDate());
        dataLogRequest.setUploadStartTime(uploadStartTime);
        dataLogRequest.setUploadEndTime(uploadEndTime);
        dataLogRequest.setUa(productStat.getUa());
        dataLogRequest.setProductId(productStat.getProductId());
        dataLogRequest.setGroupId(productStat.getGroupId());

        return dataLogRequest;
    }

    @Log
    private DataLogRequest getDataLogRequestByLogStat(LogStat logStat) {
        DataLogRequest dataLogRequest = new DataLogRequest();
        dataLogRequest.setDate(logStat.getProcessDate());
        Date uploadStartTime = DateHandler.getStartTime(logStat.getProcessDate());
        Date uploadEndTime = DateHandler.getEndTime(logStat.getProcessDate());
        dataLogRequest.setUploadStartTime(uploadStartTime);
        dataLogRequest.setUploadEndTime(uploadEndTime);
        dataLogRequest.setUa(logStat.getUa());
        dataLogRequest.setDeviceCode(logStat.getDeviceCode());
        dataLogRequest.setChannelId(logStat.getChannelId());

        return dataLogRequest;
    }

    @Log
    private boolean isQueryCount(Date createTime, Date now) {
        Date date = DateHandler.getEndTime(createTime);
        long d = date.getTime();
        long s = now.getTime();
        if (s - d > 10 * 60 * 1000) {
            return false;
        }

        return true;
    }

}
