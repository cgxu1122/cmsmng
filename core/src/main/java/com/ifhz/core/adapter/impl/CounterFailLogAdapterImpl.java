package com.ifhz.core.adapter.impl;

import com.ifhz.core.adapter.CounterFailLogAdapter;
import com.ifhz.core.mapper.CounterFailLogMapper;
import com.ifhz.core.po.CounterFailLog;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 13:07
 */
@Repository("counterFailLogAdapter")
public class CounterFailLogAdapterImpl implements CounterFailLogAdapter {

    @Resource(name = "counterFailLogMapper")
    private CounterFailLogMapper counterFailLogMapper;

    @Override
    public void insert(CounterFailLog po) {
        counterFailLogMapper.insert(po);
    }

    @Override
    public long queryTotalCount(Date startTime, Date endTime) {
        return 0;
    }

    @Override
    public List<CounterFailLog> queryPage(int pageSize, int pageNum, Date startTime, Date endTime) {
        return null;
    }
}
