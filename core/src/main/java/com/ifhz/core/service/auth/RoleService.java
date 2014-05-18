/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.service.auth;

import com.ifhz.core.po.Role;
import com.ifhz.core.vo.RoleVo;

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
    public List<RoleVo> queryRoleInfoByRoleName(String roleName);

//    /**
//     * 获取资源列表
//     *
//     * @author luyujian
//     * @return
//     */
//    public List<Map> getResourceList();
//

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
     * @author radishlee
     * @return
     */
//    public String findAllRoleResourceXmlString(long id);

    /**
     * 授权
     *
     * @author radishlee
     * @param string
     * @param resIdList
     * @return
     */
//    public void authorization(String string, List<String> resIdList);

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
    public void saveRoleFullPath(Role dbRole);

    /**
     * 根据名称查询 除了自己
     *
     * @author radishlee
     * @param roleName
     * @return
     */
//    public Role findByRoleNameBesideSelf(String roleName, long id);

//    /**
//     * @author radishlee
//     * @param role
//     */
//    public void update(Role role);
//
//    /**
//     * @author radishlee
//     * @param id
//     */
//    public void delete(long id);

    /**
     * @author radishlee
     * @param roleId
     */
//    public void deleteAllRefByRoleId(long roleId);

    /**
     * @author radishlee
     * @param roleId
     * @return
     */
//    public boolean check2Delete(long roleId);

    /**
     * 根据id查找父角色
     *
     * @param parentId
     * @return
     * @author wangshaofen
     */

    public Role findParentById(long parentId);

    /**
     * @author radish
     * @return
     */
//    public RoleVo getAdminRole();
}
