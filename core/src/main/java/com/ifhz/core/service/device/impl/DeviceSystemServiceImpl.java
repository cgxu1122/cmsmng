package com.ifhz.core.service.device.impl;

import com.ifhz.core.adapter.DeviceSystemAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DeviceSystem;
import com.ifhz.core.service.device.DeviceSystemService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service("deviceSystemService")
public class DeviceSystemServiceImpl implements DeviceSystemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSystemServiceImpl.class);

    @Resource(name = "deviceSystemAdapter")
    private DeviceSystemAdapter deviceSystemAdapter;


    @Override
    @Log
    public DeviceSystem getById(Long id) {
        return deviceSystemAdapter.getById(id);
    }

    @Override
    @Log
    public List<DeviceSystem> queryByVo(Pagination page, DeviceSystem record) {
        return deviceSystemAdapter.queryByVo(page, record);
    }

    @Override
    @Log
    public int insert(DeviceSystem record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        return deviceSystemAdapter.insert(record);
    }

    @Override
    @Log
    public int update(DeviceSystem record) {
        record.setUpdateTime(new Date());
        return deviceSystemAdapter.update(record);
    }

    @Override
    @Log
    public int delete(DeviceSystem record) {
        return deviceSystemAdapter.delete(record);
    }

    @Override
    public DeviceSystem queryNewestVersion(Date currentTime) {
        List<DeviceSystem> result = deviceSystemAdapter.queryNewestVersion(currentTime);
        if (CollectionUtils.isNotEmpty(result)) {
            return result.get(0);
        }
        return null;
    }
}
