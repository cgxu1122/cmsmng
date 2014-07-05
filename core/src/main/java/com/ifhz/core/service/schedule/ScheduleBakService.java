package com.ifhz.core.service.schedule;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 11:19
 */
public interface ScheduleBakService {

    public void scanCounterTempLog(Date startTime, Date endTime);

    public void statisticsData(Date startTime, Date endTime);

    public void fetchWdjData(Date date);
}
