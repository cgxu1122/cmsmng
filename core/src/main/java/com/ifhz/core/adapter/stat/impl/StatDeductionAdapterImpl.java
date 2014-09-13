package com.ifhz.core.adapter.stat.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.stat.StatDeductionAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.stat.StatDeductionMapper;
import com.ifhz.core.po.stat.StatDeduction;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/9
 * Time: 0:38
 */
@Repository
public class StatDeductionAdapterImpl implements StatDeductionAdapter {

    @Resource
    private StatDeductionMapper statDeductionMapper;


    @Override
    public int insert(StatDeduction record) {
        return statDeductionMapper.insert(record);
    }

    @Override
    public int update(StatDeduction record) {
        return statDeductionMapper.update(record);
    }

    @Override
    public StatDeduction getById(Long id) {
        return statDeductionMapper.getById(id);
    }

    @Override
    public StatDeduction getByChannelId(Long channelId) {
        List<StatDeduction> list = statDeductionMapper.getByChannelId(channelId);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<StatDeduction> queryByVo(Pagination pagination, StatDeduction record) {
        List<StatDeduction> result = statDeductionMapper.queryByVo(pagination, record);
        return result == null ? Lists.<StatDeduction>newArrayList() : result;
    }
}
