package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.PubChlModRefAdapter;
import com.ifhz.core.mapper.PubChlModRefMapper;
import com.ifhz.core.po.PubChlModRef;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 16:05
 */
@Repository("pubChlModRefAdapter")
public class PubChlModRefAdapterImpl implements PubChlModRefAdapter {

    @Resource(name = "pubChlModRefMapper")
    private PubChlModRefMapper pubChlModRefMapper;

    @Override
    public int insert(PubChlModRef record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        return pubChlModRefMapper.insert(record);
    }

    @Override
    public int deleteRepeatRef(PubChlModRef record) {
        record.setUpdateTime(new Date());
        return pubChlModRefMapper.deleteRepeatRef(record);
    }

    @Override
    public int deleteByPublishId(PubChlModRef record) {
        record.setUpdateTime(new Date());
        return pubChlModRefMapper.deleteByPublishId(record);
    }

    @Override
    public List<PubChlModRef> queryNormalPkgList(Long groupId, Long channelId, Date startTime, Date endTime) {
        List<PubChlModRef> result = pubChlModRefMapper.queryNormalPkgList(groupId, channelId, startTime, endTime);
        return result == null ? Lists.<PubChlModRef>newArrayList() : result;
    }

    @Override
    public List<PubChlModRef> queryCommonPkgList(Long groupId, Date startTime, Date endTime) {
        List<PubChlModRef> result = pubChlModRefMapper.queryCommonPkgList(groupId, startTime, endTime);
        return result == null ? Lists.<PubChlModRef>newArrayList() : result;
    }
}
