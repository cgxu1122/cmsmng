package com.ifhz.core.service.cache;

import com.ifhz.core.base.commons.cache.CommonCacheService;
import com.ifhz.core.po.ChannelGroup;

/**
 * 类描述
 * User: yangjian
 */
public interface ChannelGroupCacheService extends CommonCacheService {

    public ChannelGroup getByKeyCode(String keyCode);

}
