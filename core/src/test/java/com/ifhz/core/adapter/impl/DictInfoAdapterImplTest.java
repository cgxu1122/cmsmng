package com.ifhz.core.adapter.impl;

import com.ifhz.core.adapter.DictInfoAdapter;
import com.ifhz.core.constants.GlobalConstants;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DictInfoAdapterImplTest extends BaseTest {

    @Autowired
    private DictInfoAdapter dictInfoAdapter;

    @Test
    public void testInsert() throws Exception {

    }

    @Test
    public void testGetByKeyCode() throws Exception {
        log(dictInfoAdapter.getByKeyCode(GlobalConstants.KEY_SYS_INIT_DATE));
    }
}