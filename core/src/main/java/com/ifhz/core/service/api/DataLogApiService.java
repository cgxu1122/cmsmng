package com.ifhz.core.service.api;

import com.ifhz.core.po.DataLog;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 16:28
 */
public interface DataLogApiService {

    public int insertDeviceData(DataLog record);

    public int updateCounterData(DataLog record);

    public DataLog getByImei(String imei);

}
