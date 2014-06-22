package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.ProductStatAdapter;
import com.ifhz.core.mapper.ProductStatMapper;
import com.ifhz.core.po.ProductStat;
import com.ifhz.core.service.api.bean.StatUpdateBean;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 16:53
 */
@Repository("productStatAdapter")
public class ProductStatAdapterImpl implements ProductStatAdapter {

    @Resource(name = "productStatMapper")
    private ProductStatMapper productStatMapper;

    @Override
    public int insert(ProductStat record) {
        return productStatMapper.insert(record);
    }

    @Override
    public int update(ProductStat record) {
        return productStatMapper.update(record);
    }

    @Override
    public ProductStat getById(Long id) {
        return productStatMapper.getById(id);
    }

    @Override
    public ProductStat getByMd5Key(String md5Key) {
        return productStatMapper.getByMd5Key(md5Key);
    }

    @Override
    public List<ProductStat> queryListByQueryKey(String queryKey, Date startTime, Date endTime) {
        List<ProductStat> list = productStatMapper.queryListByQueryKey(queryKey, startTime, endTime);
        return list == null ? Lists.<ProductStat>newArrayList() : list;
    }

    @Override
    public int updateStat(StatUpdateBean statUpdateBean) {
        return productStatMapper.updateStat(statUpdateBean);
    }
}
