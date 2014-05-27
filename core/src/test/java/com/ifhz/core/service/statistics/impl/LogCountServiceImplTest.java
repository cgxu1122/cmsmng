package com.ifhz.core.service.statistics.impl;

import com.ifhz.core.service.statistics.LogCountService;
import com.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by lm on 14-5-20.
 */
public class LogCountServiceImplTest extends BaseTest {
    @Resource(name = "logCountService")
    private LogCountService logCountService;

    @Test
    public void testAutoCountLog() throws Exception {

        Date startDate = new Date();
        startDate.setDate(19);
        startDate.setHours(13);
        startDate.setMinutes(0);
        startDate.setSeconds(0);

        Date endDate = new Date();
        endDate.setDate(20);
        endDate.setHours(15);
        endDate.setMinutes(0);
        endDate.setSeconds(0);
        logCountService.countLogByDate(startDate, endDate);

    }


}
