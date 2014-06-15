package com.ifhz.core.service.pkgmng.impl;

import com.ifhz.core.adapter.PackageApkRefAdapter;
import com.ifhz.core.adapter.PackageInfoAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PackageApkRef;
import com.ifhz.core.po.PackageInfo;
import com.ifhz.core.service.pkgmng.PackageInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 18:06
 */
@Service("packageInfoService")
public class PackageInfoServiceImpl implements PackageInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PackageInfoServiceImpl.class);

    @Resource(name = "packageInfoAdapter")
    private PackageInfoAdapter packageInfoAdapter;
    @Resource(name = "packageApkRefAdapter")
    private PackageApkRefAdapter packageApkRefAdapter;

    @Override
    public PackageInfo getById(Long id) {
        return packageInfoAdapter.getById(id);
    }

    @Override
    public List<PackageInfo> queryByVo(Pagination page, PackageInfo record) {
        return packageInfoAdapter.queryByVo(page, record);
    }

    @Override
    public int insert(PackageInfo record) {
        int flag = packageInfoAdapter.insert(record);
        List<PackageApkRef> packageApkRefList = record.getPackageApkRefList();
        if (CollectionUtils.isNotEmpty(packageApkRefList)) {
            for (PackageApkRef packageApkRef : packageApkRefList) {
                packageApkRef.setPackageId(record.getPackageId());
                packageApkRefAdapter.insert(packageApkRef);
            }
        }
        return flag;
    }

    @Override
    public int update(PackageInfo record) {
        //先删除
        PackageApkRef paRef = new PackageApkRef();
        paRef.setPackageId(record.getPackageId());
        packageApkRefAdapter.delete(paRef);
        //后添加
        List<PackageApkRef> packageApkRefList = record.getPackageApkRefList();
        if (CollectionUtils.isNotEmpty(packageApkRefList)) {
            for (PackageApkRef packageApkRef : packageApkRefList) {
                packageApkRef.setPackageId(record.getPackageId());
                packageApkRefAdapter.insert(packageApkRef);
            }
        }
        return packageInfoAdapter.update(record);
    }

    @Override
    public int delete(PackageInfo record) {
        PackageApkRef paRef = new PackageApkRef();
        paRef.setPackageId(record.getPackageId());
        packageApkRefAdapter.delete(paRef);
        //先删除关联表信息
        return packageInfoAdapter.delete(record);
    }

    @Override
    public List<PackageInfo> queryList(Long channelId, Date startTime, Date endTime) {
        return null;
    }
}
