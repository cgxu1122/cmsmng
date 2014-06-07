package com.ifhz.core.mapper;

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
public interface CounterUploadLogMapper {

    public void insert(CounterUploadLog po);

    public CounterUploadLog getByImei(@Param("tableName") String tableName, @Param("imei") String imei);

    public List<CounterUploadLog> getCounterUploadLog(Map m);

    public long getYesterdayLogCount(Map tableName);

    public long getLogCountByTime(Map m);

    public List<Map<String, Object>> queryArriveImeiByPage(Pagination page, @Param(value = "record") Map record);

    public List<Map<String, Object>> queryArriveImei(Map record);

    public List<Map<String, Object>> queryUploadImeiByPage(Pagination page, @Param(value = "record") Map record);

    public List<Map<String, Object>> queryUploadImei(Map record);
}
