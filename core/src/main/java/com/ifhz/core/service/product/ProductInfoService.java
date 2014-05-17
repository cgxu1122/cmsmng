package com.ifhz.core.service.product;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ProductInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangjian
 */
public interface ProductInfoService {
    public ProductInfo getById(Long id);

    public List<ProductInfo> queryByVo(Pagination page, ProductInfo record);

    public int insert(ProductInfo record);

    public int update(ProductInfo record);

    public int delete(ProductInfo record);
}
