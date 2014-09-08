package com.ifhz.core.adapter.stat.impl;

import com.ifhz.core.adapter.stat.LogArriveStatTempAdapter;
import com.ifhz.core.mapper.stat.LogArriveStatTempMapper;
import com.ifhz.core.po.stat.LogArriveStatTemp;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public class LogArriveStatTempAdapterImpl implements LogArriveStatTempAdapter {

    private LogArriveStatTempMapper logArriveStatTempMapper;

    @Override
    public int insert(LogArriveStatTemp record) {
        return logArriveStatTempMapper.insert(record);
    }

    @Override
    public LogArriveStatTemp getById(Long id) {
        return logArriveStatTempMapper.getById(id);
    }
}
