package com.ifhz.core.service.batch;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.BatchProductRef;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangjian
 */
public interface BatchProductRefService {

    public List<BatchProductRef> queryByVo(Pagination page, BatchProductRef record);

    public int insert(BatchProductRef record);

    public int delete(BatchProductRef record);
}
