package com.ifhz.core.service.common;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 12:47
 */
public interface SplitTableService {
    /**
     * 获取加工设备数据当前插入的表名称
     *
     * @param now
     * @return
     */
    public String getTableNameForDeviceByNow(Date now);

    /**
     * 获取计数器数据当前插入的表名称
     *
     * @param now
     * @return
     */
    public String getTableNameForCounterByNow(Date now);


    /**
     * 获取加工设备数据 验证数据需要查询的表名列表
     *
     * @param now
     * @return
     */
    public List<String> getTableListForDeviceByNow(Date now);

    /**
     * 获取计数器数据 验证数据需要查询的表名列表
     *
     * @param now
     * @return
     */
    public List<String> getTableListForCounterByNow(Date now);
}
