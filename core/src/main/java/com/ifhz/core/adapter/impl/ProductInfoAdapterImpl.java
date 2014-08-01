package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.ProductInfoAdapter;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.ProductInfoMapper;
import com.ifhz.core.po.ProductInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
@Repository("productInfoAdapter")
public class ProductInfoAdapterImpl implements ProductInfoAdapter {

    @Resource(name = "productInfoMapper")
    private ProductInfoMapper productInfoMapper;


    @Override
    public ProductInfo getById(Long id) {
        return productInfoMapper.getById(id);
    }

    @Override
    public List<ProductInfo> queryByVo(Pagination page, ProductInfo record) {
        List<ProductInfo> result = productInfoMapper.queryByVo(page, record);
        return result == null ? Lists.<ProductInfo>newArrayList() : result;
    }

    @Override
    public int insert(ProductInfo record) {
        record.setCreateTime(new Date());
        return productInfoMapper.insert(record);
    }

    @Override
    public int update(ProductInfo record) {
        record.setUpdateTime(new Date());
        return productInfoMapper.update(record);
    }

    @Override
    public int delete(ProductInfo record) {
        record.setActive(JcywConstants.ACTIVE_D);
        record.setUpdateTime(new Date());
        return productInfoMapper.update(record);
    }

    @Override
    public List<ProductInfo> queryAllList() {
        return null;
    }
}
