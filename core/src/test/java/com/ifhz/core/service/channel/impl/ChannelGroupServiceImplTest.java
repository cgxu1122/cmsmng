package com.ifhz.core.service.channel.impl;

import com.ifhz.core.po.ChannelGroup;
import com.ifhz.core.service.channel.ChannelGroupService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ChannelGroupServiceImplTest extends BaseTest {


    @Autowired
    private ChannelGroupService channelGroupService;

    @Test
    public void testGetById() throws Exception {
        log(channelGroupService.getById(1L));
    }

    @Test
    public void testQueryByVo() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {
        ChannelGroup po = new ChannelGroup();
        po.setGroupName("天音渠道");
        channelGroupService.insert(po);
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }
}