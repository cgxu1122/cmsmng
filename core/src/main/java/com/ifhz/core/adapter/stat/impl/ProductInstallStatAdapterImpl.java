package com.ifhz.core.adapter.stat.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.stat.ProductInstallStatAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.stat.ProductInstallStatMapper;
import com.ifhz.core.po.stat.ProductInstallStat;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/8
 * Time: 22:15
 */
@Repository
public class ProductInstallStatAdapterImpl implements ProductInstallStatAdapter {

    @Resource
    private ProductInstallStatMapper productInstallStatMapper;

    @Override
    public int insert(ProductInstallStat record) {
        return productInstallStatMapper.insert(record);
    }

    @Override
    public int update(ProductInstallStat record) {
        return productInstallStatMapper.update(record);
    }

    @Override
    public ProductInstallStat getById(Long id) {
        return productInstallStatMapper.getById(id);
    }

    @Override
    public ProductInstallStat getByMd5Key(String md5Key) {
        return productInstallStatMapper.getByMd5Key(md5Key);
    }

    @Override
    public List<ProductInstallStat> queryByVo(Pagination pagination, ProductInstallStat record) {
        List<ProductInstallStat> result = productInstallStatMapper.queryByVo(pagination, record);
        return result == null ? Lists.<ProductInstallStat>newArrayList() : result;
    }

    @Override
    public ProductInstallStat queryCountByVo(ProductInstallStat record) {
        return productInstallStatMapper.queryCountByVo(record);
    }
}
