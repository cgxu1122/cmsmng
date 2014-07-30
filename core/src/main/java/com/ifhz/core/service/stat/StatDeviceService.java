package com.ifhz.core.service.stat;

import com.ifhz.core.po.DataLog;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/22
 * Time: 18:13
 */
public interface StatDeviceService {

    /**
     * 加工设备上传数据时，同步更新 流水统计表和产品统计表
     *
     * @param dataLog
     */
    public void updateStat(DataLog dataLog);
}
