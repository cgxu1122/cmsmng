package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.DataLogAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.DataLogMapper;
import com.ifhz.core.po.DataLog;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/20
 * Time: 20:38
 */
@Repository("dataLogAdapter")
public class DataLogAdapterImpl implements DataLogAdapter {

    @Resource(name = "dataLogMapper")
    private DataLogMapper dataLogMapper;

    @Override
    public int insertDeviceData(DataLog record) {
        return dataLogMapper.insertDeviceData(record);
    }

    @Override
    public int updateCounterData(DataLog record) {
        return dataLogMapper.updateCounterData(record);
    }

    @Override
    public DataLog getByImei(String tableName, String imei) {
        return dataLogMapper.getByImei(tableName, imei);
    }

    @Override
    public List<DataLog> queryPageForDevice(Pagination page, String tableName, Date startTime, Date endTime) {
        List<DataLog> result = dataLogMapper.queryPageForDevice(page, tableName, startTime, endTime);
        return result == null ? Lists.<DataLog>newArrayList() : result;
    }

    @Override
    public List<DataLog> queryPageForCounter(Pagination page, String tableName, Date startTime, Date endTime) {
        List<DataLog> result = dataLogMapper.queryPageForCounter(page, tableName, startTime, endTime);
        return result == null ? Lists.<DataLog>newArrayList() : result;
    }

    @Override
    public long queryTotalCountForDevice(String tableName, Date startTime, Date endTime) {
        return dataLogMapper.queryTotalCountForDevice(tableName, startTime, endTime);
    }

    @Override
    public long queryTotalCountForCounter(String tableName, Date startTime, Date endTime, Integer active) {
        return dataLogMapper.queryTotalCountForCounter(tableName, startTime, endTime, active);
    }
}
