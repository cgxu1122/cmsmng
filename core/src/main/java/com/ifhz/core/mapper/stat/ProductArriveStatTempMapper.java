package com.ifhz.core.mapper.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.ProductArriveStatTemp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:51
 */
public interface ProductArriveStatTempMapper {

    public int insert(ProductArriveStatTemp record);

    public ProductArriveStatTemp getById(Long id);

    public List<ProductArriveStatTemp> queryByVo(Pagination pagination, @Param("record") ProductArriveStatTemp record);

    public List<ProductArriveStatTemp> querySumByVo(Pagination pagination, @Param("record") ProductArriveStatTemp record);

    public ProductArriveStatTemp queryCountByVo(ProductArriveStatTemp record);
}
