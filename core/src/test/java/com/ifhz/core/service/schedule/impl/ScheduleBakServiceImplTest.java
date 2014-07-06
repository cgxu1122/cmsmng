package com.ifhz.core.service.schedule.impl;

import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.constants.GlobalConstants;
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
        String str = "2014-07-07 03:18:05";
        Date startTime = DateFormatUtils.parse(str, GlobalConstants.DEFAULT_DATE_FORMAT);
        scheduleBakService.statisticsData(startTime, DateHandler.getEndTime(new Date()));
        long end = System.currentTimeMillis();
        System.out.println("totalTime:" + (end - start));
    }

    @Test
    public void testFetchWdjData() throws Exception {

    }
}