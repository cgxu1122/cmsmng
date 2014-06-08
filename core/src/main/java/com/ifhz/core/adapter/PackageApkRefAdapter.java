package com.ifhz.core.adapter;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PackageApkRef;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/24
 * Time: 17:37
 */
public interface PackageApkRefAdapter {
    public PackageApkRef getById(Long id);

    public List<PackageApkRef> queryByVo(Pagination page, PackageApkRef record);

    public int insert(PackageApkRef record);

    public int update(PackageApkRef record);

    public int delete(PackageApkRef record);
}
