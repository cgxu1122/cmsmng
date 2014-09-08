package com.ifhz.core.adapter.stat;

import com.ifhz.core.po.stat.ProductArriveStatTemp;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:51
 */
public interface ProductArriveStatTempAdapter {

    public int insert(ProductArriveStatTemp record);

    public ProductArriveStatTemp getById(Long id);
}
