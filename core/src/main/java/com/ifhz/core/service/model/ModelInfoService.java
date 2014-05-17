package com.ifhz.core.service.model;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ModelInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangjian
 */
public interface ModelInfoService {
    public ModelInfo getById(Long id);

    public List<ModelInfo> queryByVo(Pagination page, ModelInfo record);

    public int insert(ModelInfo record);

    public int update(ModelInfo record);

    public int delete(ModelInfo record);
}
