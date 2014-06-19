package com.ifhz.core.adapter;

import com.ifhz.core.service.imei.bean.CounterResult;
import com.ifhz.core.service.imei.bean.DeviceResult;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/16
 * Time: 23:54
 */
public interface ImeiQueryAdapter {

    public int insertBatch(List<String> list);

    public List<DeviceResult> queryListForDeviceResult(String tableName);

    public List<CounterResult> queryListForCounterResult(String tableName);
}
