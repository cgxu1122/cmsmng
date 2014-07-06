package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.DataLogAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.DataLogMapper;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.stat.bean.DataLogRequest;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public List<DataLog> queryPageForStat(Pagination page, DataLogRequest dataLogRequest) {
        List<DataLog> result = dataLogMapper.queryPageForStat(page, dataLogRequest);
        return result == null ? Lists.<DataLog>newArrayList() : result;
    }

    @Override
    public long queryDeviceUpdDayNum(DataLogRequest dataLogRequest) {
        return dataLogMapper.queryDeviceUpdDayNum(dataLogRequest);
    }

    @Override
    public long queryProductUpdDayNum(DataLogRequest dataLogRequest) {
        return dataLogMapper.queryProductUpdDayNum(dataLogRequest);
    }

    @Override
    public long queryTotalCountForStat(DataLogRequest dataLogRequest) {
        return dataLogMapper.queryTotalCountForStat(dataLogRequest);
    }


    @Override
    public List<String> getLogImeiList(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<String> getProductImeiList(Map<String, Object> params) {
        return null;
    }
}
