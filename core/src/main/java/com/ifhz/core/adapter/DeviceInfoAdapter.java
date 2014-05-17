package com.ifhz.core.adapter;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DeviceInfo;

import java.util.List;

/**
 * 类描述
 * User:yangjian
 */
public interface DeviceInfoAdapter {

    public DeviceInfo getById(Long id);

    public List<DeviceInfo> queryByVo(Pagination page, DeviceInfo record);

    public int insert(DeviceInfo record);

    public int update(DeviceInfo record);

    public int delete(DeviceInfo record);
}
