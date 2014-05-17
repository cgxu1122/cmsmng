package com.ifhz.core.service.model.impl;

import com.ifhz.core.adapter.ModelInfoAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.service.model.ModelInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service("modelInfoService")
public class ModelInfoServiceImpl implements ModelInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelInfoServiceImpl.class);

    @Resource(name = "modelInfoAdapter")
    private ModelInfoAdapter modelInfoAdapter;


    @Override
    public ModelInfo getById(Long id) {
        return modelInfoAdapter.getById(id);
    }

    @Override
    public List<ModelInfo> queryByVo(Pagination page, ModelInfo record) {
        return modelInfoAdapter.queryByVo(page, record);
    }

    @Override
    public int insert(ModelInfo record) {
        return modelInfoAdapter.insert(record);
    }

    @Override
    public int update(ModelInfo record) {
        return modelInfoAdapter.update(record);
    }

    @Override
    public int delete(ModelInfo record) {
        return modelInfoAdapter.delete(record);
    }
}
