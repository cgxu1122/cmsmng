package com.ifhz.core.adapter.stat.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.stat.ProductArriveStatTempAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.stat.ProductArriveStatTempMapper;
import com.ifhz.core.po.stat.ProductArriveStatTemp;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public List<ProductArriveStatTemp> queryByVo(Pagination pagination, ProductArriveStatTemp record) {
        return productArriveStatTempMapper.queryByVo(pagination, record);
    }

    @Override
    public List<ProductArriveStatTemp> querySumByVo(Pagination pagination, ProductArriveStatTemp record) {
        List<ProductArriveStatTemp> result = productArriveStatTempMapper.queryByVo(pagination, record);
        return result == null ? Lists.<ProductArriveStatTemp>newArrayList() : result;
    }

    @Override
    public ProductArriveStatTemp queryCountByVo(ProductArriveStatTemp record) {
        return productArriveStatTempMapper.queryCountByVo(record);
    }
}
