package com.ifhz.core.service.statistics;

import com.ifhz.core.po.ProductCount;

import java.util.List;

/**
 * Created by lm on 14-5-21.
 */
public interface ProductCountService {
    public ProductCount getById(Long id);

    public List<ProductCount> queryByVo(ProductCount record);

    public int insert(ProductCount record);

    public int update(ProductCount record);

    public int delete(ProductCount record);
}
