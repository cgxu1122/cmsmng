package com.ifhz.core.service.device.impl;

import com.ifhz.core.adapter.DeviceInfoAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DeviceInfo;
import com.ifhz.core.po.DeviceSwitch;
import com.ifhz.core.service.device.DeviceInfoService;
import com.ifhz.core.service.stat.DeviceSwitchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service("deviceInfoService")
public class DeviceInfoServiceImpl implements DeviceInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceInfoServiceImpl.class);

    @Resource(name = "deviceInfoAdapter")
    private DeviceInfoAdapter deviceInfoAdapter;
    @Resource
    private DeviceSwitchService deviceSwitchService;


    @Override
    @Log
    public DeviceInfo getById(Long id) {
        return deviceInfoAdapter.getById(id);
    }

    @Override
    @Log
    public List<DeviceInfo> queryByVo(Pagination page, DeviceInfo record) {
        return deviceInfoAdapter.queryByVo(page, record);
    }

    @Override
    @Log
    public int insert(DeviceInfo record) {
        try {
            DeviceSwitch bean = new DeviceSwitch();
            bean.setDeviceCode(record.getDeviceCode());
            bean.setStatus(0);
            deviceSwitchService.insert(bean);
        } catch (Exception e) {
            LOGGER.error("insert DeviceSwitch error", e);
        }
        return deviceInfoAdapter.insert(record);
    }

    @Override
    @Log
    public int update(DeviceInfo record) {
        return deviceInfoAdapter.update(record);
    }

    @Override
    @Log
    public int delete(DeviceInfo record) {
        try {
            deviceSwitchService.delete(record.getDeviceCode());
        } catch (Exception e) {
            LOGGER.error("delete DeviceSwitch error", e);
        }
        return deviceInfoAdapter.delete(record);
    }

    @Override
    @Log
    public DeviceInfo queryByDeviceCode(String deviceCode) {
        return deviceInfoAdapter.queryByDeviceCode(deviceCode);
    }
}
