package com.ifhz.core.mapper;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.stat.bean.DataLogRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    public List<DataLog> queryPageForStat(Pagination page, @Param("dataLogRequest") DataLogRequest dataLogRequest);

    public long queryDeviceUpdDayNum(DataLogRequest dataLogRequest);

    public long queryProductUpdDayNum(DataLogRequest dataLogRequest);

    public long queryTotalCountForStat(DataLogRequest dataLogRequest);

    public List<String> getLogImeiList(Map<String, Object> params);

    public List<String> getProductImeiList(Map<String, Object> params);
}
