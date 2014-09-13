package com.ifhz.core.mapper.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.StatDeduction;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/9
 * Time: 0:37
 */
public interface StatDeductionMapper {

    public int insert(StatDeduction record);

    public int update(StatDeduction record);

    public StatDeduction getById(Long id);

    public List<StatDeduction> queryByVo(Pagination pagination, StatDeduction record);
}
