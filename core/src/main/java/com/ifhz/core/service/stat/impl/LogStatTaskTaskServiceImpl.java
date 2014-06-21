package com.ifhz.core.service.stat.impl;

import com.google.common.collect.Maps;
import com.ifhz.core.adapter.ChannelInfoAdapter;
import com.ifhz.core.adapter.DataLogAdapter;
import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.enums.GroupType;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.stat.DataLogQueryService;
import com.ifhz.core.service.stat.LogStatTaskService;
import com.ifhz.core.service.stat.LogStatUpdateService;
import com.ifhz.core.service.stat.constants.CounterActive;
import com.ifhz.core.service.stat.handle.DateHandler;
import com.ifhz.core.service.stat.handle.StatConvertHandler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
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
@Service("logStatService")
public class LogStatTaskTaskServiceImpl implements LogStatTaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogStatTaskTaskServiceImpl.class);

    @Resource(name = "logStatUpdateService")
    private LogStatUpdateService logStatUpdateService;
    @Resource(name = "splitTableService")
    private SplitTableService splitTableService;
    @Resource(name = "dataLogAdapter")
    private DataLogAdapter dataLogAdapter;
    @Resource(name = "channelInfoAdapter")
    private ChannelInfoAdapter channelInfoAdapter;
    @Resource(name = "dataLogQueryService")
    private DataLogQueryService dataLogQueryService;
    //统计时分页处理条目，默认10000条
    public final int pageSize = MapConfig.getInt("stat.pageSize", GlobalConstants.GLOBAL_CONFIG, 10000);


    /**
     * 统计[startTime,endTime]内流水数据表，不允许跨天
     *
     * @param startTime 开始时间 不允许跨天
     * @param endTime   结束时间 不允许跨天
     */
    public void scanDataLog(Date startTime, Date endTime) {
        LOGGER.info("统计程序开始执行{},{}", startTime, endTime);
        Map<String, LogStat> container = Maps.newHashMap();
        String tableName = splitTableService.getCurrentTableName(startTime);
        long totalCount = dataLogAdapter.queryTotalCountForDevice(tableName, startTime, endTime);
        if (totalCount > 0) {
            long pageNum = StatConvertHandler.getPageNum(totalCount, pageSize);
            for (int i = 0; i < pageNum; i++) {
                Pagination page = new Pagination();
                page.setPageSize(pageSize);
                page.setCurrentPage(i + 1);
                List<DataLog> dataLogList = dataLogAdapter.queryPageForDevice(page, tableName, startTime, endTime);
                if (CollectionUtils.isNotEmpty(dataLogList)) {
                    for (DataLog dataLog : dataLogList) {
                        String md5Key = StatConvertHandler.getMd5KeyForLogStat(dataLog);
                        LogStat logStat = getLogStatFromMap(container, md5Key, dataLog);
                        logStat.setMd5Key(md5Key);
                        logStat.setDevicePrsDayNum(logStat.getDeviceUpdDayNum() + 1);
                        //判断此加工数据 同时计数器数据也已经上传
                        if (dataLog.getActive() != null && dataLog.getCounterUploadTime() != null) {
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
                        }
                        //重新放入容器中
                        container.put(md5Key, logStat);
                    }
                    //统计完成 保存入库
                    saveStatMap(container);
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


    public void saveStatMap(Map<String, LogStat> param) {
        if (MapUtils.isNotEmpty(param)) {
            for (Map.Entry<String, LogStat> entry : param.entrySet()) {
                try {
                    //保存前先查询库里是否有
                    String md5Key = entry.getKey();
                    LogStat value = entry.getValue();
                    LogStat entity = logStatUpdateService.getByMd5Key(md5Key);
                    Date startTime = DateHandler.getStartTime(entity.getProcessDate());
                    Date endTime = DateHandler.getEndTime(entity.getProcessDate());

                    if (entity != null) {//数据库有数据则更新
                        entity.setDevicePrsDayNum(entity.getDevicePrsDayNum() + value.getDevicePrsDayNum());
                        entity.setPrsActiveTotalNum(entity.getPrsActiveTotalNum() + value.getPrsActiveTotalNum());
                        entity.setPrsActiveValidNum(entity.getPrsActiveValidNum() + value.getPrsActiveValidNum());
                        entity.setPrsInvalidReplaceNum(entity.getPrsInvalidReplaceNum() + value.getPrsInvalidReplaceNum());
                        entity.setPrsInvalidUninstallNum(entity.getPrsInvalidUninstallNum() + value.getPrsInvalidUninstallNum());

                        long deviceUpdDayNum = dataLogQueryService.queryDeviceUpdDayNum(entity.getProcessDate(), startTime, endTime);
                        long counterUpdDayNum = dataLogQueryService.queryCounterUpdDayNum(entity.getProcessDate(), startTime, endTime);
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
                        long deviceUpdDayNum = dataLogQueryService.queryDeviceUpdDayNum(entity.getProcessDate(), startTime, endTime);
                        long counterUpdDayNum = dataLogQueryService.queryCounterUpdDayNum(entity.getProcessDate(), startTime, endTime);
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


}
