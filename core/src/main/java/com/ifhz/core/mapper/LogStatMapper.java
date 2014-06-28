package com.ifhz.core.mapper;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.LogStat;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 11:46
 */
public interface LogStatMapper {

    public int insert(LogStat record);

    public int update(LogStat record);

    public LogStat getById(Long id);

    public LogStat getByMd5Key(String md5Key);

    public List<LogStat> queryListByQueryKey(String queryKey, Date startTime, Date endTime);

    public List<LogStat> queryByVo(Pagination page, @Param(value = "record") LogStat record);

    public LogStat queryCountByVO(LogStat record);
}
