package com.ifhz.core.mapper;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ModelChannelRef;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
public interface ModelChannelRefMapper {

    public ModelChannelRef getById(Long id);

    public List<ModelChannelRef> queryByVo(Pagination page, @Param(value = "record") ModelChannelRef record);

    public int insert(ModelChannelRef record);

    public int update(ModelChannelRef record);

    public int delete(ModelChannelRef record);
}
