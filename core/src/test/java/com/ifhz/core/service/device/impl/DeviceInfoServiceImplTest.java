package com.ifhz.core.service.device.impl;

import com.ifhz.core.po.DeviceInfo;
import com.ifhz.core.service.device.DeviceInfoService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeviceInfoServiceImplTest extends BaseTest {

    @Autowired
    private DeviceInfoService deviceInfoService;

    @Test
    public void testGetById() throws Exception {
        log(deviceInfoService.getById(2L));
    }

    @Test
    public void testQueryByVo() throws Exception {
        DeviceInfo po = new DeviceInfo();
        po.setActive("Y");
        log(deviceInfoService.queryByVo(null, po));
    }

    @Test
    public void testInsert() throws Exception {
        DeviceInfo po = new DeviceInfo();
        po.setChannelId(2L);
        po.setDeviceCode("设备号1");
        po.setGroupId(1L);
        log(deviceInfoService.insert(po));
        log(po.getDeviceId());
    }

    @Test
    public void testUpdate() throws Exception {
        DeviceInfo po = new DeviceInfo();
        po.setDeviceId(2L);
        po.setChannelId(1L);
        po.setGroupId(1L);
        po.setDeviceCode("shebeihao1");
        deviceInfoService.update(po);
    }

    @Test
    public void testDelete() throws Exception {
        DeviceInfo po = new DeviceInfo();
        po.setDeviceId(2L);
        deviceInfoService.delete(po);
    }
}