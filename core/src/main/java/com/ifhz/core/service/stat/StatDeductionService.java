package com.ifhz.core.service.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.StatDeduction;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/9
 * Time: 0:38
 */
public interface StatDeductionService {

    public int insert(StatDeduction record);

    public int update(StatDeduction record);

    public StatDeduction getById(Long id);

    public StatDeduction getByChannelId(Long channelId);

    public List<StatDeduction> queryByVo(Pagination pagination, StatDeduction record);
}
