package com.ifhz.core.mapper.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.ProductInstallStat;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/8
 * Time: 22:15
 */
public interface ProductInstallStatMapper {

    public int insert(ProductInstallStat record);

    public int update(ProductInstallStat record);

    public int delete(Long id);

    public List<ProductInstallStat> queryForPage(Pagination pagination, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    public long queryForPageTotalCount(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    public ProductInstallStat getById(Long id);

    public ProductInstallStat getByMd5Key(String md5Key);

    public List<ProductInstallStat> queryByVo(Pagination pagination, @Param("record") ProductInstallStat record);

    public List<ProductInstallStat> querySumByVo(Pagination pagination, @Param("record") ProductInstallStat record);

    public ProductInstallStat queryCountByVo(ProductInstallStat record);
}
