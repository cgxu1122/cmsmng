package com.ifhz.core.service.stat;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 16:28
 */
public interface DataLogQueryService {


    public long queryDeviceUpdDayNum(Date date, Date startTime, Date endTime);


    public long queryCounterUpdDayNum(Date date, Date startTime, Date endTime);

}
