package com.ifhz.core.adapter.stat.impl;

import com.ifhz.core.adapter.stat.ProductArriveStatTempAdapter;
import com.ifhz.core.mapper.stat.ProductArriveStatTempMapper;
import com.ifhz.core.po.stat.ProductArriveStatTemp;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:51
 */
public class ProductArriveStatTempAdapterImpl implements ProductArriveStatTempAdapter {

    private ProductArriveStatTempMapper productArriveStatTempMapper;

    @Override
    public int insert(ProductArriveStatTemp record) {
        return productArriveStatTempMapper.insert(record);
    }

    @Override
    public ProductArriveStatTemp getById(Long id) {
        return productArriveStatTempMapper.getById(id);
    }
}
