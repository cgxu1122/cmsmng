package com.ifhz.core.service.product.impl;

import com.ifhz.core.adapter.ProductInfoAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ProductInfo;
import com.ifhz.core.service.cache.ProductInfoCacheService;
import com.ifhz.core.service.product.ProductInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service("productInfoService")
public class ProductInfoServiceImpl implements ProductInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInfoServiceImpl.class);

    @Resource(name = "productInfoAdapter")
    private ProductInfoAdapter productInfoAdapter;
    @Resource(name = "productInfoCacheService")
    private ProductInfoCacheService productInfoCacheService;


    @Override
    @Log
    public ProductInfo getById(Long id) {
        return productInfoAdapter.getById(id);
    }

    @Override
    @Log
    public List<ProductInfo> queryByVo(Pagination page, ProductInfo record) {
        return productInfoAdapter.queryByVo(page, record);
    }

    @Override
    @Log
    public List<ProductInfo> queryByVoForStat(Pagination page, ProductInfo record) {
        return productInfoAdapter.queryByVoForStat(page, record);
    }

    @Override
    @Log
    public int insert(ProductInfo record) {
        return productInfoAdapter.insert(record);
    }

    @Override
    @Log
    public int update(ProductInfo record) {
        return productInfoAdapter.update(record);
    }

    @Override
    @Log
    public int delete(ProductInfo record) {
        int ret = productInfoAdapter.delete(record);
        if (ret == 1) {
            productInfoCacheService.remove(record.getProductId());
        }
        return ret;
    }

    @Override
    @Log
    public Date getMaxQueryDateByPartnerId(Long partnerId) {
        Date date = productInfoAdapter.getMaxQueryDateByPartnerId(partnerId);
        if (date == null) {
            date = new Date();
        }
        return date;
    }
}
