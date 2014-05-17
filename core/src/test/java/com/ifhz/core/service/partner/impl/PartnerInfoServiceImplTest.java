package com.ifhz.core.service.partner.impl;

import com.ifhz.core.po.PartnerInfo;
import com.ifhz.core.service.partner.PartnerInfoService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PartnerInfoServiceImplTest extends BaseTest {

    @Autowired
    private PartnerInfoService partnerInfoService;

    @Test
    public void testGetById() throws Exception {
        log(partnerInfoService.getById(2L));
    }

    @Test
    public void testQueryByVo() throws Exception {
        PartnerInfo po = new PartnerInfo();
        po.setActive("Y");
        log(partnerInfoService.queryByVo(null, po));
    }

    @Test
    public void testInsert() throws Exception {
        PartnerInfo po = new PartnerInfo();
        po.setPartnerName("百度");
        po.setExportImeiSource("Y");
        po.setQueryImeiSource("Y");
        po.setUserId(1L);
        log(partnerInfoService.insert(po));
    }

    @Test
    public void testUpdate() throws Exception {
        PartnerInfo po = new PartnerInfo();
        po.setPartnerId(2L);
        po.setPartnerName("tengxun");
        po.setQueryImeiSource("Y");
        partnerInfoService.update(po);
    }

    @Test
    public void testDelete() throws Exception {
        PartnerInfo po = new PartnerInfo();
        po.setPartnerId(2L);
        partnerInfoService.delete(po);
    }
}