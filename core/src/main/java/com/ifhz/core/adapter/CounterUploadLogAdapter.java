package com.ifhz.core.adapter;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.CounterUploadLog;
import org.apache.ibatis.annotations.Param;

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

    public List<Map<String, Object>> queryArriveImeiByPage(Pagination page, Map record);

    public List<Map<String, Object>> queryArriveImei(@Param(value = "record") Map record);

    public List<Map<String, Object>> queryUploadImeiByPage(Pagination page, Map record);

    public List<Map<String, Object>> queryUploadImei(Map record);
}
