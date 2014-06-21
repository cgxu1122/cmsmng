package com.ifhz.core.service.stat;

import com.ifhz.core.po.ProductStat;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 17:16
 */
public interface ProductStatUpdateService {

    public int insert(ProductStat record);

    public int update(ProductStat record);

    public ProductStat getByMd5Key(String md5Key);
}
