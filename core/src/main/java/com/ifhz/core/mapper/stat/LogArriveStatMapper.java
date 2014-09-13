package com.ifhz.core.mapper.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.LogArriveStat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public interface LogArriveStatMapper {

    public int insert(LogArriveStat record);

    public int update(LogArriveStat record);

    public LogArriveStat getById(Long id);

    public LogArriveStat getByMd5Key(String md5Key);

    public List<LogArriveStat> queryByVo(Pagination pagination, @Param("record") LogArriveStat record);

    public LogArriveStat queryCountByVo(LogArriveStat record);
}
