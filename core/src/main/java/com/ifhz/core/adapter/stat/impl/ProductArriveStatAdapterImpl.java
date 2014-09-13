package com.ifhz.core.adapter.stat.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.stat.ProductArriveStatAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.stat.ProductArriveStatMapper;
import com.ifhz.core.po.stat.ProductArriveStat;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
@Repository
public class ProductArriveStatAdapterImpl implements ProductArriveStatAdapter {

    @Resource
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

    @Override
    public List<ProductArriveStat> queryByVo(Pagination pagination, ProductArriveStat record) {
        List<ProductArriveStat> result = productArriveStatMapper.queryByVo(pagination, record);
        return result == null ? Lists.<ProductArriveStat>newArrayList() : result;
    }

    @Override
    public ProductArriveStat queryCountByVo(ProductArriveStat record) {
        return productArriveStatMapper.queryCountByVo(record);
    }

    @Override
    public long queryTotalCount(Date startTime, Date endTime) {
        return productArriveStatMapper.queryTotalCount(startTime, endTime);
    }

    @Override
    public List<ProductArriveStat> queryStatList(Pagination pagination, Date startTime, Date endTime) {
        return productArriveStatMapper.queryStatList(pagination, startTime, endTime);
    }
}
