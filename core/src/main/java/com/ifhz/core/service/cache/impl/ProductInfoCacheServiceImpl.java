package com.ifhz.core.service.cache.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.ifhz.core.adapter.ProductInfoAdapter;
import com.ifhz.core.po.ProductInfo;
import com.ifhz.core.service.cache.ProductInfoCacheService;
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
 * Date: 2014/7/13
 * Time: 23:13
 */
@Service("productInfoCacheService")
public class ProductInfoCacheServiceImpl implements ProductInfoCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInfoCacheServiceImpl.class);

    private final Cache<Long, ProductInfo> CACHE = createCache();

    @Resource(name = "productInfoAdapter")
    private ProductInfoAdapter productInfoAdapter;

    private Cache<Long, ProductInfo> createCache() {
        return CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(60, TimeUnit.MINUTES).build();
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
    public ProductInfo getById(Long productId) {
        Preconditions.checkArgument(productId != null, "productId must be not empty");

        ProductInfo ret = null;
        try {
            ret = CACHE.get(productId, new CacheCallable(productId));
        } catch (ExecutionException e) {
            LOGGER.error("getById error", e);
        }
        if (ret == null || ret.getProductId() == null) {
            return null;
        }

        return ret;
    }

    @Override
    public void remove(Long productId) {
        Preconditions.checkArgument(productId != null, "productId must be not empty");
        try {
            CACHE.invalidate(productId);
        } catch (Exception e) {
            LOGGER.error("remove error", e);
        }
    }


    /**
     *
     */
    private class CacheCallable implements Callable<ProductInfo> {

        private Long keyCode;

        private CacheCallable(Long keyCode) {
            this.keyCode = keyCode;
        }

        @Override
        public ProductInfo call() {
            ProductInfo result = null;
            try {
                if (keyCode != null) {
                    result = productInfoAdapter.getById(keyCode);
                }
            } catch (Exception e) {
                LOGGER.error("query ProductInfo Failure", e);
            } finally {
                LOGGER.info("result={}", JSON.toJSONString(result));
            }
            if (result == null) {
                result = new ProductInfo();
            }
            return result;
        }
    }
}
