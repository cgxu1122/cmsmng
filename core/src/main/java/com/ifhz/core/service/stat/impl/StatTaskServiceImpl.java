package com.ifhz.core.service.stat.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.ifhz.core.adapter.BatchProductRefAdapter;
import com.ifhz.core.adapter.ChannelInfoAdapter;
import com.ifhz.core.adapter.DataLogAdapter;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.enums.GroupType;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.po.ProductStat;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.stat.DataLogQueryService;
import com.ifhz.core.service.stat.LogStatUpdateService;
import com.ifhz.core.service.stat.ProductStatUpdateService;
import com.ifhz.core.service.stat.StatTaskService;
import com.ifhz.core.service.stat.bean.DataLogRequest;
import com.ifhz.core.service.stat.handle.DateHandler;
import com.ifhz.core.service.stat.handle.StatConvertHandler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 17:15
 */
@Service("statTaskService")
public class StatTaskServiceImpl implements StatTaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatTaskServiceImpl.class);

    @Resource(name = "logStatUpdateService")
    private LogStatUpdateService logStatUpdateService;
    @Resource(name = "productStatUpdateService")
    private ProductStatUpdateService productStatUpdateService;
    @Resource(name = "splitTableService")
    private SplitTableService splitTableService;
    @Resource(name = "dataLogAdapter")
    private DataLogAdapter dataLogAdapter;
    @Resource(name = "channelInfoAdapter")
    private ChannelInfoAdapter channelInfoAdapter;
    @Resource(name = "dataLogQueryService")
    private DataLogQueryService dataLogQueryService;
    @Resource(name = "batchProductRefAdapter")
    private BatchProductRefAdapter batchProductRefAdapter;


    /**
     * 统计[startTime,endTime]内流水数据表，不允许跨天
     *
     * @param startTime 开始时间 不允许跨天
     * @param endTime   结束时间 不允许跨天
     */
    public void scanDataLog(Date startTime, Date endTime) {
        LOGGER.info("统计程序开始执行{},{}", DateFormatUtils.formatDate(startTime, "yyyy-MM-dd HH:mm:ss:sss"), DateFormatUtils.formatDate(endTime, "yyyy-MM-dd HH:mm:ss:sss"));
        Map<String, LogStat> logStatHashMap = Maps.newHashMap();
        Map<String, ProductStat> productStatHashMap = Maps.newHashMap();
        String tableName = splitTableService.getCurrentTableName(startTime);
        DataLogRequest dataLogRequest = new DataLogRequest();
        dataLogRequest.setTableName(tableName);
        dataLogRequest.setDate(startTime);
        dataLogRequest.setStartTime(startTime);
        dataLogRequest.setEndTime(endTime);
        dataLogRequest.setStatSwitch(true);

        long totalCount = dataLogAdapter.queryTotalCountForDevice(dataLogRequest);
        LOGGER.info("数据总量为{}", totalCount);
        if (totalCount > 0) {
            long pageNum = StatConvertHandler.getPageNum(totalCount, GlobalConstants.PAGE_SIZE);
            LOGGER.info("数据总量为{},分页值为{}", totalCount, pageNum);
            for (int i = 0; i < pageNum; i++) {
                Pagination page = new Pagination();
                page.setPageSize(GlobalConstants.PAGE_SIZE);
                page.setCurrentPage(i + 1);
                List<DataLog> dataLogList = dataLogAdapter.queryPageForDevice(page, dataLogRequest);
                if (CollectionUtils.isNotEmpty(dataLogList)) {
                    LOGGER.info("第{}次查询统计开始", i + 1);
                    for (DataLog dataLog : dataLogList) {
                        {//流水统计
                            String logMd5Key = StatConvertHandler.getMd5KeyForLogStat(dataLog);
                            String logStatMd5Key = StatConvertHandler.getMd5KeyByLogDataForLogStat(dataLog);
                            LogStat logStat = getLogStatFromMap(logStatHashMap, logMd5Key, dataLog);
                            logStat.setMd5Key(logMd5Key);
                            logStat.setDataLogMd5Key(logStatMd5Key);

                            logStat.setDevicePrsDayNum(logStat.getDeviceUpdDayNum() + 1);
                            //重新放入容器中
                            logStatHashMap.put(logMd5Key, logStat);
                        }
                        {
                            //产品统计
                            if (StringUtils.isNotBlank(dataLog.getBatchCode())) {
                                List<Long> productIdList = batchProductRefAdapter.queryProductIdList(dataLog.getBatchCode());
                                if (CollectionUtils.isNotEmpty(productIdList)) {
                                    for (Long productId : productIdList) {
                                        String productMd5Key = StatConvertHandler.getMd5KeyForProductStat(dataLog, productId);
                                        String productStatMd5Key = StatConvertHandler.getMd5KeyByLogDataForProductStat(dataLog);
                                        ProductStat productStat = getProductStatFromMap(productStatHashMap, productMd5Key, dataLog, productId);
                                        productStat.setMd5Key(productMd5Key);
                                        productStat.setDataLogPmd5Key(productStatMd5Key);
                                        productStat.setProductPrsDayNum(productStat.getProductPrsDayNum() + 1);
                                        //重新放入容器中
                                        productStatHashMap.put(productMd5Key, productStat);
                                    }
                                }
                            }
                        }
                    }
                    LOGGER.info("第{}次查询统计完成", i + 1);
                    //统计完成 保存入库
                    LOGGER.info("第{}次查询统计流水数据保存入库开始", i + 1);
                    saveLogStatMap(logStatHashMap, startTime);
                    LOGGER.info("第{}次查询统计流水数据保存入库结束", i + 1);
                    LOGGER.info("第{}次查询统计产品数据保存入库开始", i + 1);
                    saveProductStatMap(productStatHashMap, startTime);
                    LOGGER.info("第{}次查询统计产品数据保存入库结束", i + 1);
                }
            }
        }
    }

    private LogStat getLogStatFromMap(Map<String, LogStat> container, String md5Key, DataLog dataLog) {
        if (container.containsKey(md5Key)) {
            return container.get(md5Key);
        } else {
            return StatConvertHandler.initDataStat(dataLog);
        }
    }

    private ProductStat getProductStatFromMap(Map<String, ProductStat> container, String md5Key, DataLog dataLog, Long productId) {
        if (container.containsKey(md5Key)) {
            return container.get(md5Key);
        } else {
            return StatConvertHandler.initProductStat(dataLog, productId);
        }
    }


    public void saveLogStatMap(Map<String, LogStat> param, Date date) {
        if (MapUtils.isNotEmpty(param)) {
            for (Map.Entry<String, LogStat> entry : param.entrySet()) {
                try {
                    //保存前先查询库里是否有
                    String md5Key = entry.getKey();
                    LogStat value = entry.getValue();
                    LogStat entity = logStatUpdateService.getByMd5Key(md5Key);
                    LOGGER.info("统计数据为:{}，数据库统计数据为:{}", JSON.toJSONString(value), JSON.toJSONString(entity));
                    Date startTime = DateHandler.getStartTime(value.getProcessDate());
                    Date endTime = DateHandler.getEndTime(value.getProcessDate());
                    //是否需要再次查询 每天固定的数据
                    boolean isQueryCount = isQueryCount(value.getProcessDate(), date);
                    long deviceUpdDayNum = 0L;
                    long counterUpdDayNum = 0L;
                    if (isQueryCount) {
                        DataLogRequest dataLogRequest = new DataLogRequest();
                        dataLogRequest.setDate(value.getProcessDate());
                        dataLogRequest.setMd5Key(value.getDataLogMd5Key());
                        dataLogRequest.setStartTime(startTime);
                        dataLogRequest.setEndTime(endTime);
                        //加工日期 累计上传数量   日期过期后，数值固定
                        deviceUpdDayNum = dataLogQueryService.queryDeviceUpdDayNum(dataLogRequest);
                        LOGGER.info("{} -- {} 的加工设备累计上传数量为{}", value.getDataLogMd5Key(), value.getMd5Key(), deviceUpdDayNum);
                        //加工日期 计数器上传数量 日期过期后，数值固定
                        counterUpdDayNum = dataLogQueryService.queryCounterUpdDayNum(dataLogRequest);
                        LOGGER.info("{} -- {} 的计数器累计上传数量为{}", value.getDataLogMd5Key(), value.getMd5Key(), counterUpdDayNum);
                    }
                    if (entity != null) {//数据库有数据则更新
                        entity.setDevicePrsDayNum(entity.getDevicePrsDayNum() + value.getDevicePrsDayNum());
                        entity.setDeviceUpdDayNum(deviceUpdDayNum);
                        entity.setCounterUpdDayNum(counterUpdDayNum);

                        logStatUpdateService.update(entity);
                    } else {//数据库无数据则插入
                        if (value.getGroupId() == GroupType.DB.VALUE || value.getGroupId() == GroupType.LW.VALUE) {
                            ChannelInfo channelInfo = channelInfoAdapter.getById(value.getChannelId());
                            if (channelInfo != null && channelInfo.getLaowuId() != null) {
                                value.setLaowuId(channelInfo.getLaowuId());
                            }
                        }
                        value.setDeviceUpdDayNum(deviceUpdDayNum);
                        value.setCounterUpdDayNum(counterUpdDayNum);

                        logStatUpdateService.insert(value);
                    }
                } catch (Exception e) {
                    LOGGER.error("LogStat update error", e);
                }
            }
        }
    }


    public void saveProductStatMap(Map<String, ProductStat> param, Date date) {
        if (MapUtils.isNotEmpty(param)) {
            for (Map.Entry<String, ProductStat> entry : param.entrySet()) {
                try {
                    //保存前先查询库里是否有
                    String md5Key = entry.getKey();
                    ProductStat value = entry.getValue();
                    ProductStat entity = productStatUpdateService.getByMd5Key(md5Key);
                    LOGGER.info("统计数据为:{}，数据库统计数据为:{}", JSON.toJSONString(value), JSON.toJSONString(entity));
                    Date startTime = DateHandler.getStartTime(value.getProcessDate());
                    Date endTime = DateHandler.getEndTime(value.getProcessDate());
                    //是否需要再次查询 每天固定的数据
                    boolean isQueryCount = isQueryCount(value.getProcessDate(), date);
                    long productUpdDayNum = 0L;
                    long counterProductDayNum = 0L;
                    if (isQueryCount) {
                        DataLogRequest request = new DataLogRequest();
                        request.setStartTime(value.getProcessDate());
                        request.setStartTime(startTime);
                        request.setDate(startTime);
                        request.setEndTime(endTime);
                        request.setMd5Key(value.getDataLogPmd5Key());
                        request.setProductSwitch(true);

                        productUpdDayNum = dataLogQueryService.queryProductUpdDayNum(request);
                        LOGGER.info("{} -- {} 的加工设备累计上传数量为{}", value.getDataLogPmd5Key(), value.getMd5Key(), productUpdDayNum);
                        counterProductDayNum = dataLogQueryService.queryCounterProductDayNum(request);
                        LOGGER.info("{} -- {} 的计数器累计上传数量为{}", value.getDataLogPmd5Key(), value.getMd5Key(), counterProductDayNum);
                    }
                    if (entity != null) {//数据库有数据则更新
                        entity.setProductPrsDayNum(entity.getProductPrsDayNum() + value.getProductPrsDayNum());
                        entity.setProductUpdDayNum(productUpdDayNum);
                        entity.setCounterProductDayNum(counterProductDayNum);

                        productStatUpdateService.update(entity);
                    } else {//数据库无数据则插入
                        value.setProductUpdDayNum(productUpdDayNum);
                        value.setCounterProductDayNum(counterProductDayNum);

                        productStatUpdateService.insert(value);
                    }
                } catch (Exception e) {
                    LOGGER.error("ProductStat update error", e);
                }
            }
        }
    }

    private boolean isQueryCount(Date processDate, Date startTime) {
        Date date = DateHandler.getEndTime(processDate);
        long d = date.getTime();
        long s = startTime.getTime();
        if (s - d > 61 * 60 * 1000) {
            return false;
        }

        return true;
    }


    //加工日期
    //判断此加工数据 同时计数器数据也已经上传
                        /*if (dataLog.getActive() != null && dataLog.getCounterUploadTime() != null) {
                            logStat.setPrsActiveTotalNum(logStat.getPrsActiveTotalNum() + 1);
                            boolean isInvalid = false;
                            if (dataLog.getActive() == CounterActive.Valid.value) {
                                logStat.setPrsActiveValidNum(logStat.getPrsActiveValidNum() + 1);
                            } else if (dataLog.getActive() == CounterActive.Invalid_Replace.value) {
                                isInvalid = true;
                                logStat.setPrsActiveInvalidNum(logStat.getPrsActiveInvalidNum() + 1);
                            } else if (dataLog.getActive() == CounterActive.Invalid_Uninstall.value) {
                                isInvalid = true;
                                logStat.setPrsInvalidUninstallNum(logStat.getPrsInvalidUninstallNum() + 1);
                            }
                            if (isInvalid) {
                                logStat.setPrsActiveInvalidNum(logStat.getPrsActiveInvalidNum() + 1);
                            }
                        }*/
}
