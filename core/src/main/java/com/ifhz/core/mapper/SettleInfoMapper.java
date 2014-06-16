package com.ifhz.core.mapper;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.SettleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
public interface SettleInfoMapper {

    public SettleInfo getById(@Param(value = "settleId") Long settleId);

    public List<SettleInfo> queryByVo(Pagination page, @Param(value = "record") SettleInfo record);

    public int insert(SettleInfo record);

    public int update(SettleInfo record);

    public int delete(SettleInfo record);
}
