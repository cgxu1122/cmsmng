package com.ifhz.core.service.device.impl;

import com.ifhz.core.adapter.DeviceInfoAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DeviceInfo;
import com.ifhz.core.service.device.DeviceInfoService;
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


    @Override
    public DeviceInfo getById(Long id) {
        return deviceInfoAdapter.getById(id);
    }

    @Override
    public List<DeviceInfo> queryByVo(Pagination page, DeviceInfo record) {
        return deviceInfoAdapter.queryByVo(page, record);
    }

    @Override
    public int insert(DeviceInfo record) {
        return deviceInfoAdapter.insert(record);
    }

    @Override
    public int update(DeviceInfo record) {
        return deviceInfoAdapter.update(record);
    }

    @Override
    public int delete(DeviceInfo record) {
        return deviceInfoAdapter.delete(record);
    }

    @Override
    public DeviceInfo queryByDeviceCode(String deviceCode) {
        return deviceInfoAdapter.queryByDeviceCode(deviceCode);
    }
}
