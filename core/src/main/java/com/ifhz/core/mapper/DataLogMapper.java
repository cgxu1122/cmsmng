package com.ifhz.core.mapper;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.stat.bean.DataLogRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:14
 */
public interface DataLogMapper {

    public int insertDeviceData(DataLog record);

    public int updateCounterData(DataLog record);

    public DataLog getByImei(@Param("tableName") String tableName, @Param("imei") String imei);

    public List<DataLog> queryPageForDevice(Pagination page, @Param("dataLogRequest") DataLogRequest dataLogRequest);

    public List<DataLog> queryPageForCounter(Pagination page, @Param("dataLogRequest") DataLogRequest dataLogRequest);

    public long queryTotalCountForDevice(DataLogRequest dataLogRequest);

    public long queryTotalCountForCounter(@Param("dataLogRequest") DataLogRequest dataLogRequest);
}
