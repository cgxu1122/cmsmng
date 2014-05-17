package com.ifhz.core.mapper;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
public interface ChannelGroupMapper {

    public ChannelGroup getById(Long id);

    public List<ChannelGroup> queryByVo(Pagination page, @Param(value = "record") ChannelGroup record);

    public int insert(ChannelGroup record);

    public int update(ChannelGroup record);

    public int delete(ChannelGroup record);
}
