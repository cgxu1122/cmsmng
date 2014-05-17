package com.ifhz.core.mapper;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DeviceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
public interface DeviceInfoMapper {

    public DeviceInfo getById(Long id);

    public List<DeviceInfo> queryByVo(Pagination page, @Param(value = "record") DeviceInfo record);

    public int insert(DeviceInfo record);

    public int update(DeviceInfo record);

    public int delete(DeviceInfo record);
}
