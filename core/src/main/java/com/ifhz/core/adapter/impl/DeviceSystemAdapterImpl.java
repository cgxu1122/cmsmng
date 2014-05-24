package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.DeviceSystemAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.DeviceSystemMapper;
import com.ifhz.core.po.DeviceSystem;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
@Repository("deviceSystemAdapter")
public class DeviceSystemAdapterImpl implements DeviceSystemAdapter {

    @Resource(name = "deviceSystemMapper")
    private DeviceSystemMapper deviceSystemMapper;


    @Override
    public DeviceSystem getById(Long id) {
        return deviceSystemMapper.getById(id);
    }

    @Override
    public List<DeviceSystem> queryByVo(Pagination page, DeviceSystem record) {
        List<DeviceSystem> result = deviceSystemMapper.queryByVo(page, record);
        return result == null ? Lists.<DeviceSystem>newArrayList() : result;
    }

    @Override
    public int insert(DeviceSystem record) {
        record.setCreateTime(new Date());
        return deviceSystemMapper.insert(record);
    }

    @Override
    public int update(DeviceSystem record) {
        return deviceSystemMapper.update(record);
    }

    @Override
    public int delete(DeviceSystem record) {
        return deviceSystemMapper.delete(record);
    }

    @Override
    public DeviceSystem queryNewestVersion(Date currentTime) {
        return deviceSystemMapper.queryNewestVersion(currentTime);
    }
}
