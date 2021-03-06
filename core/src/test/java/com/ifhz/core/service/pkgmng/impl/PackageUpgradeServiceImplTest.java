package com.ifhz.core.service.pkgmng.impl;

import com.ifhz.core.service.pkgmng.PackageUpgradeService;
import com.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

public class PackageUpgradeServiceImplTest extends BaseTest {

    @Resource
    private PackageUpgradeService packageUpgradeService;

    @Test
    public void testQueryNormalPkgList() throws Exception {
        Long groupId = 1L;
        Long channelId = 46L;
        Calendar calendar = Calendar.getInstance();
        Date startTime = new Date(1405820764343L);
        Date endTime = new Date(1405820764343L);


        packageUpgradeService.queryNormalPkgList(groupId, channelId, true, startTime, endTime);
    }

    @Test
    public void testQueryCommonPkgList() throws Exception {
        Long groupId = 1L;
        Long channelId = 2L;
        Calendar calendar = Calendar.getInstance();
        Date startTime = calendar.getTime();
        Date endTime = calendar.getTime();
        packageUpgradeService.queryCommonPkgList(groupId, channelId, startTime, endTime);
    }

    @Test
    public void testQueryApkList() throws Exception {
        Long groupId = 1L;
        Long channelId = 1L;
        Calendar calendar = Calendar.getInstance();
        Date startTime = calendar.getTime();
        Date endTime = calendar.getTime();
        packageUpgradeService.queryApkList(startTime, endTime);
    }

    @Test
    public void testQueryApkList1() throws Exception {
        Long groupId = 1L;
        Long channelId = 1L;
        Calendar calendar = Calendar.getInstance();
        Date startTime = calendar.getTime();
        Date endTime = calendar.getTime();
        packageUpgradeService.queryApkList(groupId, channelId, startTime, endTime);
    }
}