package com.ifhz.core.adapter.stat;

import com.ifhz.core.po.stat.LogArriveStatTemp;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public interface LogArriveStatTempAdapter {

    public int insert(LogArriveStatTemp record);

    public LogArriveStatTemp getById(Long id);
}
