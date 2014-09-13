package com.ifhz.core.adapter.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.LogArriveStatTemp;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public interface LogArriveStatTempAdapter {

    public int insert(LogArriveStatTemp record);

    public LogArriveStatTemp getById(Long id);

    public List<LogArriveStatTemp> queryByVo(Pagination pagination, LogArriveStatTemp record);

    public List<LogArriveStatTemp> querySumByVo(Pagination pagination, LogArriveStatTemp record);

    public LogArriveStatTemp queryCountByVo(LogArriveStatTemp record);
}
