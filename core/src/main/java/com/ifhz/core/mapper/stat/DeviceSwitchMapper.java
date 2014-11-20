package com.ifhz.core.mapper.stat;

import com.ifhz.core.po.DeviceSwitch;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/11/16
 * Time: 19:29
 */
public interface DeviceSwitchMapper {


    public int insert(DeviceSwitch record);

    public int update(DeviceSwitch record);

    public int delete(String deviceCode);

    public DeviceSwitch get(String deviceCode);

    public List<DeviceSwitch> queryByVo();

}
