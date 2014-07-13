package com.ifhz.core.service.cache;

import com.google.common.cache.CacheStats;
import com.ifhz.core.po.ProductInfo;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/13
 * Time: 23:12
 */
public interface ProductInfoCacheService {

    public ProductInfo getById(Long productId);

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
    public void remove(Long productId);
}
