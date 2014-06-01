package com.ifhz.core.mapper;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.LogCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lm on 14-5-15.
 */
public interface LogCountMapper {
    public LogCount getById(Long id);

    public List<LogCount> queryByVo(LogCount record);

    public int insert(LogCount record);

    public int update(LogCount record);

    public int delete(LogCount record);

    public void batchInsert(List<LogCount> list);

    public LogCount getByProcessKey(String processKey);

    public List<Map<String, Object>> partnerQuery(Pagination page, @Param(value = "record") Map record);

    public List<Map<String, Object>> partnerCPQuery(Pagination page, @Param(value = "record") Map record);

    public List<Map<String, Object>> partnerLaowuQueryList(Pagination page, @Param(value = "record") Map record);

}
