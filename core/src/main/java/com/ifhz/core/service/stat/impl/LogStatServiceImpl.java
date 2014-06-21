package com.ifhz.core.service.stat.impl;

import com.google.common.collect.Maps;
import com.ifhz.core.adapter.ChannelInfoAdapter;
import com.ifhz.core.adapter.DataLogAdapter;
import com.ifhz.core.adapter.LogStatAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.enums.GroupType;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.stat.LogStatService;
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
public class LogStatServiceImpl implements LogStatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogStatServiceImpl.class);

    @Resource(name = "logStatAdapter")
    private LogStatAdapter logStatAdapter;
    @Resource(name = "splitTableService")
    private SplitTableService splitTableService;
    @Resource(name = "dataLogAdapter")
    private DataLogAdapter dataLogAdapter;
    @Resource(name = "channelInfoAdapter")
    private ChannelInfoAdapter channelInfoAdapter;

    public final int pageSize = 10000;


    /**
     * 统计[startTime,endTime]内流水数据表，不允许跨天
     *
     * @param startTime 开始时间 不允许跨天
     * @param endTime   结束时间 不允许跨天
     */
    public void scanDataLogForDevice(Date startTime, Date endTime) {
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
                        LogStat logStat;
                        if (container.containsKey(md5Key)) {
                            logStat = container.get(md5Key);
                        } else {
                            logStat = StatConvertHandler.initDataStat(dataLog);
                        }
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

                }
            }
        }
    }


    public void saveStatMap(Map<String, LogStat> param) {
        if (MapUtils.isNotEmpty(param)) {
            for (Map.Entry<String, LogStat> entry : param.entrySet()) {
                //保存前先查询库里是否有
                String md5Key = entry.getKey();
                LogStat value = entry.getValue();
                LogStat entity = logStatAdapter.getByMd5Key(md5Key);

                if (entity != null) {
                    entity.setDevicePrsDayNum(entity.getDevicePrsDayNum() + value.getDevicePrsDayNum());
                    entity.setPrsActiveTotalNum(entity.getPrsActiveTotalNum() + value.getPrsActiveTotalNum());
                    entity.setPrsActiveValidNum(entity.getPrsActiveValidNum() + value.getPrsActiveValidNum());
                    entity.setPrsInvalidReplaceNum(entity.getPrsInvalidReplaceNum() + value.getPrsInvalidReplaceNum());
                    entity.setPrsInvalidUninstallNum(entity.getPrsInvalidUninstallNum() + value.getPrsInvalidUninstallNum());

                    //TODO 更新操作
                } else {
                    entity = value;
                    if (entity.getGroupId() == GroupType.DB.VALUE || entity.getGroupId() == GroupType.LW.VALUE) {
                        ChannelInfo channelInfo = channelInfoAdapter.getById(value.getChannelId());
                        if (channelInfo != null && channelInfo.getLaowuId() != null) {
                            entity.setLaowuId(channelInfo.getLaowuId());
                        }
                    }
                }

                Date startTime = DateHandler.getStartTime(entity.getProcessDate());
                Date endTime = DateHandler.getEndTime(entity.getProcessDate());


            }
        }
    }


}
