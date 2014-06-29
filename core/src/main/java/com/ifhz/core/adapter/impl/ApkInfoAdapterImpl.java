package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.ApkInfoAdapter;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.ApkInfoMapper;
import com.ifhz.core.po.ApkInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/24
 * Time: 15:50
 */
@Repository("apkInfoAdapter")
public class ApkInfoAdapterImpl implements ApkInfoAdapter {

    @Resource(name = "apkInfoMapper")
    private ApkInfoMapper apkInfoMapper;

    @Override
    public ApkInfo getById(Long id) {
        return apkInfoMapper.getById(id);
    }

    @Override
    public List<ApkInfo> queryByVo(Pagination page, ApkInfo record) {
        List<ApkInfo> result = apkInfoMapper.queryByVo(page, record);
        return result == null ? Lists.<ApkInfo>newArrayList() : result;
    }

    @Override
    public int insert(ApkInfo record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        return apkInfoMapper.insert(record);
    }

    @Override
    public int update(ApkInfo record) {
        return apkInfoMapper.update(record);
    }

    @Override
    public int delete(ApkInfo record) {
        record.setActive(JcywConstants.ACTIVE_D);
        record.setUpdateTime(new Date());
        return apkInfoMapper.update(record);
    }

    @Override
    public List<ApkInfo> queryUpgradeList(Date startTime, Date endTime) {
        List<ApkInfo> result = apkInfoMapper.queryUpgradeList(startTime, endTime);
        return result == null ? Lists.<ApkInfo>newArrayList() : result;
    }

    @Override
    public List<ApkInfo> queryListByPackageId(Long packageId, String active) {
        return null;
    }

    @Override
    public List<ApkInfo> queryListByApkIdList(Set<Long> apkIdList) {
        List<ApkInfo> result = apkInfoMapper.queryListByApkIdList(apkIdList);
        return result == null ? Lists.<ApkInfo>newArrayList() : result;
    }
}
