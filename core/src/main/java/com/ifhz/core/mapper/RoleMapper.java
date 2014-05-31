/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.mapper;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.Role;
import com.ifhz.core.po.RoleResourceRef;
import com.ifhz.core.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.awt.print.Pageable;
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
    public Integer insertRole(Role role);

    /**
     * 批量添加角色与资源关联关系
     *
    /**
     * 查询角色
     *
     * @param roleName
     * @return
     * @author luyujian
     */
    public List<Role> queryRoleByRoleName(@Param(value = "roleName") String roleName);

//    /**
//     * HEAD 获取资源列表
//     *
//     * @return
//     * @author luyujian
//     */
//    public List<Map> getResourceList();

//     * @author luyujian
//     * @param ltRoleResourceRef
//     * @return
//     */

//    public Integer batchInsertRoleResRef(@Param("list") List<RoleResourceRef> ltRoleResourceRef);
//    /**
//     * 根据角色名称查询角色信息
//     *
//     * @param roleName
//     * @return
//     * @author luyujian
//     */

//    public List<RoleVo> queryRoleInfoByRoleName(Pagination page,@Param(value = "roleName") String roleName);

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
    public Role findRootRole();

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
     * 获取系统管理员角色
     *
     * @return
     * @author radish
     */
    public Role getAdminRole();

    /**
     * 根据id查找父角色
     *
     * @param id
     * @return
     * @author wangshaofen
     */
    public Role findParentById(Pagination page,@Param(value = "id") long id);

    /**
     * 根据名称查询
     *
     * @param roleName
     * @return
     * @author radishlee
     */
    public Role findByRoleName(String roleName);

    /**
     * @param roleName
     * @return
     * @author radishlee
     */
    public Role findByRoleNameBesideSelf(Pagination pape,@Param(value = "roleName") String roleName, @Param(value = "id") long id);

    /**
     * 更新全路径
     *
     * @param id
     * @param fullPath
     * @return
     * @author radishlee
     */
    public Integer updateFullPath(@Param(value = "id") long id, @Param(value = "fullPath") String fullPath);

    /**
     * 更新role
     *
     * @param role
     * @author radishlee
     */
    public int updateRole(Role role);


    /**
     * 删除
     *
     * @param id
     * @author radishlee
     */
    public int delete(long id);

}
