package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.PublishTaskAdapter;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.PublishTaskMapper;
import com.ifhz.core.po.PublishTask;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/14
 * Time: 15:44
 */
@Repository("publishTaskAdapter")
public class PublishTaskAdapterImpl implements PublishTaskAdapter {
    @Resource(name = "publishTaskMapper")
    private PublishTaskMapper publishTaskMapper;


    @Override
    public PublishTask getById(Long id) {
        return publishTaskMapper.getById(id);
    }

    @Override
    public List<PublishTask> queryByVo(Pagination page, PublishTask record) {
        List<PublishTask> result = publishTaskMapper.queryByVo(page, record);
        return result == null ? Lists.<PublishTask>newArrayList() : result;
    }

    @Override
    public int insert(PublishTask record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        return publishTaskMapper.insert(record);
    }

    @Override
    public int update(PublishTask record) {
        record.setUpdateTime(new Date());
        return publishTaskMapper.update(record);
    }

    @Override
    public int delete(PublishTask record) {
        record.setActive(JcywConstants.ACTIVE_D);
        record.setUpdateTime(new Date());
        return publishTaskMapper.update(record);
    }
}
