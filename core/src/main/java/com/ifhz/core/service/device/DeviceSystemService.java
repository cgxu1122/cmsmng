package com.ifhz.core.service.device;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DeviceSystem;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangjian
 */
public interface DeviceSystemService {
    public DeviceSystem getById(Long id);

    public List<DeviceSystem> queryByVo(Pagination page, DeviceSystem record);

    public int insert(DeviceSystem record);

    public int update(DeviceSystem record);

    public int delete(DeviceSystem record);

    public DeviceSystem queryNewestVersion(Date currentTime);
}
