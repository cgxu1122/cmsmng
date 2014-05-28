package com.ifhz.core.mapper;

import com.ifhz.core.po.ProductCount;

import java.util.List;

/**
 * Created by lm on 14-5-21.
 * liming
 */
public interface ProductCountMapper {
    public ProductCount getById(Long id);

    public List<ProductCount> queryByVo(ProductCount record);

    public int insert(ProductCount record);

    public int update(ProductCount record);

    public int delete(ProductCount record);

    public ProductCount getByProcessKey(String processKey);
}
