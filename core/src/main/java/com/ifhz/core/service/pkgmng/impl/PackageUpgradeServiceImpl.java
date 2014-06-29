package com.ifhz.core.service.pkgmng.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ifhz.core.adapter.*;
import com.ifhz.core.constants.Active;
import com.ifhz.core.constants.ApiEnums;
import com.ifhz.core.po.*;
import com.ifhz.core.service.pkgmng.PackageUpgradeService;
import com.ifhz.core.service.pkgmng.handle.PoToVoHandler;
import com.ifhz.core.vo.ApkVo;
import com.ifhz.core.vo.PackageVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 21:28
 */
@Service("packageUpgradeService")
public class PackageUpgradeServiceImpl implements PackageUpgradeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PackageUpgradeServiceImpl.class);

    @Resource(name = "pubChlModRefAdapter")
    private PubChlModRefAdapter pubChlModRefAdapter;
    @Resource(name = "packageInfoAdapter")
    private PackageInfoAdapter packageInfoAdapter;
    @Resource(name = "modelInfoAdapter")
    private ModelInfoAdapter modelInfoAdapter;
    @Resource(name = "packageApkRefAdapter")
    private PackageApkRefAdapter packageApkRefAdapter;
    @Resource(name = "apkInfoAdapter")
    private ApkInfoAdapter apkInfoAdapter;


    @Override
    public List<PackageVo> queryNormalPkgList(long groupId, long channelId, boolean isInitPkg, Date startTime, Date endTime) {
        Map<Long, PackageVo> packageVoMap = Maps.newHashMap();
        String active = null;
        if (isInitPkg) {
            active = Active.Y.dbValue;
        }
        List<PubChlModRef> list = pubChlModRefAdapter.queryNormalPkgList(groupId, channelId, active, startTime, endTime);
        if (CollectionUtils.isNotEmpty(list)) {
            for (PubChlModRef pubChlModRef : list) {
                if (pubChlModRef != null) {
                    // 获取机型UA
                    ModelInfo modelInfo = modelInfoAdapter.getById(pubChlModRef.getModelId());
                    if (modelInfo == null) continue;
                    //获取安装包信息
                    PackageVo packageVo = packageVoMap.get(pubChlModRef.getPackageId());
                    if (packageVo == null) {
                        PackageInfo packageInfo = packageInfoAdapter.getById(pubChlModRef.getPackageId());
                        //获取安装包与apk映射关系
                        List<PackageApkRef> packageApkRefList = packageApkRefAdapter.queryListByPackageId(packageInfo.getPackageId());
                        if (packageInfo == null || CollectionUtils.isEmpty(packageApkRefList)) {
                            continue;
                        }
                        packageVo = PoToVoHandler.translatePackageVo(packageInfo, packageApkRefList);
                        if (packageVo == null) {
                            continue;
                        }

                        packageVoMap.put(packageVo.getPackageId(), packageVo);
                    }
                    if (ApiEnums.UpdateType.Delete != ApiEnums.UpdateType.getfromValue(packageVo.getType())) {
                        //设置UA给packageVO
                        List<String> modelList = packageVo.getModelList();
                        if (CollectionUtils.isNotEmpty(modelList)) {
                            modelList.add(modelInfo.getUa());
                        } else {
                            modelList = Lists.newArrayList();
                            modelList.add(modelInfo.getUa());
                        }
                        packageVo.setModelList(modelList);
                    }
                }
            }
        }

        return Lists.newArrayList(packageVoMap.values());
    }

    @Override
    public PackageVo queryCommonPkgList(long groupId, Date startTime, Date endTime) {
        PackageVo packageVo = null;
        List<PubChlModRef> list = pubChlModRefAdapter.queryCommonPkgList(groupId, startTime, endTime);
        if (CollectionUtils.isNotEmpty(list)) {
            PubChlModRef pubChlModRef = list.get(0);
            //获取安装包信息
            PackageInfo packageInfo = packageInfoAdapter.getById(pubChlModRef.getPackageId());
            //获取安装包与apk映射关系
            List<PackageApkRef> packageApkRefList = packageApkRefAdapter.queryListByPackageId(packageInfo.getPackageId());
            if (packageInfo != null && CollectionUtils.isNotEmpty(packageApkRefList)) {
                packageVo = PoToVoHandler.translatePackageVo(packageInfo, packageApkRefList);
            }
        }

        return packageVo;
    }

    @Override
    public List<ApkVo> queryApkList(Date startTime, Date endTime) {
        List<ApkVo> result = Lists.newArrayList();
        List<ApkInfo> apkInfoList = apkInfoAdapter.queryUpgradeList(startTime, endTime);
        if (CollectionUtils.isNotEmpty(apkInfoList)) {
            for (ApkInfo apkInfo : apkInfoList) {
                ApkVo vo = PoToVoHandler.translateApkVo(apkInfo);
                if (vo != null) {
                    result.add(vo);
                }
            }
        }
        return result;
    }

    @Override
    public List<ApkVo> queryApkList(long groupId, long channelId, Date startTime, Date endTime) {
        List<Long> paramList = Lists.newArrayList();
        List<ApkVo> result = Lists.newArrayList();
        Set<Long> set = Sets.newHashSet();

        List<Long> normalPkgIdList = pubChlModRefAdapter.queryPkgIdListForNormalPkg(groupId, channelId, startTime, endTime);
        List<Long> commonPkgIdList = pubChlModRefAdapter.queryPkgIdListForCommonPkg(groupId, startTime, endTime);
        if (CollectionUtils.isNotEmpty(normalPkgIdList)) {
            paramList.addAll(normalPkgIdList);
        }
        if (CollectionUtils.isNotEmpty(commonPkgIdList)) {
            paramList.addAll(commonPkgIdList);
        }

        if (CollectionUtils.isNotEmpty(paramList)) {
            List<ApkInfo> apkInfoList = apkInfoAdapter.queryListByApkIdList(paramList);
            if (CollectionUtils.isNotEmpty(apkInfoList)) {
                for (ApkInfo apkInfo : apkInfoList) {
                    if (!set.contains(apkInfo.getApkId())) {
                        ApkVo vo = PoToVoHandler.translateApkVo(apkInfo);
                        if (vo != null) {
                            result.add(vo);
                        }
                    }
                }
            }
        }

        return result;
    }
}
