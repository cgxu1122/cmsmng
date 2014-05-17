package com.ifhz.core.service.model.impl;

import com.ifhz.core.adapter.ModelChannelRefAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ModelChannelRef;
import com.ifhz.core.service.model.ModelChannelRefService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service("modelChannelRefService")
public class ModelChannelRefServiceImpl implements ModelChannelRefService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelChannelRefServiceImpl.class);

    @Resource(name = "modelChannelRefAdapter")
    private ModelChannelRefAdapter modelChannelRefAdapter;


    @Override
    public List<ModelChannelRef> queryByVo(Pagination page, ModelChannelRef record) {
        return modelChannelRefAdapter.queryByVo(page, record);
    }

    @Override
    public int insert(ModelChannelRef record) {
        return modelChannelRefAdapter.insert(record);
    }

    @Override
    public int delete(ModelChannelRef record) {
        return modelChannelRefAdapter.delete(record);
    }
}
