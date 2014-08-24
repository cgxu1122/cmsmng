package com.ifhz.core.service.api.impl;

import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.api.ApiUploadService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class ApiUploadServiceImplTest extends BaseTest {

    @Autowired
    private ApiUploadService apiUploadService;

    @Test
    public void testSaveCounterDataLog() throws Exception {
        DataLog dataLog = new DataLog();
        dataLog.setImei("55");
        dataLog.setUa("SM-G3588V");
        dataLog.setActive(1);
        dataLog.setCounterUploadTime(new Date());
        boolean t = apiUploadService.saveCounterDataLog(dataLog);
        System.out.println(t);
    }

    @Test
    public void testSaveDeviceDataLog() throws Exception {

    }

    @Test
    public void testBatchSave() throws Exception {

    }
}