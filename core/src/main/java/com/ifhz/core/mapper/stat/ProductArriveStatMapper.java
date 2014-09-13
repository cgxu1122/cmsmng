package com.ifhz.core.mapper.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.ProductArriveStat;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public interface ProductArriveStatMapper {

    public int insert(ProductArriveStat record);

    public int update(ProductArriveStat record);

    public ProductArriveStat getById(Long id);

    public ProductArriveStat getByMd5Key(String md5Key);

    public List<ProductArriveStat> queryByVo(Pagination pagination, @Param("record") ProductArriveStat record);

    public ProductArriveStat queryCountByVo(ProductArriveStat record);

    public long queryTotalCount(@Param(value = "startTime") Date startTime,
                                @Param(value = "endTime") Date endTime);

    public List<ProductArriveStat> queryStatList(Pagination pagination,
                                                 @Param(value = "startTime") Date startTime,
                                                 @Param(value = "endTime") Date endTime);

}
