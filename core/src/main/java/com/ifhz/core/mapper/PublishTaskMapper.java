package com.ifhz.core.mapper;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PublishTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/24
 * Time: 17:38
 */
public interface PublishTaskMapper {
    public PublishTask getById(Long id);

    public List<PublishTask> queryByVo(Pagination page, @Param(value = "record") PublishTask record);

    public int insert(PublishTask record);

    public int update(PublishTask record);

    public int delete(PublishTask record);
}
