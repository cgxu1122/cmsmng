package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.BatchProductRefAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.BatchProductRefMapper;
import com.ifhz.core.po.BatchProductRef;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
@Repository("batchProductRefAdapter")
public class BatchProductRefAdapterImpl implements BatchProductRefAdapter {

    @Resource(name = "batchProductRefMapper")
    private BatchProductRefMapper batchProductRefMapper;


    @Override
    public BatchProductRef getById(Long id) {
        return batchProductRefMapper.getById(id);
    }

    @Override
    public List<BatchProductRef> queryByVo(Pagination page, BatchProductRef record) {
        List<BatchProductRef> result = batchProductRefMapper.queryByVo(page, record);
        return result == null ? Lists.<BatchProductRef>newArrayList() : result;
    }

    @Override
    public int insert(BatchProductRef record) {
        return batchProductRefMapper.insert(record);
    }

    @Override
    public int update(BatchProductRef record) {
        return batchProductRefMapper.update(record);
    }

    @Override
    public int delete(BatchProductRef record) {
        return batchProductRefMapper.delete(record);
    }
}
