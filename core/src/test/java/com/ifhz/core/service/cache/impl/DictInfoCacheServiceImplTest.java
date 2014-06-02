package com.ifhz.core.service.cache.impl;

import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.DictInfo;
import com.ifhz.core.service.cache.DictInfoCacheService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class DictInfoCacheServiceImplTest extends BaseTest {

    @Autowired
    private DictInfoCacheService dictInfoCacheService;

    @Test
    public void testGetByKeyCode() throws Exception {
        DictInfo dictInfo = dictInfoCacheService.getByKeyCode(GlobalConstants.KEY_SYS_INIT_DATE);
        log(dictInfo);
    }

    @Test
    public void testGetSystemInitDate() throws Exception {
        Date date = dictInfoCacheService.getSystemInitDate();
        log(DateFormatUtils.formatYYYYMMDD(date));
    }
}