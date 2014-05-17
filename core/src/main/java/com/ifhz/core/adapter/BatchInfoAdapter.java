package com.ifhz.core.adapter;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.BatchInfo;

import java.util.List;

/**
 * 类描述
 * User:yangjian
 */
public interface BatchInfoAdapter {

    public BatchInfo getById(Long id);

    public List<BatchInfo> queryByVo(Pagination page, BatchInfo record);

    public int insert(BatchInfo record);

    public int update(BatchInfo record);

    public int delete(BatchInfo record);
}
