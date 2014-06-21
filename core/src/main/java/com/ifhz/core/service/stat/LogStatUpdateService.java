package com.ifhz.core.service.stat;

import com.ifhz.core.po.LogStat;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 23:26
 */
public interface LogStatUpdateService {


    public int insert(LogStat record);

    public int update(LogStat record);

    public LogStat getByMd5Key(String md5Key);
}
