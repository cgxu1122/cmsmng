package com.ifhz.core.service.imei.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.service.imei.ImeiQueryService;
import com.ifhz.core.service.imei.bean.DataLogResult;
import com.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class ImeiQueryServiceImplTest extends BaseTest {

    @Resource(name = "imeiQueryService")
    private ImeiQueryService imeiQueryService;

    @Test
    public void testQueryListByImeiList() throws Exception {

        List<String> imeiList = Lists.newArrayList("1", "2", "3");
        List<DataLogResult> dataLogResultList = imeiQueryService.queryListByImeiList(imeiList);
        log(dataLogResultList);

    }
}