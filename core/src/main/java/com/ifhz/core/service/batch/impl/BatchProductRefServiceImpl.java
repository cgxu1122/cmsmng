package com.ifhz.core.service.batch.impl;

import com.ifhz.core.adapter.BatchProductRefAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.BatchProductRef;
import com.ifhz.core.service.batch.BatchProductRefService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service("batchProductRefService")
public class BatchProductRefServiceImpl implements BatchProductRefService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchProductRefServiceImpl.class);

    @Resource(name = "batchProductRefAdapter")
    private BatchProductRefAdapter batchProductRefAdapter;


    @Override
    @Log
    public List<BatchProductRef> queryByVo(Pagination page, BatchProductRef record) {
        return batchProductRefAdapter.queryByVo(page, record);
    }

    @Override
    @Log
    public int insert(BatchProductRef record) {
        return batchProductRefAdapter.insert(record);
    }

    @Override
    @Log
    public int delete(BatchProductRef record) {
        return batchProductRefAdapter.delete(record);
    }
}
