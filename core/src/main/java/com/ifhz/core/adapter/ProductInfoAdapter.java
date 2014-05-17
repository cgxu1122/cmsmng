package com.ifhz.core.adapter;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ProductInfo;

import java.util.List;

/**
 * 类描述
 * User:yangjian
 */
public interface ProductInfoAdapter {

    public ProductInfo getById(Long id);

    public List<ProductInfo> queryByVo(Pagination page, ProductInfo record);

    public int insert(ProductInfo record);

    public int update(ProductInfo record);

    public int delete(ProductInfo record);
}
