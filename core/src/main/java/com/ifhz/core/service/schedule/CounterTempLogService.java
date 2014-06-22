package com.ifhz.core.service.schedule;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 16:14
 */
public interface CounterTempLogService {

    public void scanCounterTempLog(Date startTime, Date endTime);

}
