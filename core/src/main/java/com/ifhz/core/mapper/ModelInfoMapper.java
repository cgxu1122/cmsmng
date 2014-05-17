package com.ifhz.core.mapper;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ModelInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
public interface ModelInfoMapper {

    public ModelInfo getById(Long id);

    public List<ModelInfo> queryByVo(Pagination page, @Param(value = "record") ModelInfo record);

    public int insert(ModelInfo record);

    public int update(ModelInfo record);

    public int delete(ModelInfo record);
}
