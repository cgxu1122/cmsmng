package com.ifhz.core.adapter;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ModelInfo;

import java.util.List;

/**
 * 类描述
 * User:yangjian
 */
public interface ModelInfoAdapter {

    public ModelInfo getById(Long id);

    public List<ModelInfo> queryByVo(Pagination page, ModelInfo record);

    public int insert(ModelInfo record);

    public int update(ModelInfo record);

    public int delete(ModelInfo record);

    public ModelInfo getByGroupIdAndUa(Long groupId, String ua);
}
