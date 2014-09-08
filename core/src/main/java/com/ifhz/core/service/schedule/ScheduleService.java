package com.ifhz.core.service.schedule;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 11:19
 */
public interface ScheduleService {

    public void scanCounterTempLogFoUnDo();

    public void scanCounterTempLogFoUnStat();

    public void statisticsData();

    public void fetchWdjData();

    public void deleteCounterTempLog();

    public void syncLogActiveTemp();

    public void syncProductActiveTemp();
}
