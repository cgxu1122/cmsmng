package com.ifhz.core.adapter.impl;

import com.ifhz.core.adapter.CounterUploadLogAdapter;
import com.ifhz.core.mapper.CounterUploadLogMapper;
import com.ifhz.core.po.CounterUploadLog;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public void insert(String tableName, CounterUploadLog po) {
        po.setTableName(tableName);
        counterUploadLogMapper.insert(po);
    }

    @Override
    public CounterUploadLog getByImei(String tableName, String imei) {
        if (StringUtils.isBlank(imei)) {
            return null;
        }
        return counterUploadLogMapper.getByImei(tableName, imei);
    }

    @Override
    public List<CounterUploadLog> getCounterUploadLog(Map m) {
        return counterUploadLogMapper.getCounterUploadLog(m);
    }

    @Override
    public long getYesterdayLogCount(Map tableName) {
        return counterUploadLogMapper.getYesterdayLogCount(tableName);
    }

    @Override
    public long getLogCountByTime(Map m) {
        return counterUploadLogMapper.getLogCountByTime(m);
    }
}
