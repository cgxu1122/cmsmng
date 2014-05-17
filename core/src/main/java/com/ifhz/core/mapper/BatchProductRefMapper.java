package com.ifhz.core.mapper;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.BatchProductRef;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
public interface BatchProductRefMapper {

    public BatchProductRef getById(Long id);

    public List<BatchProductRef> queryByVo(Pagination page, @Param(value = "record") BatchProductRef record);

    public int insert(BatchProductRef record);

    public int update(BatchProductRef record);

    public int delete(BatchProductRef record);
}
