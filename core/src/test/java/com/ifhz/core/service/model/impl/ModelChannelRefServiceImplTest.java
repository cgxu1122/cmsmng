package com.ifhz.core.service.model.impl;

import com.ifhz.core.po.ModelChannelRef;
import com.ifhz.core.service.model.ModelChannelRefService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ModelChannelRefServiceImplTest extends BaseTest {

    @Autowired
    private ModelChannelRefService modelChannelRefService;

    @Test
    public void testQueryByVo() throws Exception {
        ModelChannelRef po = new ModelChannelRef();
        log(modelChannelRefService.queryByVo(null, po));
    }

    @Test
    public void testInsert() throws Exception {
        ModelChannelRef po = new ModelChannelRef();
        po.setModelId(1L);
        po.setChannelId(2L);
        po.setGroupId(1L);
        modelChannelRefService.insert(po);
        log(po.getModelId());
    }

    @Test
    public void testDelete() throws Exception {
        ModelChannelRef po = new ModelChannelRef();
        po.setModelId(1L);
        po.setChannelId(2L);
        modelChannelRefService.delete(po);
    }
}