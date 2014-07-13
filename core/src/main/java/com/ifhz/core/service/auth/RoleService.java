/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.service.auth;

import com.ifhz.core.po.Role;
import com.ifhz.core.vo.RoleVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 角色管理
 *
 * @author luyujian
 */
public interface RoleService {

    /**
     * 根据角色名称查询角色信息
     *
     * @param roleName
     * @return
     * @author luyujian
     */
    public List<Role> queryRoleInfoByRoleName(String roleName);

    /**
     * 获取资源列表
     *
     * @author luyujian
     * @return
     */
    //public List<Map> getResourceList();


    /**
     * 获取所有角色
     *
     * @return
     * @author radishlee
     */
    public List<Role> findAllRole();

    /**
     * 获取角色树
     *
     * @return
     * @author radishlee
     */
    public String findAllRoleTreeXmlString();

    /**
     * 获取资源树
     *
     * @return
     * @author radishlee
     */
    public String findAllRoleResourceXmlString(long id);

    /**
     * 授权
     *
     * @param string
     * @param resIdList
     * @return
     * @author radishlee
     */
    public void authorization(String string, List<String> resIdList);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     * @author radishlee
     */
    public RoleVo findById(long id);

    /**
     * 新增角色
     *
     * @param role
     * @author radishlee
     */
    public void insert(Role role);

    /**
     * 根据名称查询
     *
     * @param name
     * @return
     * @author radishlee
     */
    public Role findByRoleName(String name);

    /**
     * 保持全路径值
     *
     * @param dbRole
     * @author radishlee
     */
    public void saveFullPathAndType(Role dbRole);

    /**
     * 根据名称查询 除了自己
     *
     * @param roleName
     * @return
     * @author radishlee
     */
    public Role findByRoleNameBesideSelf(String roleName, long id);

    /**
     * @param role
     * @author radishlee
     */
    public void update(Role role);

    /**
     * @param id
     * @author radishlee
     */
    public void delete(long id);

    /**
     * @param roleId
     * @author radishlee
     */
    public void deleteAllRefByRoleId(long roleId);

    /**
     * @param roleId
     * @return
     * @author radishlee
     */
    public boolean check2Delete(long roleId);

    /**
     * 根据id查找父角色
     *
     * @param parentId
     * @return
     * @author wangshaofen
     */

    public Role findParentById(long parentId);

    /**
     * @return
     * @author radish
     */
    public RoleVo getAdminRole();

    String findRoleTreeXmlStringByRoleId(long roleId);

    List<Role> findAllRoleSon(long roleId);

    RoleVo findRootRole() throws InvocationTargetException, IllegalAccessException;
}
