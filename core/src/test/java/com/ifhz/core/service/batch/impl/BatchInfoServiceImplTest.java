package com.ifhz.core.service.batch.impl;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.BatchInfo;
import com.ifhz.core.service.batch.BatchInfoService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BatchInfoServiceImplTest extends BaseTest {
    @Autowired
    private BatchInfoService batchInfoService;

    @Test
    public void testGetById() throws Exception {
        log(batchInfoService.getById(4L));
    }

    @Test
    public void testQueryByVo() throws Exception {
        BatchInfo po = new BatchInfo();
        po.setActive("Y");
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(10);
        log(batchInfoService.queryByVo(page, po));
    }

    @Test
    public void testInsert() throws Exception {
        BatchInfo po = new BatchInfo();
        po.setBatchCode("1.0.0");
        po.setGroupId(1L);
        log(batchInfoService.insert(po));
    }

    @Test
    public void testUpdate() throws Exception {
        BatchInfo po = new BatchInfo();
        po.setBatchId(4L);
        po.setBatchCode("1.0.2");
        log(batchInfoService.update(po));
    }

    @Test
    public void testDelete() throws Exception {
        BatchInfo po = new BatchInfo();
        po.setBatchId(4L);
        log(batchInfoService.delete(po));
    }
}