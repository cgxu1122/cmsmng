package com.ifhz.core.adapter.stat.impl;

import com.ifhz.core.adapter.stat.ProductArriveStatTempAdapter;
import com.ifhz.core.mapper.stat.ProductArriveStatTempMapper;
import com.ifhz.core.po.stat.ProductArriveStatTemp;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:51
 */
@Repository
public class ProductArriveStatTempAdapterImpl implements ProductArriveStatTempAdapter {

    @Resource
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
