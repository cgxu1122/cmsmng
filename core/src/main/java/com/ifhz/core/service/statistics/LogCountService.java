package com.ifhz.core.service.statistics;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.LogCount;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 14-5-22.
 */
public interface LogCountService {
    public LogCount getById(Long id);

    public List<LogCount> queryByVo(LogCount record);

    public int insert(LogCount record);

    public int update(LogCount record);

    public int delete(LogCount record);

    public void countLogByDate(Date startDate, Date endDate);

    public List<Map<String, Object>> partnerQuery(Pagination page, Map pars);

    public List<Map<String, Object>> partnerCPQuery(Pagination page, Map pars);

    public List<Map<String, Object>> partnerLaowuQueryList(Pagination page, Map pars);

    public List<Map<String, Object>> warehouseQueryList(Pagination page, Map pars);


}
