package com.ifhz.core.service.wdj.impl;

import com.ifhz.core.service.wdj.WdjDataQueryService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class WdjDataQueryServiceImplTest extends BaseTest {

    @Autowired
    private WdjDataQueryService wdjDataQueryService;

    @Test
    public void testQueryDataList() throws Exception {
        wdjDataQueryService.queryDataList(new Date());
    }
}