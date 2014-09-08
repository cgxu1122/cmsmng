package com.ifhz.core.mapper.stat;

import com.ifhz.core.po.stat.ProductInstallStat;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/8
 * Time: 22:15
 */
public interface ProductInstallStatMapper {

    public int insert(ProductInstallStat record);

    public int update(ProductInstallStat record);

    public ProductInstallStat getById(Long id);

    public ProductInstallStat getByMd5Key(String md5Key);
}
