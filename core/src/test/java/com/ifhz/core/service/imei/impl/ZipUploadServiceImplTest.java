package com.ifhz.core.service.imei.impl;

import com.ifhz.core.service.imei.ZipUploadService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class ZipUploadServiceImplTest extends BaseTest {

    @Autowired
    ZipUploadService zipUploadService;

    @Test
    public void testProcessFile() throws Exception {
        zipUploadService.processFile("D:\\log.zip", 2L, new Date());
    }
}