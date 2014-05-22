package com.ifhz.core.mapper;

import com.ifhz.core.po.CounterFailLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:13
 */
public interface CounterFailLogMapper {

    public void insert(CounterFailLog po);

    public void delete(Long failId);

    public CounterFailLog queryByImei(@Param("imei") String imei);


    public long queryTotalCount(@Param(value = "startTime") Date startTime,
                                @Param(value = "endTime") Date endTime);

    //TODO 分页sql 还没有写
    public List<CounterFailLog> queryPage(@Param("pageSize") int pageSize,
                                          @Param("pageNum") int pageNum,
                                          @Param(value = "startTime") Date startTime,
                                          @Param(value = "endTime") Date endTime);
}
