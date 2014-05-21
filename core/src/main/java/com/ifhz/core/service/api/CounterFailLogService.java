package com.ifhz.core.service.api;

import com.ifhz.core.po.CounterFailLog;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 16:14
 */
public interface CounterFailLogService {

    public void insert(CounterFailLog po);

    public long queryTotalCount(Date startTime, Date endTime);

    public List<CounterFailLog> queryPage(int pageSize, int pageNum, Date startTime, Date endTime);
}
