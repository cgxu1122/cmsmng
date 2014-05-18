package com.ifhz.core.adapter.impl;

import com.ifhz.core.adapter.DeviceProcessLogAdapter;
import com.ifhz.core.mapper.DeviceProcessLogMapper;
import com.ifhz.core.po.DeviceProcessLog;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:14
 */
@Repository("deviceProcessLogAdapter")
public class DeviceProcessLogAdapterImpl implements DeviceProcessLogAdapter {

    @Resource(name = "deviceProcessLogMapper")
    private DeviceProcessLogMapper deviceProcessLogMapper;

    @Override
    public void insert(String tableName, DeviceProcessLog po) {
        deviceProcessLogMapper.insert(tableName, po);
    }

    @Override
    public DeviceProcessLog getByImei(String tableName, String imei) {
        if (StringUtils.isBlank(imei)) {
            return null;
        }
        return deviceProcessLogMapper.getByImei(tableName, imei);
    }
}
