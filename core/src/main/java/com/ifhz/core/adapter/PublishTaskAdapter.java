package com.ifhz.core.adapter;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PublishTask;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/24
 * Time: 17:38
 */
public interface PublishTaskAdapter {
    public PublishTask getById(Long id);

    public List<PublishTask> queryByVo(Pagination page, PublishTask record);

    public int insert(PublishTask record);

    public int update(PublishTask record);

    public int delete(PublishTask record);
}
