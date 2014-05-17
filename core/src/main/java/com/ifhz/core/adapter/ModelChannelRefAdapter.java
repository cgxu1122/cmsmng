package com.ifhz.core.adapter;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ModelChannelRef;

import java.util.List;

/**
 * 类描述
 * User:yangjian
 */
public interface ModelChannelRefAdapter {

    public ModelChannelRef getById(Long id);

    public List<ModelChannelRef> queryByVo(Pagination page, ModelChannelRef record);

    public int insert(ModelChannelRef record);

    public int update(ModelChannelRef record);

    public int delete(ModelChannelRef record);
}
