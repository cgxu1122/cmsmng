package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.BatchInfoAdapter;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.BatchInfoMapper;
import com.ifhz.core.po.BatchInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
@Repository("batchInfoAdapter")
public class BatchInfoAdapterImpl implements BatchInfoAdapter {

    @Resource(name = "batchInfoMapper")
    private BatchInfoMapper batchInfoMapper;


    @Override
    public BatchInfo getById(Long id) {
        return batchInfoMapper.getById(id);
    }

    @Override
    public List<BatchInfo> queryByVo(Pagination page, BatchInfo record) {
        List<BatchInfo> result = batchInfoMapper.queryByVo(page, record);
        return result == null ? Lists.<BatchInfo>newArrayList() : result;
    }

    @Override
    public int insert(BatchInfo record) {
        return batchInfoMapper.insert(record);
    }

    @Override
    public int update(BatchInfo record) {
        record.setUpdateTime(new Date());
        return batchInfoMapper.update(record);
    }

    @Override
    public int delete(BatchInfo record) {
        record.setActive(JcywConstants.ACTIVE_D);
        record.setUpdateTime(new Date());
        return batchInfoMapper.update(record);
    }
}
