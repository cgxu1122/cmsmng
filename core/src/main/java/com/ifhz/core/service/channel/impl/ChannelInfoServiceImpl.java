package com.ifhz.core.service.channel.impl;

import com.ifhz.core.adapter.ChannelInfoAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.service.channel.ChannelInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service("channelInfoService")
public class ChannelInfoServiceImpl implements ChannelInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelInfoServiceImpl.class);

    @Resource(name = "channelInfoAdapter")
    private ChannelInfoAdapter channelInfoAdapter;


    @Override
    public ChannelInfo getById(Long id) {
        return channelInfoAdapter.getById(id);
    }

    @Override
    public List<ChannelInfo> queryByVo(Pagination page, ChannelInfo record) {
        return channelInfoAdapter.queryByVo(page, record);
    }

    @Override
    public int insert(ChannelInfo record) {
        return channelInfoAdapter.insert(record);
    }

    @Override
    public int update(ChannelInfo record) {
        return channelInfoAdapter.update(record);
    }

    @Override
    public int delete(ChannelInfo record) {
        return channelInfoAdapter.delete(record);
    }
}
