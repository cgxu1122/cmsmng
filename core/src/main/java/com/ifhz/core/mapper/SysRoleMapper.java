package com.ifhz.core.mapper;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.auth.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 16:39
 */
public interface SysRoleMapper {

    public int insert(SysRole po);

    public int update(SysRole po);

    public int delete(Long roleId);

    public SysRole getById(Long roleId);

    public List<SysRole> queryByVo(Pagination pagination, @Param(value = "record") SysRole record);

    public List<SysRole> queryChildListByRoleId(@Param(value = "roleId") Long roleId);

    public List<SysRole> queryParentListByRoleId(@Param(value = "roleId") Long roleId);

    public List<SysRole> queryListByRootId(@Param(value = "rootId") Long rootId);

    public List<SysRole> queryAllRoleList();
}
