package com.ifhz.core.adapter;

import com.ifhz.core.po.CounterFailLog;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:13
 */
public interface CounterFailLogAdapter {

    public void insert(CounterFailLog po);

    public long queryTotalCount(Date startTime, Date endTime);

    public List<CounterFailLog> queryPage(int pageSize, int pageNum, Date startTime, Date endTime);
}
