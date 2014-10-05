package com.ifhz.core.service.pkgmng.handle;

import com.google.common.collect.Lists;
import com.ifhz.core.constants.Active;
import com.ifhz.core.constants.ApiEnums;
import com.ifhz.core.constants.AutoRun;
import com.ifhz.core.constants.DesktopIcon;
import com.ifhz.core.po.ApkInfo;
import com.ifhz.core.po.PackageApkRef;
import com.ifhz.core.po.PackageInfo;
import com.ifhz.core.vo.ApkVo;
import com.ifhz.core.vo.PackageApkVo;
import com.ifhz.core.vo.PackageVo;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 18:18
 */
public final class PoToVoHandler {

    public static PackageVo translatePackageVo(PackageInfo info, List<PackageApkRef> refList) {
        PackageVo vo = null;
        if (info != null && CollectionUtils.isNotEmpty(refList)) {
            vo = new PackageVo();

            vo.setPackageId(info.getPackageId());
            if (Active.N == Active.getByDbValue(info.getActive())) {
                vo.setType(ApiEnums.UpdateType.Delete.VALUE);
                return vo;
            } else {
                vo.setType(ApiEnums.UpdateType.Add.VALUE);
            }
            vo.setBatchCode(info.getBatchCode());
            vo.setName(info.getPackageName());

            List<PackageApkVo> packageApkVos = Lists.newArrayList();
            for (PackageApkRef ref : refList) {
                PackageApkVo packageApkVo = translatePackageApkVo(ref);
                if (packageApkVo != null) {
                    packageApkVos.add(packageApkVo);
                }
            }

            vo.setApkList(packageApkVos);
        }

        return vo;
    }


    public static PackageApkVo translatePackageApkVo(PackageApkRef ref) {
        PackageApkVo vo = null;
        if (ref != null) {
            vo = new PackageApkVo();
            vo.setApkId(ref.getApkId());
            vo.setIcon(DesktopIcon.getByDbValue(ref.getDesktopIcon()).infValue);
            vo.setRun(AutoRun.getByDbValue(ref.getAutoRun()).infValue);
            vo.setSort(ref.getSort());
        }

        return vo;
    }


    public static ApkVo translateApkVo(ApkInfo info) {
        if (info == null) {
            return null;
        }
        ApkVo vo = new ApkVo();
        vo.setApkId(info.getApkId());
        vo.setMd5value(info.getMd5Value());
        vo.setPath(info.getDownloadUrl());
        vo.setType(ApiEnums.ApkVoType.getfromType(info.getType().trim()));
        vo.setPackagePath(info.getPackagePath());

        return vo;
    }

    private PoToVoHandler() {
    }
}
