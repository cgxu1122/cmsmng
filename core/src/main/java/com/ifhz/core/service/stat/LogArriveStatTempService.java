package com.ifhz.core.service.stat;

import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.stat.LogArriveStat;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public interface LogArriveStatTempService {

    public boolean statTempLogArrive(DataLog record);


    public boolean asyncData(LogArriveStat record, boolean isInsert);
}
