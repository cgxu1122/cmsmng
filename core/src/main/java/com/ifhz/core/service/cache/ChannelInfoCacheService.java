package com.ifhz.core.service.cache;

import com.google.common.cache.CacheStats;
import com.ifhz.core.po.ChannelInfo;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 18:49
 */
public interface ChannelInfoCacheService {


    public ChannelInfo getByChannelId(Long channelId) throws Exception;

    /**
     * 清空Cache数据
     */
    public void cleanCache();

    /**
     * 查询Cache状态
     *
     * @return
     */
    public CacheStats status();


    /**
     * 清空指定key 的数据
     */
    public void remove(Long channelId) throws Exception;

}
