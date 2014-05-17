package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.ModelInfoAdapter;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.ModelInfoMapper;
import com.ifhz.core.po.ModelInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
@Repository("modelInfoAdapter")
public class ModelInfoAdapterImpl implements ModelInfoAdapter {

    @Resource(name = "modelInfoMapper")
    private ModelInfoMapper modelInfoMapper;


    @Override
    public ModelInfo getById(Long id) {
        return modelInfoMapper.getById(id);
    }

    @Override
    public List<ModelInfo> queryByVo(Pagination page, ModelInfo record) {
        List<ModelInfo> result = modelInfoMapper.queryByVo(page, record);
        return result == null ? Lists.<ModelInfo>newArrayList() : result;
    }

    @Override
    public int insert(ModelInfo record) {
        return modelInfoMapper.insert(record);
    }

    @Override
    public int update(ModelInfo record) {
        record.setUpdateTime(new Date());
        return modelInfoMapper.update(record);
    }

    @Override
    public int delete(ModelInfo record) {
        record.setActive(JcywConstants.ACTIVE_D);
        record.setUpdateTime(new Date());
        return modelInfoMapper.update(record);
    }
}
