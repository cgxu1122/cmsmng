package com.ifhz.core.mapper;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ApkInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 类描述
 * User: yangjian
 */
public interface ApkInfoMapper {

    public ApkInfo getById(Long apkId);

    public List<ApkInfo> queryByVo(Pagination page, @Param(value = "record") ApkInfo record);

    public int insert(ApkInfo record);

    public int update(ApkInfo record);

    public int delete(ApkInfo record);

    public List<ApkInfo> queryUpgradeList(@Param("startTime") Date date, @Param("endTime") Date endTime);

    public List<ApkInfo> queryListByApkIdList(@Param("pkgIdList") Set<Long> pkgIdList,
                                              @Param("startTime") Date startTime,
                                              @Param("endTime") Date endTime);
}
