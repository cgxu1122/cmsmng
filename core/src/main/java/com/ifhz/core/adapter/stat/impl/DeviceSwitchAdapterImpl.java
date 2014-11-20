package com.ifhz.core.adapter.stat.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.stat.DeviceSwitchAdapter;
import com.ifhz.core.mapper.stat.DeviceSwitchMapper;
import com.ifhz.core.po.DeviceSwitch;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/11/16
 * Time: 20:01
 */
@Repository
public class DeviceSwitchAdapterImpl implements DeviceSwitchAdapter {

    @Resource
    private DeviceSwitchMapper deviceSwitchMapper;

    @Override
    public int insert(DeviceSwitch record) {
        return deviceSwitchMapper.insert(record);
    }

    @Override
    public int update(DeviceSwitch record) {
        return deviceSwitchMapper.update(record);
    }

    @Override
    public int delete(String deviceCode) {
        return deviceSwitchMapper.delete(deviceCode);
    }

    @Override
    public DeviceSwitch get(String deviceCode) {
        return deviceSwitchMapper.get(deviceCode);
    }

    @Override
    public List<DeviceSwitch> queryByVo() {
        List<DeviceSwitch> list = deviceSwitchMapper.queryByVo();
        return list == null ? Lists.<DeviceSwitch>newArrayList() : list;
    }
}
