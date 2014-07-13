package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.CounterTempLogAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.CounterTempLogMapper;
import com.ifhz.core.po.CounterTempLog;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/20
 * Time: 20:31
 */
@Repository("counterTempLogAdapter")
public class CounterTempLogAdapterImpl implements CounterTempLogAdapter {

    @Resource(name = "counterTempLogMapper")
    private CounterTempLogMapper counterTempLogMapper;

    @Override
    public int insert(CounterTempLog po) {
        return counterTempLogMapper.insert(po);
    }

    @Override
    public int update(String imei, Integer type) {
        return counterTempLogMapper.update(imei, type);
    }

    @Override
    public CounterTempLog queryByImei(String imei) {
        return counterTempLogMapper.queryByImei(imei);
    }

    @Override
    public long queryTotalCount(Date startTime, Date endTime, Integer type) {
        return counterTempLogMapper.queryTotalCount(startTime, endTime, type);
    }

    @Override
    public List<CounterTempLog> queryPage(Pagination page, Date startTime, Date endTime, Integer type) {
        List<CounterTempLog> result = counterTempLogMapper.queryPage(page, startTime, endTime, type);
        return result == null ? Lists.<CounterTempLog>newArrayList() : result;
    }

    @Override
    public void batchDelete(Date startTime, Date endTime) {
        counterTempLogMapper.batchDelete(startTime, endTime);
    }
}
