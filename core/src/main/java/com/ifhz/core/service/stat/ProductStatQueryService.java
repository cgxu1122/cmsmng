package com.ifhz.core.service.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ProductStat;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 17:17
 */
public interface ProductStatQueryService {
    public List<ProductStat> queryByVo(Pagination page, ProductStat record);

    public List<ProductStat> querySumByVo(Pagination page, ProductStat record);

    public ProductStat queryCountByVo(ProductStat record);
}
