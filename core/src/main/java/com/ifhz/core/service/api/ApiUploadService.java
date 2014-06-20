package com.ifhz.core.service.api;

import com.ifhz.core.po.DataLog;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/20
 * Time: 0:21
 */
public interface ApiUploadService {

    public void saveCounterDataLog(DataLog po);

    public void saveDeviceDataLog(DataLog po);

    public void batchSave(List<DataLog> processLogList);
}
