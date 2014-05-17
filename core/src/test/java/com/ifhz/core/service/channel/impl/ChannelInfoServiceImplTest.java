package com.ifhz.core.service.channel.impl;

import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.service.channel.ChannelInfoService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ChannelInfoServiceImplTest extends BaseTest {

    @Autowired
    private ChannelInfoService channelInfoService;

    @Test
    public void testGetById() throws Exception {
        log(channelInfoService.getById(21L));
    }

    @Test
    public void testQueryByVo() throws Exception {
        ChannelInfo po = new ChannelInfo();
        po.setActive("Y");
        log(channelInfoService.queryByVo(null, po));
    }

    @Test
    public void testInsert() throws Exception {
        ChannelInfo po = new ChannelInfo();
        po.setChannelName("北京仓");
        po.setDesc("beijingde");
        po.setGroupId(1L);
        po.setMngId(1L);
        po.setParentId(-1L);
        channelInfoService.insert(po);
        log(po.getChannelId());
    }

    @Test
    public void testUpdate() throws Exception {
        ChannelInfo po = new ChannelInfo();
        po.setChannelId(21L);
        po.setDesc("bxxe");
        po.setGroupId(1L);
        po.setMngId(2L);
        po.setUserId(1L);
        po.setLeaf("N");
        channelInfoService.update(po);
    }

    @Test
    public void testDelete() throws Exception {
        ChannelInfo po = new ChannelInfo();
        po.setChannelId(21L);
        channelInfoService.delete(po);
    }
}