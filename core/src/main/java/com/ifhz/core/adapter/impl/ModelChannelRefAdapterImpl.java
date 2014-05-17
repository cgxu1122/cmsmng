package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.ModelChannelRefAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.ModelChannelRefMapper;
import com.ifhz.core.po.ModelChannelRef;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
@Repository("modelChannelRefAdapter")
public class ModelChannelRefAdapterImpl implements ModelChannelRefAdapter {

    @Resource(name = "modelChannelRefMapper")
    private ModelChannelRefMapper modelChannelRefMapper;


    @Override
    public ModelChannelRef getById(Long id) {
        return modelChannelRefMapper.getById(id);
    }

    @Override
    public List<ModelChannelRef> queryByVo(Pagination page, ModelChannelRef record) {
        List<ModelChannelRef> result = modelChannelRefMapper.queryByVo(page, record);
        return result == null ? Lists.<ModelChannelRef>newArrayList() : result;
    }

    @Override
    public int insert(ModelChannelRef record) {
        return modelChannelRefMapper.insert(record);
    }

    @Override
    public int update(ModelChannelRef record) {
        return modelChannelRefMapper.update(record);
    }

    @Override
    public int delete(ModelChannelRef record) {
        return modelChannelRefMapper.delete(record);
    }
}
