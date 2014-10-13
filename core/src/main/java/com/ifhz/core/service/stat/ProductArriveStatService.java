package com.ifhz.core.service.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.stat.ProductArriveStat;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public interface ProductArriveStatService {

    public boolean statProductArrive(DataLog record);


    public List<ProductArriveStat> queryByVo(Pagination pagination, ProductArriveStat record);

    public List<ProductArriveStat> querySumByVo(Pagination pagination, ProductArriveStat record);

    public ProductArriveStat queryCountByVo(ProductArriveStat record);
}
