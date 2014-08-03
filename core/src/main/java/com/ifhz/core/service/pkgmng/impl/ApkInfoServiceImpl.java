package com.ifhz.core.service.pkgmng.impl;

import com.ifhz.core.adapter.ApkInfoAdapter;
import com.ifhz.core.adapter.PackageApkRefAdapter;
import com.ifhz.core.adapter.PubChlModRefAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ApkInfo;
import com.ifhz.core.po.PackageApkRef;
import com.ifhz.core.po.PubChlModRef;
import com.ifhz.core.service.pkgmng.ApkInfoService;
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
@Service("apkInfoService")
public class ApkInfoServiceImpl implements ApkInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApkInfoServiceImpl.class);

    @Resource(name = "apkInfoAdapter")
    private ApkInfoAdapter apkInfoAdapter;
    @Resource(name = "pubChlModRefAdapter")
    private PubChlModRefAdapter pubChlModRefAdapter;
    @Resource(name = "packageApkRefAdapter")
    private PackageApkRefAdapter packageApkRefAdapter;


    @Override
    @Log
    public ApkInfo getById(Long id) {
        return apkInfoAdapter.getById(id);
    }

    @Override
    @Log
    public List<ApkInfo> queryByVo(Pagination page, ApkInfo record) {
        return apkInfoAdapter.queryByVo(page, record);
    }

    @Override
    @Log
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(ApkInfo record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        return apkInfoAdapter.insert(record);
    }

    @Override
    @Log
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(ApkInfo record) {
        record.setUpdateTime(new Date());
        return apkInfoAdapter.update(record);
    }

    @Override
    @Log
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(ApkInfo record) {
        int ret = apkInfoAdapter.delete(record);
        if (ret != 0) {
            List<PackageApkRef> packageApkRefList = packageApkRefAdapter.queryListByApkId(record.getApkId());
            if (CollectionUtils.isNotEmpty(packageApkRefList)) {
                for (PackageApkRef packageApkRef : packageApkRefList) {
                    int result = packageApkRefAdapter.delete(packageApkRef);
                    if (result != 0) {
                        PubChlModRef ref = new PubChlModRef();
                        ref.setGroupId(packageApkRef.getPackageId());
                        ref.setUpdateTime(new Date());
                        pubChlModRefAdapter.updateByPackageId(ref);
                    }
                }
            }
        }

        return ret;
    }
}
