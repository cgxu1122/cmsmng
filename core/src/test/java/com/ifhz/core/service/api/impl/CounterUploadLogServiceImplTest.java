package com.ifhz.core.service.api.impl;

import com.ifhz.core.po.CounterUploadLog;
import com.ifhz.core.service.api.CounterUploadLogService;
import com.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

public class CounterUploadLogServiceImplTest extends BaseTest {

    @Resource(name = "counterUploadLogService")
    private CounterUploadLogService counterUploadLogService;

    @Test
    public void testInsert() throws Exception {
        CounterUploadLog log = new CounterUploadLog();
        log.setBatchCode("11111");
        log.setChannelId("2222");
        log.setGroupId(11111L);
        log.setUa("11111");
        log.setDeviceCode("11111");
        log.setCreateTime(new Date());
        log.setImei("11111");
        log.setProcessTime(new Date().getTime() + "");
        log.setActive("1");

        counterUploadLogService.insert(log);
        log(log);
    }

    @Test
    public void testQueryHasImei() throws Exception {

    }
}