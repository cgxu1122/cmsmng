/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.service.auth;

import com.ifhz.core.po.RoleResourceRef;

import java.util.List;


/**
 * 角色资源管理
 * 
 * @author radish
 */
public interface RoleResourceRefService {

    /**
     * 资源角色管理
     *
     * @author radish
     * @param roleId
     * @param resourceId
     * @return
     */
    public List<RoleResourceRef> roleIdAndResourceId(long roleId, long resourceId);

    /**
     * 授权
     * 
     * @author radish
     * @param roleId
     * @param resIdList
     * @return
     */
    public void authorization(String roleId, List<String> resIdList);

    /**
     * 获取角色树
     * 
     * @author radish
     * @return
     */
    public String findAllRoleTreeXmlString();

    /**
     * 找到角色资源关系
     * 
     * @author radish
     * @param id
     * @return
     */
    public String findAllRoleResourceXmlString(long id);

    /**
     * 根据roleId删除资源
     * 
     * @author radish
     * @param roleid
     * @return
     */
    public Boolean deleteAllRefByRoleId(long roleid);

    /**
     * 根据resId删除资源
     * 
     * @author radishlee
     * @param resId
     */
    public void deleteAllRefByResId(long resId);

}
