package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.ProductStatAdapter;
import com.ifhz.core.po.ProductStat;
import com.ifhz.core.service.stat.ProductStatUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 17:16
 */
@Service("productStatUpdateService")
public class ProductStatUpdateServiceImpl implements ProductStatUpdateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductStatUpdateServiceImpl.class);

    @Resource(name = "productStatAdapter")
    private ProductStatAdapter productStatAdapter;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(ProductStat record) {
        return productStatAdapter.insert(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(ProductStat record) {
        return productStatAdapter.update(record);
    }

    @Override
    public ProductStat getByMd5Key(String md5Key) {
        return productStatAdapter.getByMd5Key(md5Key);
    }
}
