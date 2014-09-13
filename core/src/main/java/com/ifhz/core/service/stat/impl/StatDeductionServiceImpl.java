package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.stat.StatDeductionAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.StatDeduction;
import com.ifhz.core.service.stat.StatDeductionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/13
 * Time: 19:40
 */
@Service
public class StatDeductionServiceImpl implements StatDeductionService {

    @Resource
    private StatDeductionAdapter statDeductionAdapter;

    @Override
    public int insert(StatDeduction record) {
        record.setCreateTime(new Date());
        return statDeductionAdapter.insert(record);
    }

    @Override
    public int update(StatDeduction record) {
        record.setUpdateTime(new Date());
        return statDeductionAdapter.update(record);
    }

    @Override
    public StatDeduction getById(Long id) {
        return statDeductionAdapter.getById(id);
    }

    @Override
    public List<StatDeduction> queryByVo(Pagination pagination, StatDeduction record) {
        return statDeductionAdapter.queryByVo(pagination, record);
    }
}
