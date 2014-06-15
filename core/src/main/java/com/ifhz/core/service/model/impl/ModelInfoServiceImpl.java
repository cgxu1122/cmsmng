package com.ifhz.core.service.model.impl;

import com.ifhz.core.adapter.ModelInfoAdapter;
import com.ifhz.core.adapter.PubChlModRefAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.po.PubChlModRef;
import com.ifhz.core.service.model.ModelInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Resource(name = "pubChlModRefAdapter")
    private PubChlModRefAdapter pubChlModRefAdapter;


    @Override
    public ModelInfo getById(Long id) {
        return modelInfoAdapter.getById(id);
    }

    @Override
    public List<ModelInfo> queryByVo(Pagination page, ModelInfo record) {
        return modelInfoAdapter.queryByVo(page, record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(ModelInfo record) {
        return modelInfoAdapter.insert(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(ModelInfo record) {
        return modelInfoAdapter.update(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(ModelInfo record) {
        int ret = modelInfoAdapter.delete(record);
        if (ret == 0) {
            PubChlModRef ref = new PubChlModRef();
            ref.setGroupId(record.getGroupId());
            ref.setModelId(record.getModelId());
            pubChlModRefAdapter.deleteRepeatRef(ref);
        }

        return ret;
    }

    @Override
    public ModelInfo getByGroupIdAndUa(Long groupId, String ua) {
        return modelInfoAdapter.getByGroupIdAndUa(groupId, ua);
    }
}
