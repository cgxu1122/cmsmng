package com.ifhz.core.service.device;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DeviceInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangjian
 */
public interface DeviceInfoService {
    public DeviceInfo getById(Long id);

    public List<DeviceInfo> queryByVo(Pagination page, DeviceInfo record);

    public int insert(DeviceInfo record);

    public int update(DeviceInfo record);

    public int delete(DeviceInfo record);
}
