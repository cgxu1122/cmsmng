package com.ifhz.core.mapper;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.CounterTempLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:13
 */
public interface CounterTempLogMapper {

    public int insert(CounterTempLog po);

    public int update(@Param("imei") String imei, @Param("type") Integer type);

    public CounterTempLog queryByImei(@Param("imei") String imei);

    public long queryTotalCount(@Param(value = "startTime") Date startTime,
                                @Param(value = "endTime") Date endTime);

    public List<CounterTempLog> queryPage(Pagination page,
                                          @Param(value = "startTime") Date startTime,
                                          @Param(value = "endTime") Date endTime);


    public void batchDelete(@Param(value = "startTime") Date startTime, @Param(value = "endTime") Date endTime);
}
