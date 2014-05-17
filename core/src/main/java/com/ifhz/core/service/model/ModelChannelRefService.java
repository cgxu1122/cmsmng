package com.ifhz.core.service.model;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ModelChannelRef;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangjian
 */
public interface ModelChannelRefService {

    public List<ModelChannelRef> queryByVo(Pagination page, ModelChannelRef record);

    public int insert(ModelChannelRef record);

    public int delete(ModelChannelRef record);
}
