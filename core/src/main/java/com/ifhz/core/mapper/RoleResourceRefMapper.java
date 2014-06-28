package com.ifhz.core.mapper;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.RoleResourceRef;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色资源管理
 *
 * @author radish
 */
public interface RoleResourceRefMapper {
    /**
     * 获取所有角色下的资源
     *
     * @return
     * @author radishlee
     */
    public List<RoleResourceRef> getRoleIdAndResourceId(Pagination page, @Param("roleId") long roleId, @Param("resourceId") long resourceId);

    /**
     * 根据角色id删除
     *
     * @param roleId
     * @author radishlee
     */
    public void deleteAllRefByRoleId(long roleId);

    /**
     * 新增
     *
     * @param rrr
     * @author radishlee
     */
    public void insert(RoleResourceRef rrr);

    /**
     * @param resourceId
     * @author radishlee
     */
    public void deleteAllRefByResId(long resourceId);

    List<RoleResourceRef> findAllResourceForRoleByRoleId(long roleId);

    List<RoleResourceRef> findAllAvaiableRes4RoleByRoleId(long parentId);

    RoleResourceRef findByResIdAndRoleId(@Param("roleId") long roleId, @Param("resourceId") long resourceId);

    int updateRoleResourceRefNotAccess(@Param("roleId") long roleId, @Param("resourceId") long resourceId);

    int updateRoleResourceRefAccess(@Param("roleId") long roleId, @Param("resourceId") long resourceId);
}
