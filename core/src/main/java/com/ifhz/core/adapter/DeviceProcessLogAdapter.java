package com.ifhz.core.adapter;

import com.ifhz.core.po.DeviceProcessLog;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:14
 */
public interface DeviceProcessLogAdapter {

    public void insert(String tableName, DeviceProcessLog po);

    public DeviceProcessLog getByImei(String tableName, String imei);
}
