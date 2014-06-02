package com.ifhz.core.service.common.impl;

import com.ifhz.core.service.common.SplitTableService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class SplitTableServiceImplTest extends BaseTest {

    @Autowired
    private SplitTableService splitTableService;

    @Test
    public void testGetTableNameForDeviceByNow() throws Exception {
        String t = splitTableService.getTableNameForCounterByNow(new Date());
        log(t);
    }

    @Test
    public void testGetTableNameForCounterByNow() throws Exception {

    }

    @Test
    public void testGetTableListForDeviceByNow() throws Exception {
        List<String> t = splitTableService.getTableListForDeviceByNow(new Date());
        log(t);
    }

    @Test
    public void testGetTableListForCounterByNow() throws Exception {
        List<String> t = splitTableService.getTableListForDeviceByNow(new Date());
        log(t);
    }
}