package com.ifhz.core.service.device.impl;

import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.DeviceInfo;
import com.ifhz.core.service.channel.ChannelInfoService;
import com.ifhz.core.service.device.DeviceInfoService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

public class DeviceInfoServiceImplTest extends BaseTest {

    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private ChannelInfoService channelInfoService;

    @Test
    public void testGetById() throws Exception {
        log(deviceInfoService.getById(2L));
        long[] channels = new long[32];
        for (int i = 0; i < 32; i++) {
            channels[i] = i + 34L;
        }
        String str = "Device_";
        Random random = new Random();
        for (int i = 101; i < 500; i++) {
            String d = str + i;
            long channelId = channels[random.nextInt(32)];
            ChannelInfo channelInfo = channelInfoService.getById(channelId);
            DeviceInfo info = new DeviceInfo();
            info.setDeviceCode(d);
            info.setChannelId(channelInfo.getChannelId());
            info.setGroupId(channelInfo.getGroupId());

            deviceInfoService.insert(info);
        }

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