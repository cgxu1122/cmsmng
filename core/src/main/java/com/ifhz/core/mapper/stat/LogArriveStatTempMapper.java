package com.ifhz.core.mapper.stat;

import com.ifhz.core.po.stat.LogArriveStatTemp;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public interface LogArriveStatTempMapper {

    public int insert(LogArriveStatTemp record);

    public LogArriveStatTemp getById(Long id);

}
