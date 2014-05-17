package com.ifhz.core.service.model.impl;

import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.service.model.ModelInfoService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ModelInfoServiceImplTest extends BaseTest {

    @Autowired
    private ModelInfoService modelInfoService;

    @Test
    public void testGetById() throws Exception {
        log(modelInfoService.getById(2L));
    }

    @Test
    public void testQueryByVo() throws Exception {
        ModelInfo po = new ModelInfo();
        po.setActive("Y");
        log(modelInfoService.queryByVo(null, po));
    }

    @Test
    public void testInsert() throws Exception {
        ModelInfo po = new ModelInfo();
        po.setUa("122aaaa");
        po.setModelName("三星手机");
        po.setTagNum(2);
        po.setTagPrice(3.333);
        po.setGroupId(1L);
        modelInfoService.insert(po);
        log(po.getModelId());
    }

    @Test
    public void testUpdate() throws Exception {
        ModelInfo po = new ModelInfo();
        po.setModelId(2L);
        po.setGroupId(1L);
        po.setTagNum(66);
        po.setTagPrice(4.4444);
        modelInfoService.update(po);
    }

    @Test
    public void testDelete() throws Exception {
        ModelInfo po = new ModelInfo();
        po.setModelId(2L);
        modelInfoService.delete(po);
    }
}