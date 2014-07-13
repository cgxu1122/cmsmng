package com.ifhz.core.adapter;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.CounterTempLog;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/20
 * Time: 20:30
 */
public interface CounterTempLogAdapter {

    public int insert(CounterTempLog po);

    public int update(String imei, Integer type);

    public CounterTempLog queryByImei(String imei);

    public long queryTotalCount(Date startTime, Date endTime, Integer type);

    public List<CounterTempLog> queryPage(Pagination page, Date startTime, Date endTime, Integer type);

    public void batchDelete(Date startTime, Date endTime);
}
