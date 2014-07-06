package com.ifhz.core.service.cache.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.ifhz.core.adapter.ModelInfoAdapter;
import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.service.cache.ModelInfoCacheService;
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
 * Date: 2014/7/6
 * Time: 19:06
 */
@Service("modelInfoCacheService")
public class ModelInfoCacheServiceImpl implements ModelInfoCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelInfoCacheServiceImpl.class);

    private final Cache<String, ModelInfo> CACHE = createCache();

    @Resource(name = "modelInfoAdapter")
    private ModelInfoAdapter modelInfoAdapter;

    private Cache<String, ModelInfo> createCache() {
        return CacheBuilder.newBuilder().maximumSize(2000).expireAfterWrite(60, TimeUnit.MINUTES).build();
    }

    @Override
    public void cleanCache() {
        this.CACHE.invalidateAll();
    }

    @Override
    public CacheStats status() {
        return this.CACHE.stats();
    }


    @Override
    public ModelInfo getByUaAndGrouId(String ua, Long groupId) {
        Preconditions.checkArgument(StringUtils.isNotBlank(ua), "ua must be not empty");
        Preconditions.checkArgument(groupId != null, "groupId must be not empty");
        String keyCode = ua.trim() + "," + String.valueOf(groupId);
        ModelInfo ret = null;
        try {
            ret = CACHE.get(keyCode, new CacheCallable(keyCode));
        } catch (ExecutionException e) {
            LOGGER.error("getByUaAndGrouId error", e);
        }
        if (ret == null || ret.getModelId() == null) {
            return null;
        } else {
            return ret;
        }
    }

    @Override
    public void remove(String ua, Long groupId) {
        Preconditions.checkArgument(StringUtils.isNotBlank(ua), "ua must be not empty");
        Preconditions.checkArgument(groupId != null, "groupId must be not empty");

        String keyCode = ua.trim() + "," + String.valueOf(groupId);
        try {
            CACHE.invalidate(keyCode);
        } catch (Exception e) {
            LOGGER.error("remove error", e);
        }
    }


    /**
     *
     */
    private class CacheCallable implements Callable<ModelInfo> {

        private String keyCode;

        private CacheCallable(String keyCode) {
            this.keyCode = keyCode;
        }

        @Override
        public ModelInfo call() {
            ModelInfo result = null;
            try {
                if (StringUtils.isNotBlank(keyCode)) {
                    String[] str = StringUtils.split(keyCode, "\\,");
                    String ua = str[0];
                    String gid = str[1];
                    Long groupId = Long.parseLong(gid);
                    if (StringUtils.isNotBlank(ua) && groupId != null) {
                        result = modelInfoAdapter.getByGroupIdAndUa(groupId, ua);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("query ModelInfo Failure", e);
            } finally {
                LOGGER.info("result={}", JSON.toJSONString(result));
            }
            if (result == null) {
                result = new ModelInfo();
            }
            return result;
        }
    }
}
