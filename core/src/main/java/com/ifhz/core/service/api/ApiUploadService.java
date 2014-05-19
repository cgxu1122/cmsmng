package com.ifhz.core.service.api;

import com.ifhz.core.po.CounterUploadLog;
import com.ifhz.core.po.DeviceProcessLog;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/20
 * Time: 0:21
 */
public interface ApiUploadService {

    public void save(CounterUploadLog po);

    public void save(DeviceProcessLog po);

    public void batchSave(List<DeviceProcessLog> processLogList);
}
