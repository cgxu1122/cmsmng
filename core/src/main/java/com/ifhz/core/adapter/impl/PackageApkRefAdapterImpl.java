package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.PackageApkRefAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.PackageApkRefMapper;
import com.ifhz.core.po.PackageApkRef;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
@Repository("packageApkRefAdapter")
public class PackageApkRefAdapterImpl implements PackageApkRefAdapter {

    @Resource(name = "packageApkRefMapper")
    private PackageApkRefMapper packageApkRefMapper;


    @Override
    public PackageApkRef getById(Long id) {
        return packageApkRefMapper.getById(id);
    }

    @Override
    public List<PackageApkRef> queryByVo(Pagination page, PackageApkRef record) {
        List<PackageApkRef> result = packageApkRefMapper.queryByVo(page, record);
        return result == null ? Lists.<PackageApkRef>newArrayList() : result;
    }

    @Override
    public int insert(PackageApkRef record) {
        record.setCreateTime(new Date());
        return packageApkRefMapper.insert(record);
    }

    @Override
    public int update(PackageApkRef record) {
        return packageApkRefMapper.update(record);
    }

    @Override
    public int delete(PackageApkRef record) {
        return packageApkRefMapper.delete(record);
    }

    @Override
    public List<PackageApkRef> queryListByPackageId(Long packageId) {
        List<PackageApkRef> result = packageApkRefMapper.queryListByPackageId(packageId);
        return result == null ? Lists.<PackageApkRef>newArrayList() : result;
    }

    @Override
    public List<PackageApkRef> queryListByApkId(Long apkId) {
        List<PackageApkRef> list = packageApkRefMapper.queryListByApkId(apkId);
        return list == null ? Lists.<PackageApkRef>newArrayList() : list;
    }
}
