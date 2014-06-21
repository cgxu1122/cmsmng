package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.ChannelInfoAdapter;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.ChannelInfoMapper;
import com.ifhz.core.po.ChannelInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
@Repository("channelInfoAdapter")
public class ChannelInfoAdapterImpl implements ChannelInfoAdapter {

    @Resource(name = "channelInfoMapper")
    private ChannelInfoMapper channelInfoMapper;


    @Override
    public ChannelInfo getById(Long id) {
        return channelInfoMapper.getById(id);
    }

    @Override
    public List<ChannelInfo> queryByVo(Pagination page, ChannelInfo record) {
        List<ChannelInfo> result = channelInfoMapper.queryByVo(page, record);
        return result == null ? Lists.<ChannelInfo>newArrayList() : result;
    }

    @Override
    public int insert(ChannelInfo record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        return channelInfoMapper.insert(record);
    }

    @Override
    public int update(ChannelInfo record) {
        record.setUpdateTime(new Date());
        return channelInfoMapper.update(record);
    }

    @Override
    public int delete(ChannelInfo record) {
        record.setActive(JcywConstants.ACTIVE_D);
        record.setUpdateTime(new Date());
        return channelInfoMapper.update(record);
    }
}
