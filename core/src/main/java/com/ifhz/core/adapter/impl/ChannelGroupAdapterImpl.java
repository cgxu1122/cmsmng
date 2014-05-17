package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.ChannelGroupAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.ChannelGroupMapper;
import com.ifhz.core.po.ChannelGroup;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User:yangjian
 */
@Repository("channelGroupAdapter")
public class ChannelGroupAdapterImpl implements ChannelGroupAdapter {

    @Resource(name = "channelGroupMapper")
    private ChannelGroupMapper channelGroupMapper;


    @Override
    public ChannelGroup getById(Long id) {
        return channelGroupMapper.getById(id);
    }

    @Override
    public List<ChannelGroup> queryByVo(Pagination page, ChannelGroup record) {
        List<ChannelGroup> result = channelGroupMapper.queryByVo(page, record);
        return result == null ? Lists.<ChannelGroup>newArrayList() : result;
    }

    @Override
    public int insert(ChannelGroup record) {
        return channelGroupMapper.insert(record);
    }

    @Override
    public int update(ChannelGroup record) {
        return channelGroupMapper.update(record);
    }

    @Override
    public int delete(ChannelGroup record) {
        return channelGroupMapper.delete(record);
    }
}
