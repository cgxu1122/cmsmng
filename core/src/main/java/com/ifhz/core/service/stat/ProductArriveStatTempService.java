package com.ifhz.core.service.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.ProductArriveStat;
import com.ifhz.core.po.stat.ProductArriveStatTemp;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:51
 */
public interface ProductArriveStatTempService {

    public int insert(ProductArriveStat productArriveStat);

    public boolean syncProductArriveStat();

    public List<ProductArriveStatTemp> queryByVo(Pagination pagination, ProductArriveStatTemp record);

    public List<ProductArriveStatTemp> querySumByVo(Pagination pagination, ProductArriveStatTemp record);

    public ProductArriveStatTemp queryCountByVo(ProductArriveStatTemp record);
}
