package com.ifhz.core.mapper;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
public interface ChannelInfoMapper {

    public ChannelInfo getById(Long id);

    public ChannelInfo getByUserId(Long id);

    public List<ChannelInfo> queryByVo(Pagination page, @Param(value = "record") ChannelInfo record);

    public List<ChannelInfo> queryByVoForStat(Pagination page, @Param(value = "record") ChannelInfo record);

    public int insert(ChannelInfo record);

    public int update(ChannelInfo record);

    public int delete(ChannelInfo record);

    public ChannelInfo getChannelInfoByUserId(Long userId);
}
