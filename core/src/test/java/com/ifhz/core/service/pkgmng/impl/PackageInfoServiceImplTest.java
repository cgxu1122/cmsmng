package com.ifhz.core.service.pkgmng.impl;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PackageInfo;
import com.ifhz.core.service.pkgmng.PackageInfoService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PackageInfoServiceImplTest extends BaseTest {

    @Autowired
    private PackageInfoService packageInfoService;

    @Test
    public void testGetById() throws Exception {
        log(packageInfoService.getById(2L));
    }

    @Test
    public void testQueryByVo() throws Exception {
        PackageInfo po = new PackageInfo();
        po.setActive("Y");
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(10);
        log(packageInfoService.queryByVo(page, po));
    }

    @Test
    public void testInsert() throws Exception {
        PackageInfo po = new PackageInfo();
        po.setPackageName("H30#160");
        po.setBatchId(2l);
        po.setBatchCode("160");
        po.setType("N");
        po.setActive("Y");
        log(packageInfoService.insert(po));
    }

    @Test
    public void testUpdate() throws Exception {
        PackageInfo po = new PackageInfo();
        po.setPackageId(2L);
        po.setRemark("tez");
        log(packageInfoService.update(po));
    }

    @Test
    public void testDelete() throws Exception {
        PackageInfo po = new PackageInfo();
        po.setPackageId(2L);
        log(packageInfoService.delete(po));
    }
}