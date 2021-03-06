package com.ifhz.core.service.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.LogArriveStatTemp;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public interface LogArriveStatTempService {

    public int insert(LogArriveStatTemp logArriveStatTemp);

    public boolean syncLogArriveStat(Date startTime, Date endTime);

    public List<LogArriveStatTemp> queryByVo(Pagination pagination, LogArriveStatTemp record);

    public List<LogArriveStatTemp> querySumByVo(Pagination pagination, LogArriveStatTemp record);

    public LogArriveStatTemp queryCountByVo(LogArriveStatTemp record);
}
