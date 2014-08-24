package com.ifhz.core.mapper.stat;

import com.ifhz.core.po.stat.LogArriveStat;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public interface LogArriveStatMapper {

    public int insert(LogArriveStat record);

    public int update(LogArriveStat record);

    public LogArriveStat getById(Long id);

    public LogArriveStat getByMd5Key(String md5Key);
}
