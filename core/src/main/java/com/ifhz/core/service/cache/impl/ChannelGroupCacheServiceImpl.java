package com.ifhz.core.service.cache.impl;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.ifhz.core.adapter.ChannelGroupAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.po.ChannelGroup;
import com.ifhz.core.service.cache.ChannelGroupCacheService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 12:50
 */
@Service("channelGroupCacheService")
public class ChannelGroupCacheServiceImpl implements ChannelGroupCacheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelGroupCacheServiceImpl.class);


    private final Cache<String, ChannelGroup> CACHE = createCache();

    @Resource(name = "channelGroupAdapter")
    private ChannelGroupAdapter channelGroupAdapter;


    private Cache<String, ChannelGroup> createCache() {
        return CacheBuilder.newBuilder().maximumSize(200).expireAfterWrite(60, TimeUnit.MINUTES).build();
    }

    @Override
    @Log
    public void cleanCache() {
        this.CACHE.invalidateAll();
    }

    @Override
    @Log
    public CacheStats status() {
        return this.CACHE.stats();
    }

    @Override
    @Log
    public ChannelGroup getByKeyCode(String keyCode) {
        Preconditions.checkArgument(StringUtils.isNotBlank(keyCode), "String keyCode must not be Null and Empty !");
        try {
            return this.CACHE.get(keyCode, new CacheCallable(keyCode));
        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     *
     */
    private class CacheCallable implements Callable<ChannelGroup> {

        private String keyCode;

        private CacheCallable(String keyCode) {
            this.keyCode = keyCode;
        }

        @Override
        @Log
        public ChannelGroup call() {
            ChannelGroup result = null;
            try {
                result = channelGroupAdapter.getById(Long.parseLong(this.keyCode));
            } catch (Exception e) {
                LOGGER.error("query ChannelGroup Failure", e);
            }
            return result;
        }
    }


}
