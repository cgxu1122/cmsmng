package com.ifhz.core.adapter.impl;

import com.google.common.collect.Maps;
import com.ifhz.core.adapter.DataLogAdapter;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.service.imei.bean.ImeiQueryType;
import com.ifhz.core.service.imei.bean.StatImeiRequest;
import com.ifhz.core.service.stat.handle.DateHandler;
import com.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DataLogAdapterImplTest extends BaseTest {

    @Resource(name = "dataLogAdapter")
    private DataLogAdapter dataLogAdapter;

    @Test
    public void testGetLogImeiList() throws Exception {
        StatImeiRequest request = new StatImeiRequest(ImeiQueryType.Log_Install, false);
        request.setDeviceCode("Device24");
        Date processDate = DateFormatUtils.parse("2014-06-06", "yyyy-MM-dd");
        request.setProcessDate(processDate);
        request.setChannelId(25L);
        request.setUa("ZTE_U960E");

        String tableName = "TY_DATA_LOG_20143";

        Map<String, Object> params = Maps.newHashMap();
        params.put("ua", request.getUa());
        params.put("startTime", DateHandler.getStartTime(request.getProcessDate()));
        params.put("endTime", DateHandler.getEndTime(request.getProcessDate()));
        params.put("deviceCode", request.getDeviceCode());
        params.put("channelId", request.getChannelId());
        params.put("type", request.getType().value);
        if (request.getActive() != null) {
            params.put("activeType", request.getActive().value);
        }
        params.put("tableName", tableName);
        log(params);
        List<String> list = dataLogAdapter.getLogImeiList(params);
        log(list);
    }

    @Test
    public void testGetProductImeiList() throws Exception {

    }
}