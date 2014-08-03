package com.ifhz.core.service.pkgmng.impl;

import com.ifhz.core.adapter.PackageApkRefAdapter;
import com.ifhz.core.adapter.PackageInfoAdapter;
import com.ifhz.core.adapter.PubChlModRefAdapter;
import com.ifhz.core.adapter.PublishTaskAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PackageApkRef;
import com.ifhz.core.po.PackageInfo;
import com.ifhz.core.po.PubChlModRef;
import com.ifhz.core.service.pkgmng.PackageInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Resource(name = "pubChlModRefAdapter")
    private PubChlModRefAdapter pubChlModRefAdapter;
    @Resource(name = "publishTaskAdapter")
    private PublishTaskAdapter publishTaskAdapter;

    @Override
    @Log
    public PackageInfo getById(Long id) {
        return packageInfoAdapter.getById(id);
    }

    @Override
    @Log
    public List<PackageInfo> queryByVo(Pagination page, PackageInfo record) {
        return packageInfoAdapter.queryByVo(page, record);
    }

    @Override
    @Log
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
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

    @Log
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
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
        int ret = packageInfoAdapter.update(record);
        if (ret != 0) {
            //更新发布任务与渠道、机型关联表对应数据
            PubChlModRef pubChlModRef = new PubChlModRef();
            pubChlModRef.setPackageId(record.getPackageId());
            pubChlModRef.setUpdateTime(new Date());
            pubChlModRefAdapter.updateByPackageId(pubChlModRef);
        }

        return ret;
    }

    @Override
    @Log
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(PackageInfo record) {
        //先删除关联表信息
        PackageApkRef paRef = new PackageApkRef();
        paRef.setPackageId(record.getPackageId());
        packageApkRefAdapter.delete(paRef);

        int ret = packageInfoAdapter.delete(record);
        if (ret != 0) {
            //删除发布任务与渠道、机型关联表对应数据
            PubChlModRef pubChlModRef = new PubChlModRef();
            pubChlModRef.setPackageId(record.getPackageId());
            pubChlModRef.setUpdateTime(new Date());
            pubChlModRefAdapter.deleteByPackageId(pubChlModRef);
        }
        return ret;
    }
}
