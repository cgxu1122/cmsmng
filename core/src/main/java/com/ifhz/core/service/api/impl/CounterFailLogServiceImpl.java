package com.ifhz.core.service.api.impl;

import com.ifhz.core.adapter.CounterFailLogAdapter;
import com.ifhz.core.po.CounterFailLog;
import com.ifhz.core.service.api.CounterFailLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 16:14
 */
@Service("counterFailLogService")
public class CounterFailLogServiceImpl implements CounterFailLogService {

    @Resource(name = "counterFailLogAdapter")
    private CounterFailLogAdapter counterFailLogAdapter;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void insert(CounterFailLog po) {
        counterFailLogAdapter.insert(po);
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
