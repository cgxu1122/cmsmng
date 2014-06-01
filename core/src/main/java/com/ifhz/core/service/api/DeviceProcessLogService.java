package com.ifhz.core.service.api;

import com.ifhz.core.po.DeviceProcessLog;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 16:28
 */
public interface DeviceProcessLogService {

    public void insert(DeviceProcessLog po);

    public DeviceProcessLog queryHasImei(String imei);

    public List<DeviceProcessLog> queryDeviceProcessLog(DeviceProcessLog dpl);
}
