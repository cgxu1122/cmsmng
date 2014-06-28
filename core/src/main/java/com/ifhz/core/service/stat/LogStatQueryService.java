package com.ifhz.core.service.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.LogStat;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 17:16
 */
public interface LogStatQueryService {
    public List<LogStat> queryByVo(Pagination page, LogStat record);

    public LogStat queryCountByVo(LogStat record);
}
