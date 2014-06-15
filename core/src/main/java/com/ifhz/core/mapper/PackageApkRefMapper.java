package com.ifhz.core.mapper;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PackageApkRef;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/24
 * Time: 17:37
 */
public interface PackageApkRefMapper {
    public PackageApkRef getById(Long id);

    public List<PackageApkRef> queryByVo(Pagination page, @Param(value = "record") PackageApkRef record);

    public int insert(PackageApkRef record);

    public int update(PackageApkRef record);

    public int delete(PackageApkRef record);

    public List<PackageApkRef> queryListByPackageId(@Param("packageId") Long packageId);

    public List<PackageApkRef> queryListByApkId(@Param("apkId") Long apkId);
}
