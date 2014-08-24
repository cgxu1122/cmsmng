package com.ifhz.core.adapter.stat.impl;

import com.ifhz.core.adapter.stat.ProductArriveStatAdapter;
import com.ifhz.core.mapper.stat.ProductArriveStatMapper;
import com.ifhz.core.po.stat.ProductArriveStat;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public class ProductArriveStatAdapterImpl implements ProductArriveStatAdapter {

    private ProductArriveStatMapper productArriveStatMapper;

    @Override
    public int insert(ProductArriveStat record) {
        return productArriveStatMapper.insert(record);
    }

    @Override
    public int update(ProductArriveStat record) {
        return productArriveStatMapper.update(record);
    }

    @Override
    public ProductArriveStat getById(Long id) {
        return productArriveStatMapper.getById(id);
    }

    @Override
    public ProductArriveStat getByMd5Key(String md5Key) {
        return productArriveStatMapper.getByMd5Key(md5Key);
    }
}
