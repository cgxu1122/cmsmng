package com.ifhz.core.service.pkgmng;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PublishTask;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 18:05
 */
public interface PublishTaskService {
    public PublishTask getById(Long id);

    public List<PublishTask> queryByVo(Pagination page, PublishTask record);

    public int insert(PublishTask record);

    public int delete(PublishTask record);

}
