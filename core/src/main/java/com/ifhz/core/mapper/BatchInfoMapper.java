package com.ifhz.core.mapper;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.BatchInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
public interface BatchInfoMapper {

    public BatchInfo getById(Long id);

    public List<BatchInfo> queryByVo(Pagination page, @Param(value = "record") BatchInfo record);

    public int insert(BatchInfo record);

    public int update(BatchInfo record);

    public int delete(BatchInfo record);
}
