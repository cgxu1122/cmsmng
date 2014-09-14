package com.ifhz.core.adapter.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.ProductInstallStat;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/8
 * Time: 22:15
 */
public interface ProductInstallStatAdapter {

    public int insert(ProductInstallStat record);

    public int update(ProductInstallStat record);

    public ProductInstallStat getById(Long id);

    public ProductInstallStat getByMd5Key(String md5Key);

    public List<ProductInstallStat> queryByVo(Pagination pagination, ProductInstallStat record);

    public List<ProductInstallStat> querySumByVo(Pagination pagination, ProductInstallStat record);

    public ProductInstallStat queryCountByVo(ProductInstallStat record);
}
