package com.ifhz.core.service.pkgmng.impl;

import com.ifhz.core.adapter.ChannelVersionAdapter;
import com.ifhz.core.po.ChannelVersion;
import com.ifhz.core.service.pkgmng.ChannelVersionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 20:13
 */
@Service("channelVersionService")
public class ChannelVersionServiceImpl implements ChannelVersionService {

    @Resource(name = "channelVersionAdapter")
    private ChannelVersionAdapter channelVersionAdapter;

    @Override
    public ChannelVersion getByChannelId(Long channelId) {
        return channelVersionAdapter.getByChannelId(channelId);
    }

    @Override
    public void insert(ChannelVersion po) {
        channelVersionAdapter.insert(po);
    }

    @Override
    public boolean update(ChannelVersion po) {
        return channelVersionAdapter.update(po) == 1 ? true : false;
    }
}
