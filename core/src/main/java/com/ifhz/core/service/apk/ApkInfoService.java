package com.ifhz.core.service.apk;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ApkInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangjian
 */
public interface ApkInfoService {
    public ApkInfo getById(Long id);

    public List<ApkInfo> queryByVo(Pagination page, ApkInfo record);

    public int insert(ApkInfo record);

    public int update(ApkInfo record);

    public int delete(ApkInfo record);
}
