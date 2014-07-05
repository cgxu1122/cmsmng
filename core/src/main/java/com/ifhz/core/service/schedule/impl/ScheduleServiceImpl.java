package com.ifhz.core.service.schedule.impl;

import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.constants.GlobalConstants;
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

    @Override
    public void scanCounterTempLog() {
        LOGGER.info("scanCounterTempLog ------------------------start");
        Date date = DateFormatUtils.addDay(new Date(), -1);
        Date startTime = DateHandler.getStartTime(date);
        Date endTime = DateHandler.getEndTime(date);
        try {
            counterTempLogService.scanCounterTempLog(startTime, endTime);
        } catch (Exception e) {
            LOGGER.error("scanCounterTempLog error", e);
        }
        LOGGER.info("scanCounterTempLog --------------------------end");
    }


    /**
     * 统计 流水数据
     * 开始时间 当前时间向前推一个小时
     * 结束时间 开始时间+30分钟
     */
    @Override
    public void statisticsData() {
        LOGGER.info("statisticsData ------------------------start");
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
        LOGGER.info("statisticsData --------------------------end");
    }

    @Override
    public void fetchWdjData() {
        LOGGER.info("fetch Wdj Data ------------------------start");
        Date date = DateFormatUtils.addDay(new Date(), -1);
        try {
            wdjDataQueryService.queryDataList(date);
        } catch (Exception e) {
            LOGGER.error("fetchWdjData error", e);
        }
        LOGGER.info("fetch Wdj Data --------------------------end");
    }

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

}
