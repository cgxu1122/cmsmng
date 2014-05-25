package com.ifhz.core.service.batch;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.BatchInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangjian
 */
public interface BatchInfoService {
    public BatchInfo getById(Long id);

    public List<BatchInfo> queryByVo(Pagination page, BatchInfo record);

    public int insert(BatchInfo record);

    public int update(BatchInfo record);

    public int delete(BatchInfo record);

    public Long getSeqByGroupId(Long groupId);
}
