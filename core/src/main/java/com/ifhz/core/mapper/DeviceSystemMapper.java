package com.ifhz.core.mapper;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DeviceSystem;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
public interface DeviceSystemMapper {

    public DeviceSystem getById(Long id);

    public List<DeviceSystem> queryNewestVersion(@Param("currentTime") Date currentTime);

    public List<DeviceSystem> queryByVo(Pagination page, @Param(value = "record") DeviceSystem record);

    public int insert(DeviceSystem record);

    public int update(DeviceSystem record);

    public int delete(DeviceSystem record);
}
