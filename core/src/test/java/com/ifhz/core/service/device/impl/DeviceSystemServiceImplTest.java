package com.ifhz.core.service.device.impl;

import com.ifhz.core.po.DeviceSystem;
import com.ifhz.core.service.device.DeviceSystemService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class DeviceSystemServiceImplTest extends BaseTest {

    @Autowired
    private DeviceSystemService deviceSystemService;

    @Test
    public void testGetById() throws Exception {
        log(deviceSystemService.getById(2L));
    }

    @Test
    public void testQueryByVo() throws Exception {
        DeviceSystem po = new DeviceSystem();
        log(deviceSystemService.queryByVo(null, po));
    }

    @Test
    public void testInsert() throws Exception {
        DeviceSystem po = new DeviceSystem();
        po.setEffectiveTime(new Date());
        po.setFtpPath("aaaa/bbb.txt");
        po.setVersion("1.1.1");
        log(deviceSystemService.insert(po));
        log("id:" + po.getSystemId());
    }

    @Test
    public void testUpdate() throws Exception {
        DeviceSystem po = new DeviceSystem();
        po.setSystemId(2L);
        po.setVersion("2.0.0");
        deviceSystemService.update(po);
    }

    @Test
    public void testDelete() throws Exception {
        DeviceSystem po = new DeviceSystem();
        po.setSystemId(2L);
        deviceSystemService.delete(po);
    }
}