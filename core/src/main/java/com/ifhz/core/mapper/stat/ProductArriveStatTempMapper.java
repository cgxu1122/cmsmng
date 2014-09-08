package com.ifhz.core.mapper.stat;

import com.ifhz.core.po.stat.ProductArriveStatTemp;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:51
 */
public interface ProductArriveStatTempMapper {

    public int insert(ProductArriveStatTemp record);

    public ProductArriveStatTemp getById(Long id);
}
