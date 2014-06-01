package com.ifhz.core.mapper;

import com.ifhz.core.po.DeviceProcessLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:14
 */
public interface DeviceProcessLogMapper {

    public void insert(DeviceProcessLog po);

    public DeviceProcessLog getByImei(@Param("tableName") String tableName, @Param("imei") String imei);

    public List<DeviceProcessLog> getDeviceProcessLog(Map m);

    public long getYesterdayLogCount(Map tableName);

    public long getLogCountByTime(Map m);

    public List<DeviceProcessLog> queryDeviceProcessLog(DeviceProcessLog dpl);
}
