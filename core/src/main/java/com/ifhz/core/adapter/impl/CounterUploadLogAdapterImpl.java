package com.ifhz.core.adapter.impl;

import com.ifhz.core.adapter.CounterUploadLogAdapter;
import com.ifhz.core.mapper.CounterUploadLogMapper;
import com.ifhz.core.po.CounterUploadLog;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:13
 */
@Repository("counterUploadLogAdapter")
public class CounterUploadLogAdapterImpl implements CounterUploadLogAdapter {

    @Resource(name = "counterUploadLogMapper")
    private CounterUploadLogMapper counterUploadLogMapper;

    @Override
    public void insert(CounterUploadLog po) {
        counterUploadLogMapper.insert(po);
    }

    @Override
    public CounterUploadLog getByImei(String imei) {
        if (StringUtils.isBlank(imei)) {
            return null;
        }
        return counterUploadLogMapper.getByImei(imei);
    }
}
