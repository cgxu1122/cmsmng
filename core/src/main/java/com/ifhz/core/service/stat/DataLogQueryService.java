package com.ifhz.core.service.stat;

import com.ifhz.core.service.stat.bean.DataLogRequest;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 16:28
 */
public interface DataLogQueryService {

    public long queryDeviceUpdDayNum(DataLogRequest dataLogRequest);

    public long queryProductUpdDayNum(DataLogRequest dataLogRequest);

}
