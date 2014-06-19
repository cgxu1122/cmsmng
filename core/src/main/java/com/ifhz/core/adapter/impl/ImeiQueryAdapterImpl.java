package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.ImeiQueryAdapter;
import com.ifhz.core.mapper.ImeiTempMapper;
import com.ifhz.core.service.imei.bean.CounterResult;
import com.ifhz.core.service.imei.bean.DeviceResult;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/16
 * Time: 23:54
 */
@Repository("imeiQueryAdapter")
public class ImeiQueryAdapterImpl implements ImeiQueryAdapter {

    @Resource(name = "imeiTempMapper")
    private ImeiTempMapper imeiTempMapper;

    @Override
    public int insertBatch(List<String> list) {
        return imeiTempMapper.insertBatch(list);
    }

    @Override
    public List<DeviceResult> queryListForDeviceResult(String tableName) {
        List<DeviceResult> list = imeiTempMapper.queryListForDeviceResult(tableName);
        return list == null ? Lists.<DeviceResult>newArrayList() : list;
    }

    @Override
    public List<CounterResult> queryListForCounterResult(String tableName) {
        List<CounterResult> list = imeiTempMapper.queryListForCounterResult(tableName);
        return list == null ? Lists.<CounterResult>newArrayList() : list;
    }
}
