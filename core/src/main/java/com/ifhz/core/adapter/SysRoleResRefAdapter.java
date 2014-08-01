package com.ifhz.core.adapter;

import com.ifhz.core.po.auth.SysRoleResRef;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 16:40
 */
public interface SysRoleResRefAdapter {

    public int insert(SysRoleResRef record);

    public int delete(Long roleId);

    public List<SysRoleResRef> queryListByRoleId(Long roleId);
}
