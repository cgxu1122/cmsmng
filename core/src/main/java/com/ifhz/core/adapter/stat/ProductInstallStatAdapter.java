package com.ifhz.core.adapter.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.ProductInstallStat;

import java.util.Date;
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

    public int delete(Long id);

    public List<ProductInstallStat> queryForPage(Pagination pagination, Date startTime, Date endTime);

    public long queryForPageTotalCount(Date startTime, Date endTime);

    public ProductInstallStat getById(Long id);

    public ProductInstallStat getByMd5Key(String md5Key);

    public List<ProductInstallStat> queryByVo(Pagination pagination, ProductInstallStat record);

    public List<ProductInstallStat> querySumByVo(Pagination pagination, ProductInstallStat record);

    public ProductInstallStat queryCountByVo(ProductInstallStat record);
}
