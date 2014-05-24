package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.DeviceInfoAdapter;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.DeviceInfoMapper;
import com.ifhz.core.po.DeviceInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
@Repository("deviceInfoAdapter")
public class DeviceInfoAdapterImpl implements DeviceInfoAdapter {

    @Resource(name = "deviceInfoMapper")
    private DeviceInfoMapper deviceInfoMapper;


    @Override
    public DeviceInfo getById(Long id) {
        return deviceInfoMapper.getById(id);
    }

    @Override
    public List<DeviceInfo> queryByVo(Pagination page, DeviceInfo record) {
        List<DeviceInfo> result = deviceInfoMapper.queryByVo(page, record);
        return result == null ? Lists.<DeviceInfo>newArrayList() : result;
    }

    @Override
    public int insert(DeviceInfo record) {
        record.setCreateTime(new Date());
        return deviceInfoMapper.insert(record);
    }

    @Override
    public int update(DeviceInfo record) {
        record.setUpdateTime(new Date());
        return deviceInfoMapper.update(record);
    }

    @Override
    public int delete(DeviceInfo record) {
        record.setActive(JcywConstants.ACTIVE_D);
        record.setUpdateTime(new Date());
        return deviceInfoMapper.update(record);
    }

    @Override
    public DeviceInfo queryByDeviceCode(String deviceCode) {
        return deviceInfoMapper.queryByDeviceCode(deviceCode);
    }
}
