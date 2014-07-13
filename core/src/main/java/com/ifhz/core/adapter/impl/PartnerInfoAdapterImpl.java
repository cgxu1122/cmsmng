package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.PartnerInfoAdapter;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.PartnerInfoMapper;
import com.ifhz.core.po.PartnerInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
@Repository("partnerInfoAdapter")
public class PartnerInfoAdapterImpl implements PartnerInfoAdapter {

    @Resource(name = "partnerInfoMapper")
    private PartnerInfoMapper partnerInfoMapper;


    @Override
    public PartnerInfo getById(Long id) {
        return partnerInfoMapper.getById(id);
    }

    @Override
    public List<PartnerInfo> queryByVo(Pagination page, PartnerInfo record) {
        List<PartnerInfo> result = partnerInfoMapper.queryByVo(page, record);
        return result == null ? Lists.<PartnerInfo>newArrayList() : result;
    }

    @Override
    public int insert(PartnerInfo record) {
        record.setCreateTime(new Date());
        return partnerInfoMapper.insert(record);
    }

    @Override
    public int update(PartnerInfo record) {
        record.setUpdateTime(new Date());
        return partnerInfoMapper.update(record);
    }

    @Override
    public int delete(PartnerInfo record) {
        record.setActive(JcywConstants.ACTIVE_D);
        record.setUpdateTime(new Date());
        return partnerInfoMapper.update(record);
    }

    @Override
    public PartnerInfo getPartnerInfoByUserId(Long userId) {
        return partnerInfoMapper.getPartnerInfoByUserId(userId);
    }
}
