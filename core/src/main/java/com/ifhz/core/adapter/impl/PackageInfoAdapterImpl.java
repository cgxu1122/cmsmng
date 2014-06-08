package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.PackageInfoAdapter;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.PackageInfoMapper;
import com.ifhz.core.po.PackageInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
@Repository("packageInfoAdapter")
public class PackageInfoAdapterImpl implements PackageInfoAdapter {

    @Resource(name = "packageInfoMapper")
    private PackageInfoMapper packageInfoMapper;


    @Override
    public PackageInfo getById(Long id) {
        return packageInfoMapper.getById(id);
    }

    @Override
    public List<PackageInfo> queryByVo(Pagination page, PackageInfo record) {
        List<PackageInfo> result = packageInfoMapper.queryByVo(page, record);
        return result == null ? Lists.<PackageInfo>newArrayList() : result;
    }

    @Override
    public int insert(PackageInfo record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        return packageInfoMapper.insert(record);
    }

    @Override
    public int update(PackageInfo record) {
        record.setUpdateTime(new Date());
        return packageInfoMapper.update(record);
    }

    @Override
    public int delete(PackageInfo record) {
        record.setActive(JcywConstants.ACTIVE_D);
        record.setUpdateTime(new Date());
        return packageInfoMapper.update(record);
    }
}
