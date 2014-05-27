package com.ifhz.core.mapper;

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
public interface CounterUploadLogMapper {

    public void insert(CounterUploadLog po);

    public CounterUploadLog getByImei(@Param("tableName") String tableName, @Param("imei") String imei);

    public List<CounterUploadLog> getCounterUploadLog(Map m);

    public long getYesterdayLogCount(Map tableName);

    public long getLogCountByTime(Map m);
}
