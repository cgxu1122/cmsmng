package com.ifhz.core.adapter;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ApkInfo;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User:yangjian
 */
public interface ApkInfoAdapter {

    public ApkInfo getById(Long id);

    public List<ApkInfo> queryByVo(Pagination page, ApkInfo record);

    public int insert(ApkInfo record);

    public int update(ApkInfo record);

    public int delete(ApkInfo record);

    public List<ApkInfo> queryAllList();

    public List<ApkInfo> queryUpgradeList(Date date);
}
