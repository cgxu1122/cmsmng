package com.ifhz.core.service.pkgmng;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PackageInfo;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 18:03
 */
public interface PackageInfoService {
    public PackageInfo getById(Long id);

    public List<PackageInfo> queryByVo(Pagination page, PackageInfo record);

    public int insert(PackageInfo record);

    public int update(PackageInfo record);

    public int delete(PackageInfo record);

    public List<PackageInfo> queryList(Long channelId, Date startTime, Date endTime);
}
