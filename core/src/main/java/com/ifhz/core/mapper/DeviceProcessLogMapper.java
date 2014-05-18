package com.ifhz.core.mapper;

import com.ifhz.core.po.DeviceProcessLog;
import org.apache.ibatis.annotations.Param;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:14
 */
public interface DeviceProcessLogMapper {

    public void insert(@Param("tableName") String tableName, DeviceProcessLog po);

    public DeviceProcessLog getByImei(@Param("tableName") String tableName, @Param("imei") String imei);
}
