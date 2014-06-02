package com.ifhz.core.mapper;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ApkInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
public interface ApkInfoMapper {

    public ApkInfo getById(Long id);

    public List<ApkInfo> queryByVo(@Param("page") Pagination page, @Param(value = "record") ApkInfo record);

    public int insert(ApkInfo record);

    public int update(ApkInfo record);

    public int delete(ApkInfo record);

    public List<ApkInfo> queryAllList();

    public List<ApkInfo> queryUpgradeList(@Param("date") Date date);

}
