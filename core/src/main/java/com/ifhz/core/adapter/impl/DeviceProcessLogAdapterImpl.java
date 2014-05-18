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
    public void insert(DeviceProcessLog po) {
        deviceProcessLogMapper.insert(po);
    }

    @Override
    public DeviceProcessLog getByImei(String imei) {
        if (StringUtils.isBlank(imei)) {
            return null;
        }
        return deviceProcessLogMapper.getByImei(imei);
    }
}
