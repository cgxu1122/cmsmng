package com.ifhz.core.adapter.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.ProductArriveStat;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public interface ProductArriveStatAdapter {

    public int insert(ProductArriveStat record);

    public int update(ProductArriveStat record);

    public ProductArriveStat getById(Long id);

    public ProductArriveStat getByMd5Key(String md5Key);

    public List<ProductArriveStat> queryByVo(Pagination pagination, ProductArriveStat record);

    public ProductArriveStat queryCountByVo(ProductArriveStat record);
}
