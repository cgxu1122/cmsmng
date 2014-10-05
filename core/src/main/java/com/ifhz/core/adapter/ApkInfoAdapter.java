package com.ifhz.core.adapter;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ApkInfo;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 类描述
 * User:yangjian
 */
public interface ApkInfoAdapter {

    public ApkInfo getById(Long id);

    public List<ApkInfo> queryByVo(Pagination page, ApkInfo record);

    public List<ApkInfo> queryChooseListByVo(Pagination page, ApkInfo record);

    public int insert(ApkInfo record);

    public int update(ApkInfo record);

    public int delete(ApkInfo record);

    public List<ApkInfo> queryUpgradeList(Date startTime, Date endTime);

    public List<ApkInfo> queryListByApkIdList(Set<Long> apkIdList, Date startTime, Date endTime);
}
