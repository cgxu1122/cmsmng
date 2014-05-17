package com.ifhz.core.adapter;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DeviceSystem;

import java.util.List;

/**
 * 类描述
 * User:yangjian
 */
public interface DeviceSystemAdapter {

    public DeviceSystem getById(Long id);

    public List<DeviceSystem> queryByVo(Pagination page, DeviceSystem record);

    public int insert(DeviceSystem record);

    public int update(DeviceSystem record);

    public int delete(DeviceSystem record);
}
