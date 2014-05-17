package com.ifhz.core.mapper;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
public interface ProductInfoMapper {

    public ProductInfo getById(Long id);

    public List<ProductInfo> queryByVo(Pagination page, @Param(value = "record") ProductInfo record);

    public int insert(ProductInfo record);

    public int update(ProductInfo record);

    public int delete(ProductInfo record);
}