package com.ifhz.core.service.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.LogArriveStat;
import com.ifhz.core.po.stat.LogArriveStatTemp;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public interface LogArriveStatTempService {

    public List<LogArriveStatTemp> queryByVo(Pagination pagination, LogArriveStatTemp record);

    public int insert(LogArriveStat logArriveStat);

    public boolean syncLogArriveStat();

}
