package com.ifhz.core.service.channel.impl;

import com.ifhz.core.adapter.ChannelGroupAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelGroup;
import com.ifhz.core.service.channel.ChannelGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service("channelGroupService")
public class ChannelGroupServiceImpl implements ChannelGroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelGroupServiceImpl.class);


    @Resource(name = "channelGroupAdapter")
    private ChannelGroupAdapter channelGroupAdapter;


    @Override
    @Log
    public ChannelGroup getById(Long id) {
        return channelGroupAdapter.getById(id);
    }

    @Override
    @Log
    public List<ChannelGroup> queryByVo(Pagination page, ChannelGroup record) {
        return channelGroupAdapter.queryByVo(page, record);
    }

    @Override
    @Log
    public int insert(ChannelGroup record) {
        return channelGroupAdapter.insert(record);
    }

    @Override
    @Log
    public int update(ChannelGroup record) {
        return channelGroupAdapter.update(record);
    }

    @Override
    @Log
    public int delete(ChannelGroup record) {
        return channelGroupAdapter.delete(record);
    }
}
