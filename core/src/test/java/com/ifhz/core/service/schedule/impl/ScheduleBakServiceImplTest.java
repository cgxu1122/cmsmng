package com.ifhz.core.service.schedule.impl;

import com.ifhz.core.service.schedule.ScheduleBakService;
import com.ifhz.core.service.stat.handle.DateHandler;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class ScheduleBakServiceImplTest extends BaseTest {

    @Autowired
    private ScheduleBakService scheduleBakService;

    @Test
    public void testScanCounterTempLog() throws Exception {

    }

    @Test
    public void testStatisticsData() throws Exception {
        long start = System.currentTimeMillis();
        DateHandler.getStartTime(new Date());
        scheduleBakService.statisticsData(DateHandler.getStartTime(new Date()), DateHandler.getEndTime(new Date()));
        long end = System.currentTimeMillis();
        System.out.println("totalTime:" + (end - start));
    }

    @Test
    public void testFetchWdjData() throws Exception {

    }
}