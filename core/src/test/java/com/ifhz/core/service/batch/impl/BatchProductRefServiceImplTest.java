package com.ifhz.core.service.batch.impl;

import com.ifhz.core.po.BatchProductRef;
import com.ifhz.core.service.batch.BatchProductRefService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BatchProductRefServiceImplTest extends BaseTest {

    @Autowired
    private BatchProductRefService batchProductRefService;

    @Test
    public void testQueryByVo() throws Exception {
        BatchProductRef po = new BatchProductRef();
        log(batchProductRefService.queryByVo(null, po));
    }

    @Test
    public void testInsert() throws Exception {
        BatchProductRef po = new BatchProductRef();
        po.setBatchId(1L);
        po.setProductId(2L);
        log(batchProductRefService.insert(po));
    }

    @Test
    public void testDelete() throws Exception {
        BatchProductRef po = new BatchProductRef();
        po.setBatchId(1L);
        po.setProductId(2L);
        batchProductRefService.delete(po);
    }
}