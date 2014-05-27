package com.ifhz.core.service.statistics.impl;

import com.ifhz.core.adapter.ProductCountAdapter;
import com.ifhz.core.po.ProductCount;
import com.ifhz.core.service.statistics.ProductCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lm on 14-5-21.
 */
@Service("productCountService")
public class ProductCountServiceImpl implements ProductCountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCountServiceImpl.class);
    @Resource(name = "productCountAdapter")
    private ProductCountAdapter productCountAdapter;

    @Override
    public ProductCount getById(Long id) {
        return productCountAdapter.getById(id);
    }

    @Override
    public List<ProductCount> queryByVo(ProductCount record) {
        return productCountAdapter.queryByVo(record);
    }

    @Override
    public int insert(ProductCount record) {
        return productCountAdapter.insert(record);
    }

    @Override
    public int update(ProductCount record) {
        return productCountAdapter.update(record);
    }

    @Override
    public int delete(ProductCount record) {
        return productCountAdapter.delete(record);
    }
}
