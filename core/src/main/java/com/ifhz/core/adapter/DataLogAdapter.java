package com.ifhz.core.adapter;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DataLog;

import java.util.Date;
import java.util.List;

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

    public List<DataLog> queryPageForDevice(Pagination page, String tableName, Date startTime, Date endTime);

    public List<DataLog> queryPageForCounter(Pagination page, String tableName, Date startTime, Date endTime);

    public long queryTotalCountForDevice(String tableName, Date startTime, Date endTime);

    public long queryTotalCountForCounter(String tableName, Date startTime, Date endTime, Integer active);
}
