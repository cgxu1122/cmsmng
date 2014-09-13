package com.ifhz.core.adapter.stat.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.stat.LogArriveStatTempAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.stat.LogArriveStatTempMapper;
import com.ifhz.core.po.stat.LogArriveStatTemp;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
@Repository("logArriveStatTempAdapter")
public class LogArriveStatTempAdapterImpl implements LogArriveStatTempAdapter {

    @Resource
    private LogArriveStatTempMapper logArriveStatTempMapper;

    @Override
    public int insert(LogArriveStatTemp record) {
        return logArriveStatTempMapper.insert(record);
    }

    @Override
    public LogArriveStatTemp getById(Long id) {
        return logArriveStatTempMapper.getById(id);
    }

    @Override
    public List<LogArriveStatTemp> queryByVo(Pagination pagination, LogArriveStatTemp record) {
        List<LogArriveStatTemp> result = logArriveStatTempMapper.queryByVo(pagination, record);
        return result == null ? Lists.<LogArriveStatTemp>newArrayList() : result;
    }

    @Override
    public List<LogArriveStatTemp> querySumByVo(Pagination pagination, LogArriveStatTemp record) {
        List<LogArriveStatTemp> result = logArriveStatTempMapper.queryByVo(pagination, record);
        return result == null ? Lists.<LogArriveStatTemp>newArrayList() : result;
    }

    @Override
    public LogArriveStatTemp queryCountByVo(LogArriveStatTemp record) {
        return logArriveStatTempMapper.queryCountByVo(record);
    }
}
