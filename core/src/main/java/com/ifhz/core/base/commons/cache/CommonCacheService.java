package com.ifhz.core.base.commons.cache;

import com.google.common.cache.CacheStats;

/**
 * 类描述
 *
 * @author chenggang.xu@qunar.com created at 14-2-14 下午2:44
 * @version 1.0.0
 */
public interface CommonCacheService {

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


}
