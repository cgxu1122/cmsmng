package com.ifhz.core.adapter;

import com.ifhz.core.po.ChannelVersion;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 19:08
 */
public interface ChannelVersionAdapter {

    public ChannelVersion getByChannelId(Long channelId);

    public void insert(ChannelVersion po);

    public int update(ChannelVersion po);
}
