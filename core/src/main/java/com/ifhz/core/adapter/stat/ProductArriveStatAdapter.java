package com.ifhz.core.adapter.stat;

import com.ifhz.core.po.stat.ProductArriveStat;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public interface ProductArriveStatAdapter {

    public int insert(ProductArriveStat record);

    public int update(ProductArriveStat record);

    public ProductArriveStat getById(Long id);

    public ProductArriveStat getByMd5Key(String md5Key);
}
