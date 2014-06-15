package com.ifhz.core.service.pkgmng;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ApkInfo;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 18:02
 */
public interface ApkInfoService {

    public ApkInfo getById(Long id);

    public List<ApkInfo> queryByVo(Pagination page, ApkInfo record);

    public int insert(ApkInfo record);

    public int update(ApkInfo record);

    public int delete(ApkInfo record);
}
