package com.ifhz.core.service.apk.impl;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ApkInfo;
import com.ifhz.core.service.pkgmng.ApkInfoService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ApkInfoServiceImplTest extends BaseTest {
    @Autowired
    private ApkInfoService apkInfoService;

    @Test
    public void testGetById() throws Exception {
        log(apkInfoService.getById(2L));
    }

    @Test
    public void testQueryByVo() throws Exception {
        ApkInfo po = new ApkInfo();
        po.setActive("Y");
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(10);
        log(apkInfoService.queryByVo(page, po));
    }

    @Test
    public void testInsert() throws Exception {
        ApkInfo po = new ApkInfo();
        po.setApkName("JJ斗地主（D）");
        po.setApkName("JJLord.21220.38487.visible.apk");
        po.setFtpPath("/data/apk/aaaa.apk");
        log(apkInfoService.insert(po));
    }

    @Test
    public void testUpdate() throws Exception {
        ApkInfo po = new ApkInfo();
        po.setApkId(2L);
        po.setApkName("JJ斗地主（D）2");
        log(apkInfoService.update(po));
    }

    @Test
    public void testDelete() throws Exception {
        ApkInfo po = new ApkInfo();
        po.setApkId(2L);
        log(apkInfoService.delete(po));
    }
}