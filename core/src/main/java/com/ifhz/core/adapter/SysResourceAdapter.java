package com.ifhz.core.adapter;

import com.ifhz.core.po.auth.SysResource;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 16:40
 */
public interface SysResourceAdapter {

    public SysResource getById(Long resourceId);

    public List<SysResource> queryListByRoleId(Long roleId);

    public List<SysResource> queryAllList();
}
