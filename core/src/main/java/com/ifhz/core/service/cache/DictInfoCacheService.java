package com.ifhz.core.service.cache;

import com.ifhz.core.base.commons.cache.CommonCacheService;
import com.ifhz.core.po.DictInfo;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 12:49
 */
public interface DictInfoCacheService extends CommonCacheService {

    public DictInfo getByKeyCode(String keyCode);

    /**
     * 获取系统初始化时间
     *
     * @return
     */
    public Date getSystemInitDate();
}
