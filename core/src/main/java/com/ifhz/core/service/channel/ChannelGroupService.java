package com.ifhz.core.service.channel;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelGroup;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangjian
 */
public interface ChannelGroupService {
    public ChannelGroup getById(Long id);

    public List<ChannelGroup> queryByVo(Pagination page, ChannelGroup record);

    public int insert(ChannelGroup record);

    public int update(ChannelGroup record);

    public int delete(ChannelGroup record);
}
