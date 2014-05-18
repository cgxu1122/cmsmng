package com.ifhz.core.service.api;

import com.ifhz.core.po.CounterUploadLog;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 16:28
 */
public interface CounterUploadLogService {


    public void insert(CounterUploadLog po);

    public boolean queryHasImei(String imei);
}
