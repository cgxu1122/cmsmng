package com.ifhz.core.adapter;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.BatchProductRef;

import java.util.List;

/**
 * 类描述
 * User:yangjian
 */
public interface BatchProductRefAdapter {

    public BatchProductRef getById(Long id);

    public List<BatchProductRef> queryByVo(Pagination page, BatchProductRef record);

    public int insert(BatchProductRef record);

    public int update(BatchProductRef record);

    public int delete(BatchProductRef record);
}
