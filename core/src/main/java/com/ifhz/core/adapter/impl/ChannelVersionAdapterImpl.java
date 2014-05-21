package com.ifhz.core.adapter.impl;

import com.ifhz.core.adapter.ChannelVersionAdapter;
import com.ifhz.core.mapper.ChannelVersionMapper;
import com.ifhz.core.po.ChannelVersion;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 19:59
 */
@Service("channelVersionAdapter")
public class ChannelVersionAdapterImpl implements ChannelVersionAdapter {

    @Resource(name = "channelVersionMapper")
    private ChannelVersionMapper channelVersionMapper;

    @Override
    public ChannelVersion getByChannelId(Long channelId) {
        return channelVersionMapper.getByChannelId(channelId);
    }

    @Override
    public void insert(ChannelVersion po) {
        channelVersionMapper.insert(po);
    }

    @Override
    public int update(ChannelVersion po) {
        return channelVersionMapper.update(po);
    }
}
