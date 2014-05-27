package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.ProductCountAdapter;
import com.ifhz.core.mapper.ProductCountMapper;
import com.ifhz.core.po.ProductCount;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lm on 14-5-21.
 */
@Repository("productCountAdapter")
public class ProductCountAdapterImpl implements ProductCountAdapter {
    @Resource(name = "productCountMapper")
    private ProductCountMapper productCountMapper;

    @Override
    public ProductCount getById(Long id) {
        return productCountMapper.getById(id);
    }

    @Override
    public List<ProductCount> queryByVo(ProductCount record) {
        List<ProductCount> result = productCountMapper.queryByVo(record);
        return result == null ? Lists.<ProductCount>newArrayList() : result;
    }

    @Override
    public int insert(ProductCount record) {
        return productCountMapper.insert(record);
    }

    @Override
    public int update(ProductCount record) {
        return productCountMapper.update(record);
    }

    @Override
    public int delete(ProductCount record) {
        return productCountMapper.delete(record);
    }
}
