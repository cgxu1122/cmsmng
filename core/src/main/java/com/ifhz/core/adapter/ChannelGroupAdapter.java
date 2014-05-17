package com.ifhz.core.adapter;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelGroup;

import java.util.List;

/**
 * 类描述
 * User:yangjian
 */
public interface ChannelGroupAdapter {

    public ChannelGroup getById(Long id);

    public List<ChannelGroup> queryByVo(Pagination page, ChannelGroup record);

    public int insert(ChannelGroup record);

    public int update(ChannelGroup record);

    public int delete(ChannelGroup record);
}
