package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.stat.DeviceSwitchAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.po.DeviceSwitch;
import com.ifhz.core.service.stat.DeviceSwitchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/11/16
 * Time: 20:01
 */
@Service
public class DeviceSwitchServiceImpl implements DeviceSwitchService {

    @Resource
    private DeviceSwitchAdapter deviceSwitchAdapter;

    @Override
    @Log
    public int insert(DeviceSwitch record) {
        return deviceSwitchAdapter.insert(record);
    }

    @Override
    @Log
    public int update(DeviceSwitch record) {
        return deviceSwitchAdapter.update(record);
    }

    @Override
    @Log
    public int delete(String deviceCode) {
        return deviceSwitchAdapter.delete(deviceCode);
    }

    @Override
    @Log
    public DeviceSwitch get(String deviceCode) {
        return deviceSwitchAdapter.get(deviceCode);
    }

    @Override
    @Log
    public List<DeviceSwitch> queryByVo() {
        return deviceSwitchAdapter.queryByVo();
    }
}
