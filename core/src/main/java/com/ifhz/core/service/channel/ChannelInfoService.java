package com.ifhz.core.service.channel;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangjian
 */
public interface ChannelInfoService {
    public ChannelInfo getById(Long id);

    public List<ChannelInfo> queryByVo(Pagination page, ChannelInfo record);

    public int insert(ChannelInfo record);

    public int update(ChannelInfo record);

    public int delete(ChannelInfo record);

    public ChannelInfo getChannelInfoByUserId(Long userId);
}
