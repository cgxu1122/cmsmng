package com.ifhz.core.service.schedule.impl;

import com.ifhz.core.service.schedule.CounterTempLogService;
import com.ifhz.core.service.schedule.ScheduleBakService;
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
@Service("scheduleBakService")
public class ScheduleBakServiceImpl implements ScheduleBakService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleBakServiceImpl.class);

    @Resource(name = "wdjDataQueryService")
    private WdjDataQueryService wdjDataQueryService;
    @Resource(name = "counterTempLogService")
    private CounterTempLogService counterTempLogService;
    @Resource(name = "statTaskService")
    private StatTaskService statTaskService;

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
    public void scanCounterTempLogFoUnDo(Date startTime, Date endTime) {
        LOGGER.info("scanCounterTempLogFoUnDo startTime={},endTime={}------------------------start");
        try {
            Date stime = DateHandler.getStartTime(startTime);
            Date etime = DateHandler.getEndTime(endTime);
            counterTempLogService.scanCounterTempLogFoUnDo(stime, etime);
        } catch (Exception e) {
            LOGGER.error("scanCounterTempLogFoUnDo error", e);
        }
        LOGGER.info("scanCounterTempLogFoUnDo startTime={},endTime={}--------------------------end");
    }

    @Override
    public void scanCounterTempLogFoUnStat(Date startTime, Date endTime) {
        LOGGER.info("scanCounterTempLogFoUnStat startTime={},endTime={}------------------------start");
        try {
            Date stime = DateHandler.getStartTime(startTime);
            Date etime = DateHandler.getEndTime(endTime);
            counterTempLogService.scanCounterTempLogFoUnStat(stime, etime);
        } catch (Exception e) {
            LOGGER.error("scanCounterTempLogFoUnStat error", e);
        }
        LOGGER.info("scanCounterTempLogFoUnStat startTime={},endTime={}--------------------------end");
    }

    @Override
    public void statisticsData(Date startTime, Date endTime) {
        LOGGER.info("statisticsData startTime={},endTime={}------------------------start");
        try {
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
        LOGGER.info("statisticsData startTime={},endTime={}--------------------------end");
    }

    @Override
    public void fetchWdjData(Date date) {
        LOGGER.info("fetch Wdj Data date={}------------------------start");
        try {
            wdjDataQueryService.queryDataList(date);
        } catch (Exception e) {
            LOGGER.error("fetchWdjData error", e);
        }
        LOGGER.info("fetch Wdj Data date={}--------------------------end");
    }

    @Override
    public void resetStat(Date date) {

    }
}
