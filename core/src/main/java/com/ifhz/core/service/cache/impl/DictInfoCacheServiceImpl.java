package com.ifhz.core.service.cache.impl;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.ifhz.core.adapter.DictInfoAdapter;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.DictInfo;
import com.ifhz.core.service.cache.DictInfoCacheService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 12:50
 */
@Service("dictInfoCacheService")
public class DictInfoCacheServiceImpl implements DictInfoCacheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DictInfoCacheServiceImpl.class);


    private final Cache<String, DictInfo> CACHE = createCache();

    @Resource(name = "dictInfoAdapter")
    private DictInfoAdapter dictInfoAdapter;


    private Cache<String, DictInfo> createCache() {
        return CacheBuilder.newBuilder().maximumSize(200).expireAfterWrite(60, TimeUnit.MINUTES).build();
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
    public DictInfo getByKeyCode(String keyCode) {
        Preconditions.checkArgument(StringUtils.isNotBlank(keyCode), "String keyCode must not be Null and Empty !");
        try {
            return this.CACHE.get(keyCode, new CacheCallable(keyCode));
        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public Date getSystemInitDate() {
        Date result = null;
        DictInfo po = getByKeyCode(GlobalConstants.KEY_SYS_INIT_DATE);
        if (po != null) {
            String keyValue = po.getKeyValue();
            result = DateFormatUtils.parse(keyValue, GlobalConstants.DATE_FORMAT_DPT);
        }

        return result;
    }


    /**
     *
     */
    private class CacheCallable implements Callable<DictInfo> {

        private String keyCode;

        private CacheCallable(String keyCode) {
            this.keyCode = keyCode;
        }

        @Override
        public DictInfo call() {
            DictInfo result = null;
            try {
                result = dictInfoAdapter.getByKeyCode(this.keyCode);
            } catch (Exception e) {
                LOGGER.error("query DictInfo Failure", e);
            }
            return result;
        }
    }


}
