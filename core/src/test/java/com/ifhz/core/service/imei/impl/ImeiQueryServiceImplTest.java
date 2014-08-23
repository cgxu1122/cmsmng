package com.ifhz.core.service.imei.impl;

import com.google.common.collect.Sets;
import com.ifhz.core.adapter.ImeiQueryAdapter;
import com.ifhz.core.service.imei.ImeiQueryService;
import com.ifhz.core.service.imei.bean.DataLogResult;
import com.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

public class ImeiQueryServiceImplTest extends BaseTest {

    @Resource(name = "imeiQueryService")
    private ImeiQueryService imeiQueryService;
    @Resource(name = "imeiQueryAdapter")
    private ImeiQueryAdapter imeiQueryAdapter;

    @Test
    public void testQueryListByImeiList() throws Exception {

        Set<String> imeiList = Sets.newHashSet("863925024510590",
                "352956061737216",
                "864730013660128",
                "864730013534216",
                "864730013665135",
                "864730013668071",
                "864730013668170",
                "864730013668493",
                "864730013794638",
                "864730013799058");
        List<DataLogResult> dataLogResultList = imeiQueryService.queryListByImeiList(imeiList);
        log(dataLogResultList);

        log(imeiQueryAdapter.queryImeiList());

    }

    @Test
    public void testQueryListByImeiList1() throws Exception {

        log(imeiQueryAdapter.queryImeiList());

    }
}