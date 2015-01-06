package com.ifhz.core.service.bugfix;

import com.ifhz.core.adapter.DataLogAdapter;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.service.stat.ProductInstallStatService;
import com.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

public class BugFixServiceTest extends BaseTest {

    @Resource
    private BugFixService bugFixService;
    @Resource
    private DataLogAdapter dataLogAdapter;
    @Resource
    private ProductInstallStatService productInstallStatService;

    @Test
    public void testDeleteProductInstallStat() throws Exception {
        Date startTime = DateFormatUtils.parse("2014-11-01 00:00:00:000", "yyyy-MM-dd HH:mm:ss:sss");
        Date endTime = DateFormatUtils.parse("2014-12-31 23:59:59:999", "yyyy-MM-dd HH:mm:ss:sss");

        bugFixService.deleteProductInstallStat(startTime, endTime);
    }

    @Test
    public void testReStatProductInstall() throws Exception {
        Date startTime = DateFormatUtils.parse("2014-11-01 00:00:00:000", "yyyy-MM-dd HH:mm:ss:sss");
        Date endTime = DateFormatUtils.parse("2014-12-31 23:59:59:999", "yyyy-MM-dd HH:mm:ss:sss");
        bugFixService.reStatProductInstall(startTime, endTime);
    }
}