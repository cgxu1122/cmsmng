package com.ifhz.core.adapter.stat.impl;

import com.ifhz.core.adapter.stat.ProductInstallStatAdapter;
import com.ifhz.core.mapper.stat.ProductInstallStatMapper;
import com.ifhz.core.po.stat.ProductInstallStat;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

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
}
