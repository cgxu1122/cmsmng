package com.ifhz.core.adapter.impl;

import com.ifhz.core.adapter.DeviceProcessLogAdapter;
import com.ifhz.core.mapper.DeviceProcessLogMapper;
import com.ifhz.core.po.DeviceProcessLog;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:14
 */
@Repository("deviceProcessLogAdapter")
public class DeviceProcessLogAdapterImpl implements DeviceProcessLogAdapter {

    @Resource(name = "deviceProcessLogMapper")
    private DeviceProcessLogMapper deviceProcessLogMapper;

    @Override
    public void insert(String tableName, DeviceProcessLog po) {
        po.setTableName(tableName);
        deviceProcessLogMapper.insert(po);
    }

    @Override
    public DeviceProcessLog getByImei(String tableName, String imei) {
        if (StringUtils.isBlank(imei)) {
            return null;
        }
        return deviceProcessLogMapper.getByImei(tableName, imei);
    }

    @Override
    public List<DeviceProcessLog> getDeviceProcessLog(Map m) {
        return deviceProcessLogMapper.getDeviceProcessLog(m);
    }

    @Override
    public long getYesterdayLogCount(Map tableName) {
        return deviceProcessLogMapper.getYesterdayLogCount(tableName);
    }

    @Override
    public long getLogCountByTime(Map m) {
        return deviceProcessLogMapper.getLogCountByTime(m);
    }
}
