package com.ifhz.core.service.batch.impl;

import com.ifhz.core.adapter.BatchInfoAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.BatchInfo;
import com.ifhz.core.service.batch.BatchInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service("batchInfoService")
public class BatchInfoServiceImpl implements BatchInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchInfoServiceImpl.class);

    @Resource(name = "batchInfoAdapter")
    private BatchInfoAdapter batchInfoAdapter;


    @Override
    public BatchInfo getById(Long id) {
        return batchInfoAdapter.getById(id);
    }

    @Override
    public List<BatchInfo> queryByVo(Pagination page, BatchInfo record) {
        return batchInfoAdapter.queryByVo(page, record);
    }

    @Override
    public int insert(BatchInfo record) {
        return batchInfoAdapter.insert(record);
    }

    @Override
    public int update(BatchInfo record) {
        return batchInfoAdapter.update(record);
    }

    @Override
    public int delete(BatchInfo record) {
        return batchInfoAdapter.delete(record);
    }
}
