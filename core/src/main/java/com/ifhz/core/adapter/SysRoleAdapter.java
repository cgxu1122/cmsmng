package com.ifhz.core.adapter;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.auth.SysRole;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 16:39
 */
public interface SysRoleAdapter {

    public int insert(SysRole po);

    public int update(SysRole po);

    public int delete(Long roleId);

    public SysRole getById(Long roleId);

    public List<SysRole> queryByVo(Pagination pagination, SysRole record);

    public List<SysRole> queryChildListByRoleId(Long roleId);

    public List<SysRole> queryParentListByRoleId(Long roleId);

    public List<SysRole> queryAllRoleList();
}
