package com.ifhz.core.service.cache.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.ifhz.core.adapter.ChannelInfoAdapter;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 19:20
 */
@Service("channelInfoCacheService")
public class ChannelInfoCacheServiceImpl implements ChannelInfoCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelInfoCacheServiceImpl.class);

    private final Cache<Long, ChannelInfo> CACHE = createCache();

    @Resource(name = "channelInfoAdapter")
    private ChannelInfoAdapter channelInfoAdapter;

    private Cache<Long, ChannelInfo> createCache() {
        return CacheBuilder.newBuilder().maximumSize(3000).expireAfterWrite(60, TimeUnit.MINUTES).build();
    }

    @Override
    public ChannelInfo getByChannelId(Long channelId) throws Exception {
        Preconditions.checkArgument(channelId != null, "channelId must be not null");
        return CACHE.get(channelId, new CacheCallable(channelId));
    }

    @Override
    public void remove(Long channelId) throws Exception {
        Preconditions.checkArgument(channelId != null, "channelId must be not null");
        CACHE.invalidate(channelId);
    }


    @Override
    public void cleanCache() {
        this.CACHE.invalidateAll();
    }

    @Override
    public CacheStats status() {
        return this.CACHE.stats();
    }


    /**
     *
     */
    private class CacheCallable implements Callable<ChannelInfo> {

        private Long keyCode;

        private CacheCallable(Long keyCode) {
            this.keyCode = keyCode;
        }

        @Override
        public ChannelInfo call() {
            ChannelInfo result = null;
            try {
                if (keyCode != null) {
                    channelInfoAdapter.getById(keyCode);
                }
            } catch (Exception e) {
                LOGGER.error("query ChannelInfo Failure", e);
            } finally {
                LOGGER.info("result={}", JSON.toJSONString(result));
            }
            return result;
        }
    }
}
