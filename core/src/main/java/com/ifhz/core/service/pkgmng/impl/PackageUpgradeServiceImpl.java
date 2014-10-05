package com.ifhz.core.service.pkgmng.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ifhz.core.adapter.*;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
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
    @Log
    public List<PackageVo> queryNormalPkgList(long groupId, long channelId, boolean isInitPkg, Date startTime, Date endTime) {
        LOGGER.info("groupId={},channelId={},isInitPkg={},startTime={},endTime={}", groupId, channelId, isInitPkg, startTime, endTime);
        Map<Long, PackageVo> packageVoMap = Maps.newHashMap();
        String active = null;
        if (isInitPkg) {
            active = Active.Y.dbValue;
        }
        List<PubChlModRef> list = pubChlModRefAdapter.queryNormalPkgList(groupId, channelId, active, startTime, endTime);
        LOGGER.info("List<PubChlModRef> list={}", JSON.toJSONString(list));
        if (CollectionUtils.isNotEmpty(list)) {
            for (PubChlModRef pubChlModRef : list) {
                if (pubChlModRef != null) {
                    if (Active.N == Active.getByDbValue(pubChlModRef.getActive())) {
                        PackageVo packageVo = new PackageVo();
                        packageVo.setType(ApiEnums.UpdateType.Delete.VALUE);
                        packageVo.setPackageId(pubChlModRef.getPackageId());
                        packageVoMap.put(packageVo.getPackageId(), packageVo);

                        continue;
                    }
                    // 获取机型UA
                    ModelInfo modelInfo = modelInfoAdapter.getById(pubChlModRef.getModelId());
                    LOGGER.info("modelInfo={}", JSON.toJSONString(modelInfo));
                    if (modelInfo == null) continue;
                    //获取安装包信息
                    PackageVo packageVo = packageVoMap.get(pubChlModRef.getPackageId());
                    if (packageVo == null || packageVo.getType() == ApiEnums.UpdateType.Delete.VALUE) {
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
    @Log
    public PackageVo queryCommonPkgList(long groupId, long channelId, Date startTime, Date endTime) {
        PackageVo packageVo = null;
        List<PubChlModRef> list = pubChlModRefAdapter.queryCommonPkgList(groupId, channelId, startTime, endTime);
        if (CollectionUtils.isNotEmpty(list)) {
            for (PubChlModRef pubChlModRef : list) {
                if (Active.N == Active.getByDbValue(pubChlModRef.getActive())) {
                    continue;
                }
                //获取安装包信息
                PackageInfo packageInfo = packageInfoAdapter.getById(pubChlModRef.getPackageId());
                //获取安装包与apk映射关系
                List<PackageApkRef> packageApkRefList = packageApkRefAdapter.queryListByPackageId(packageInfo.getPackageId());
                if (packageInfo != null && CollectionUtils.isNotEmpty(packageApkRefList)) {
                    PackageVo vo = PoToVoHandler.translatePackageVo(packageInfo, packageApkRefList);
                    if (vo.getType() != ApiEnums.UpdateType.Delete.VALUE) {
                        packageVo = vo;
                        break;
                    }
                }
            }
        }

        return packageVo;
    }

    @Override
    @Log
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
    @Log
    public List<ApkVo> queryApkList(long groupId, long channelId, Date startTime, Date endTime) {
        Set<Long> paramList = Sets.newHashSet();
        List<ApkVo> result = Lists.newArrayList();
        Set<Long> set = Sets.newHashSet();

        List<Long> normalPkgIdList = pubChlModRefAdapter.queryPkgIdListForNormalPkg(groupId, channelId, startTime, endTime);
        List<Long> commonPkgIdList = pubChlModRefAdapter.queryPkgIdListForCommonPkg(groupId, channelId, startTime, endTime);
        if (CollectionUtils.isNotEmpty(normalPkgIdList)) {
            paramList.addAll(normalPkgIdList);
        }
        if (CollectionUtils.isNotEmpty(commonPkgIdList)) {
            paramList.addAll(commonPkgIdList);
        }


        if (CollectionUtils.isNotEmpty(paramList)) {
            List<ApkInfo> apkInfoList = apkInfoAdapter.queryListByApkIdList(paramList, startTime, endTime);
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

        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setPageSize(2);
        ApkInfo apkInfo = new ApkInfo();
        apkInfo.setType("3");
        List<ApkInfo> progressApkInfoList = apkInfoAdapter.queryByVo(pagination, apkInfo);
        if (CollectionUtils.isNotEmpty(progressApkInfoList)) {
            ApkVo vo = PoToVoHandler.translateApkVo(progressApkInfoList.get(0));
            if (vo != null) {
                result.add(vo);
            }
        }

        return result;
    }
}
