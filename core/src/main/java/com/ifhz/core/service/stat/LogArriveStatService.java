package com.ifhz.core.service.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.stat.LogArriveStat;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public interface LogArriveStatService {

    public boolean statLogArrive(DataLog record);

    public List<LogArriveStat> queryByVo(Pagination pagination, LogArriveStat record);

    public LogArriveStat queryCountByVo(LogArriveStat record);
}
