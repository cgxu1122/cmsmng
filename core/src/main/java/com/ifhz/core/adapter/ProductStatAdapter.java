package com.ifhz.core.adapter;

import com.ifhz.core.po.ProductStat;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 11:46
 */
public interface ProductStatAdapter {

    public int insert(ProductStat record);

    public int update(ProductStat record);

    public ProductStat getById(Long id);

    public ProductStat getByMd5Key(String md5Key);

    public List<ProductStat> queryListByQueryKey(String queryKey, Date startTime, Date endTime);
}
