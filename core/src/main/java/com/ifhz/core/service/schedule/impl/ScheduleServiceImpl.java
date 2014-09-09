package com.ifhz.core.service.schedule.impl;

import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.service.cache.DictInfoCacheService;
import com.ifhz.core.service.schedule.CounterTempLogService;
import com.ifhz.core.service.schedule.ScheduleService;
import com.ifhz.core.service.stat.StatTaskService;
import com.ifhz.core.service.stat.handle.DateHandler;
import com.ifhz.core.service.wdj.WdjDataQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 11:20
 */
@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Resource(name = "wdjDataQueryService")
    private WdjDataQueryService wdjDataQueryService;
    @Resource(name = "counterTempLogService")
    private CounterTempLogService counterTempLogService;
    @Resource(name = "statTaskService")
    private StatTaskService statTaskService;
    @Resource(name = "dictInfoCacheService")
    private DictInfoCacheService dictInfoCacheService;

    @Override
    @Log
    public void scanCounterTempLogFoUnDo() {
        LOGGER.info("scanCounterTempLogFoUnDo ------------------------start");
        long start = System.currentTimeMillis();
        Date now = new Date();
        Date date = DateFormatUtils.addMonth(now, -3);
        Date startTime = DateHandler.getStartTime(date);
        Date endTime = DateHandler.getEndTime(now);
        try {
            counterTempLogService.scanCounterTempLogFoUnDo(startTime, endTime);
        } catch (Exception e) {
            LOGGER.error("scanCounterTempLogFoUnDo error", e);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("scanCounterTempLogFoUnDo --------------------------end");
        LOGGER.info("scanCounterTempLogFoUnDo totalTime={}", (end - start));
    }

    @Override
    @Log
    public void scanCounterTempLogFoUnStat() {
        LOGGER.info("scanCounterTempLogFoUnStat ------------------------start");
        long start = System.currentTimeMillis();
        Date now = new Date();
        Date date = DateFormatUtils.addMonth(now, -3);
        Date startTime = DateHandler.getStartTime(date);
        Date endTime = DateHandler.getEndTime(now);
        try {
            counterTempLogService.scanCounterTempLogFoUnStat(startTime, endTime);
        } catch (Exception e) {
            LOGGER.error("scanCounterTempLogFoUnStat error", e);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("scanCounterTempLogFoUnStat --------------------------end");
        LOGGER.info("scanCounterTempLogFoUnStat totalTime={}", (end - start));
    }


    /**
     * 统计 流水数据
     * 开始时间 当前时间向前推一个小时
     * 结束时间 开始时间+30分钟
     */
    @Override
    @Log
    public void statisticsData() {
        LOGGER.info("statisticsData ------------------------start");
        long start = System.currentTimeMillis();
        Date date = new Date();
        try {
            int interval = MapConfig.getInt(GlobalConstants.KEY_STAT_INTERVAL_MINUTE, GlobalConstants.GLOBAL_CONFIG, 30);
            date = DateFormatUtils.addMinute(date, -60);
            Date startTime = DateHandler.getStartTimeForMinute(date);
            date = DateFormatUtils.addMinute(date, interval);
            Date endTime = DateHandler.getStartTimeForMinute(date);

            if (isCrossDay(startTime, endTime)) {
                Date currentEndTime = DateHandler.getEndTime(startTime);
                statTaskService.scanDataLog(startTime, currentEndTime);
                Date nextStartTime = DateHandler.getStartTime(endTime);
                statTaskService.scanDataLog(nextStartTime, endTime);
            } else {
                statTaskService.scanDataLog(startTime, endTime);
            }
        } catch (Exception e) {
            LOGGER.error("statisticsData error", e);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("statisticsData totalTime={}", (end - start));
        LOGGER.info("statisticsData --------------------------end");
    }

    @Override
    @Log
    public void fetchWdjData() {
        LOGGER.info("fetch Wdj Data ------------------------start");
        long start = System.currentTimeMillis();
        Date date = DateFormatUtils.addDay(new Date(), -1);
        try {
            LOGGER.info("process Wdj Data date={}------------loading", DateFormatUtils.formatDate(date, GlobalConstants.DATE_FORMAT_DPT));
            wdjDataQueryService.queryDataList(date);
        } catch (Exception e) {
            LOGGER.error("fetchWdjData error", e);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("fetch Wdj Data  totalTime={}", (end - start));
        LOGGER.info("fetch Wdj Data --------------------------end");
    }

    @Log
    private boolean isCrossDay(Date startTime, Date endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        int startDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();
        calendar.setTime(endTime);
        int endDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (startDay != endDay) {
            return true;
        }

        return false;
    }


    @Override
    @Log
    public void deleteCounterTempLog() {
        LOGGER.info("deleteCounterTempLog ------------------------start");
        long start = System.currentTimeMillis();
        Date date = DateFormatUtils.addMonth(new Date(), -3);
        Date startTime = dictInfoCacheService.getSystemInitDate();
        Date endTime = DateHandler.getEndTime(date);
        try {
            counterTempLogService.batchDelete(startTime, endTime);
        } catch (Exception e) {
            LOGGER.error("scanCounterTempLog error", e);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("deleteCounterTempLog  totalTime={}", (end - start));
        LOGGER.info("deleteCounterTempLog --------------------------end");
    }

    @Override
    public void syncLogActiveTemp() {
        LOGGER.info("syncLogActiveTemp ------------------------start");
        Date date = DateFormatUtils.addDay(new Date(), -1);
        Date startTime = DateHandler.getStartTimeForMinute(date);
        Date endTime = DateHandler.getEndTime(date);
        LOGGER.info("syncLogActiveTemp ------------------------end");
    }

    @Override
    public void syncProductActiveTemp() {
        LOGGER.info("syncProductActiveTemp ------------------------start");
        Date date = DateFormatUtils.addDay(new Date(), -1);
        Date startTime = DateHandler.getStartTimeForMinute(date);
        Date endTime = DateHandler.getEndTime(date);
        LOGGER.info("syncProductActiveTemp ------------------------end");
    }

}
