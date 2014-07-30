package com.ifhz.core.mapper;

import com.ifhz.core.po.auth.SysResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 16:40
 */
public interface SysResourceMapper {

    public SysResource getById(@Param("resourceId") Long resourceId);

    public List<SysResource> queryListByRoleId(@Param("roleId") Long roleId);

    public List<SysResource> queryAllList();
}
