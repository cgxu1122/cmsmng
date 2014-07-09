package com.ifhz.core.service.imei.impl;

import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.service.imei.StatImeiQueryService;
import com.ifhz.core.service.imei.bean.ImeiQueryType;
import com.ifhz.core.service.imei.bean.StatImeiRequest;
import com.ifhz.core.service.imei.bean.StatImeiResult;
import com.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

public class StatImeiQueryServiceImplTest extends BaseTest {

    @Resource(name = "statImeiQueryService")
    private StatImeiQueryService statImeiQueryService;

    @Test
    public void testQueryImeiListFromLog() throws Exception {
        StatImeiRequest request = new StatImeiRequest(ImeiQueryType.Day_Device_Process);
        request.setDeviceCode("Device24");
        Date processDate = DateFormatUtils.parse("2014-06-06", "yyyy-MM-dd");
        request.setProcessDate(processDate);
        request.setChannelId(25L);
        request.setUa("ZTE_U960E");

        List<StatImeiResult> resultList = statImeiQueryService.queryImeiListFromLog(request);
        log(resultList);
    }

    @Test
    public void testQueryImeiListFromProduct() throws Exception {

    }
}