package com.ifhz.core.service.device.impl;

import com.ifhz.core.adapter.DeviceSystemAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DeviceSystem;
import com.ifhz.core.service.device.DeviceSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public DeviceSystem getById(Long id) {
        return deviceSystemAdapter.getById(id);
    }

    @Override
    public List<DeviceSystem> queryByVo(Pagination page, DeviceSystem record) {
        return deviceSystemAdapter.queryByVo(page, record);
    }

    @Override
    public int insert(DeviceSystem record) {
        return deviceSystemAdapter.insert(record);
    }

    @Override
    public int update(DeviceSystem record) {
        return deviceSystemAdapter.update(record);
    }

    @Override
    public int delete(DeviceSystem record) {
        return deviceSystemAdapter.delete(record);
    }
}
