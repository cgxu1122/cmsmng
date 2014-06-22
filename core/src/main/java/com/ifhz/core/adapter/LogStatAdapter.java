package com.ifhz.core.adapter;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.service.api.bean.StatUpdateBean;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 11:46
 */
public interface LogStatAdapter {

    public int insert(LogStat record);

    public int update(LogStat record);

    public LogStat getById(Long id);

    public LogStat getByMd5Key(String md5Key);

    public int updateStat(StatUpdateBean statUpdateBean);

    public List<LogStat> queryByVO(Pagination page, LogStat record);
}
