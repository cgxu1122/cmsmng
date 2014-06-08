package com.ifhz.core.mapper;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PackageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/24
 * Time: 17:37
 */
public interface PackageInfoMapper {
    public PackageInfo getById(Long id);

    public List<PackageInfo> queryByVo(Pagination page, @Param(value = "record") PackageInfo record);

    public int insert(PackageInfo record);

    public int update(PackageInfo record);

    public int delete(PackageInfo record);
}
