package com.ifhz.core.adapter.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ifhz.core.adapter.PubChlModRefAdapter;
import com.ifhz.core.mapper.PubChlModRefMapper;
import com.ifhz.core.po.PubChlModRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(PubChlModRefAdapterImpl.class);

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
        LOGGER.info("deleteRepeatRef record={}", JSON.toJSONString(record));
        return pubChlModRefMapper.deleteRepeatRef(record);
    }

    @Override
    public int deleteByPublishId(PubChlModRef record) {
        record.setUpdateTime(new Date());
        LOGGER.info("deleteByPublishId record={}", JSON.toJSONString(record));
        return pubChlModRefMapper.deleteByPublishId(record);
    }

    @Override
    public int deleteByPackageId(PubChlModRef record) {
        record.setUpdateTime(new Date());
        LOGGER.info("deleteByPackageId record={}", JSON.toJSONString(record));
        return pubChlModRefMapper.deleteByPackageId(record);
    }

    @Override
    public int updateByPackageId(PubChlModRef record) {
        record.setUpdateTime(new Date());
        LOGGER.info("updateByPackageId record={}", JSON.toJSONString(record));
        return pubChlModRefMapper.deleteByPackageId(record);
    }

    @Override
    public int updateByGroupIdAndGroupId(PubChlModRef record) {
        record.setUpdateTime(new Date());
        LOGGER.info("updateByGroupIdAndGroupId record={}", JSON.toJSONString(record));
        return pubChlModRefMapper.updateByGroupIdAndGroupId(record);
    }

    @Override
    public List<PubChlModRef> queryNormalPkgList(Long groupId, Long channelId, String active, Date startTime, Date endTime) {
        List<PubChlModRef> result = pubChlModRefMapper.queryNormalPkgList(groupId, channelId, active, startTime, endTime);
        return result == null ? Lists.<PubChlModRef>newArrayList() : result;
    }

    @Override
    public List<PubChlModRef> queryCommonPkgList(Long groupId, Date startTime, Date endTime) {
        List<PubChlModRef> result = pubChlModRefMapper.queryCommonPkgList(groupId, startTime, endTime);
        return result == null ? Lists.<PubChlModRef>newArrayList() : result;
    }

    @Override
    public List<Long> queryPkgIdListForNormalPkg(Long groupId, Long channelId, Date startTime, Date endTime) {
        List<Long> result = pubChlModRefMapper.queryPkgIdListForNormalPkg(groupId, channelId, startTime, endTime);
        return result == null ? Lists.<Long>newArrayList() : result;
    }

    @Override
    public List<Long> queryPkgIdListForCommonPkg(Long groupId, Date startTime, Date endTime) {
        List<Long> result = pubChlModRefMapper.queryPkgIdListForCommonPkg(groupId, startTime, endTime);
        return result == null ? Lists.<Long>newArrayList() : result;
    }
}
