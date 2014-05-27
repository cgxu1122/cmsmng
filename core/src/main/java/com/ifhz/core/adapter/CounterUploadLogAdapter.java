package com.ifhz.core.adapter;

import com.ifhz.core.po.CounterUploadLog;

import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:13
 */
public interface CounterUploadLogAdapter {

    public void insert(String tableName, CounterUploadLog po);

    public CounterUploadLog getByImei(String tableName, String imei);

    public List<CounterUploadLog> getCounterUploadLog(Map m);

    public long getYesterdayLogCount(Map tableName);

    public long getLogCountByTime(Map m);
}
