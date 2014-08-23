package com.ifhz.core.mapper;

import com.ifhz.core.service.imei.bean.DataLogResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/16
 * Time: 22:20
 */
public interface ImeiTempMapper {

    public int insertBatch(@Param("list") Set<String> list);

    public List<DataLogResult> queryListByImeiList(@Param("tableName") String tableName);

    public List<String> queryImeiList();
}
