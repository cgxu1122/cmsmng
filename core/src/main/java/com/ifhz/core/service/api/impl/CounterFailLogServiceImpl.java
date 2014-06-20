package com.ifhz.core.service.api.impl;

import com.ifhz.core.adapter.CounterFailLogAdapter;
import com.ifhz.core.po.CounterFailLog;
import com.ifhz.core.service.api.CounterFailLogService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 16:14
 */
public class CounterFailLogServiceImpl implements CounterFailLogService {

    private CounterFailLogAdapter counterFailLogAdapter;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void insert(CounterFailLog po) {
        counterFailLogAdapter.insert(po);
    }

    @Override
    public CounterFailLog queryByImei(String imei) {
        return counterFailLogAdapter.queryByImei(imei);
    }

    @Override
    public long queryTotalCount(Date startTime, Date endTime) {
        return counterFailLogAdapter.queryTotalCount(startTime, endTime);
    }

    @Override
    public List<CounterFailLog> queryPage(int pageSize, int pageNum, Date startTime, Date endTime) {
        return null;
    }
}
