/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.mapper;

import com.ifhz.core.po.Role;
import com.ifhz.core.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 角色管理
 *
 * @author luyujian
 */
public interface RoleMapper {

    /**
     * 插入角色
     *
     * @return
     * @author luyujian
     */
    public int insertRole(Role role);

//    /**
//     * 添加角色与资源关联关系
//     * 
//     * @author luyujian
//     * @param resourceRef
//     * @return
//     */
//    public int insertRoleResRef(RoleResourceRef resourceRef);
//
//    /**
//     * 批量添加角色与资源关联关系
//     * 
//     * @author luyujian
//     * @param resourceRef
//     * @return
//     */
//    public void batchInsertRoleResRef(@Param("list") List<RoleResourceRef> ltRoleResourceRef);

    /**
     * 根据角色名称查询角色信息
     *
     * @param roleName
     * @return
     * @author luyujian
     */
    public List<RoleVo> queryRoleInfoByRoleName(@Param(value = "roleName") String roleName);

    /**
     * 根据ID查询角色信息
     *
     * @author luyujian
     * @param roleId
     * @return
     */
    // modify by wangshaofen[20140310]
    // public RoleMangerVo findRoleInfoById(long roleId);

//    /**
//     * 更新角色名称
//     *
//     * @author luyujian
//     * @param roleId
//     * @param roleName
//     * @return
//     */
//    public boolean updateRoleName(long roleId, String roleName);
//
//    /**
//     * 更新角色
//     *
//     * @author luyujian
//     * @return
//     */
//    public int updateRole();

    /**
     * 查询角色
     *
     * @param roleName
     * @return
     * @author luyujian
     */
    public List<Role> queryRoleByRoleName(@Param(value = "roleName") String roleName);

    /**
     * HEAD 获取资源列表
     *
     * @return
     * @author luyujian
     */
    public List<Map> getResourceList();

    /**
     * 获取所有角色
     *
     * @return
     * @author radishlee
     */
    public List<Role> findAllRole();

    /**
     * 找到根角色
     *
     * @return
     * @author radishlee
     */
    public Role findRootRole(long rootParentId);

    /**
     * 找到当前节点下的所有子节点
     *
     * @param roleId
     * @return
     * @author radishlee
     */
    public List<Role> findAllChildrenById(long roleId);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     * @author radish
     */
    public Map findById(long id);

    /**
     * 根据名称查询
     *
     * @param roleName
     * @return
     * @author radishlee
     */
    public Role findByRoleName(String roleName);

    /**
     * 更新全路径
     *
     * @param id
     * @param fullPath
     * @return
     * @author radishlee
     */
    public void updateFullPath(@Param(value = "id") long id, @Param(value = "fullPath") String fullPath);

    /**
     * @param roleName
     * @return
     * @author radishlee
     */
    public Role findByRoleNameBesideSelf(@Param(value = "roleName") String roleName, @Param(value = "id") long id);

    /**
     * 更新role
     *
     * @param role
     * @author radishlee
     */
    public void updateRole(Role role);

    /**
     * 删除
     *
     * @param id
     * @author radishlee
     */
    public void delete(long id);

    /**
     * 根据id查找父角色
     *
     * @param id
     * @return
     * @author wangshaofen
     */

    public Role findParentById(@Param(value = "id") long id);

    /**
     * 获取系统管理员角色
     *
     * @return
     * @author radish
     */
    public Map getAdminRole();

}
