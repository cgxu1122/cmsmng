package com.ifhz.core.adapter;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.stat.bean.DataLogRequest;

import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:14
 */
public interface DataLogAdapter {

    public int insertDeviceData(DataLog record);

    public int updateCounterData(DataLog record);

    public DataLog getByImei(String tableName, String imei);

    public List<DataLog> queryPageForDevice(Pagination page, DataLogRequest dataLogRequest);

    public List<DataLog> queryPageForCounter(Pagination page, DataLogRequest dataLogRequest);

    public long queryTotalCountForDevice(DataLogRequest dataLogRequest);

    public long queryTotalCountForCounter(DataLogRequest dataLogRequest);

    public List<String> getLogImeiList(Map<String, Object> params);

    public List<String> getProductImeiList(Map<String, Object> params);
}
