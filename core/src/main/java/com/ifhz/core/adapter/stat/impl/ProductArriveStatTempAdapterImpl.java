package com.ifhz.core.adapter.stat.impl;

import com.ifhz.core.adapter.stat.ProductArriveStatTempAdapter;
import com.ifhz.core.constants.Active;
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
        record.setActive(Active.Y.dbValue);
        return productArriveStatTempMapper.insert(record);
    }

    @Override
    public int update(ProductArriveStatTemp record) {
        return productArriveStatTempMapper.update(record);
    }

    @Override
    public int updateByManual(ProductArriveStatTemp record) {
        record.setActive(Active.N.dbValue);
        return productArriveStatTempMapper.update(record);
    }

    @Override
    public ProductArriveStatTemp getById(Long id) {
        return productArriveStatTempMapper.getById(id);
    }
}
