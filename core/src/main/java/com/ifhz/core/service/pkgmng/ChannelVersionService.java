package com.ifhz.core.service.pkgmng;

import com.ifhz.core.po.ChannelVersion;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 19:08
 */
public interface ChannelVersionService {

    public ChannelVersion getByChannelId(Long channelId);

    public void insert(ChannelVersion po);

    public boolean update(ChannelVersion po);
}
