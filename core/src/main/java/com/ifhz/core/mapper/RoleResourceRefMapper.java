package com.ifhz.core.mapper;

import java.util.List;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.RoleResourceRef;
import org.apache.ibatis.annotations.Param;


/**
 * 角色资源管理
 * 
 * @author radish
 */
public interface RoleResourceRefMapper  {
    /**
     * 获取所有角色下的资源
     * 
     * @author radishlee
     * @return
     */
    public List<RoleResourceRef> getRoleIdAndResourceId(Pagination page,@Param("roleId") long roleId, @Param("resourceId") long resourceId);

    /**
     * 根据角色id删除
     * 
     * @author radishlee
     * @param roleId
     */
    public void deleteAllRefByRoleId(long roleId);

    /**
     * 新增
     * 
     * @author radishlee
     * @param rrr
     */
    public void insert(RoleResourceRef rrr);

    /**
     * @author radishlee
     * @param resourceId
     */
    public void deleteAllRefByResId(long resourceId);

    List<RoleResourceRef> findAllResourceForRoleByRoleId(long roleId);

    List<RoleResourceRef> findAllAvaiableRes4RoleByRoleId(long parentId);

    RoleResourceRef findByResIdAndRoleId(@Param("roleId") long roleId, @Param("resourceId") long resourceId);

}
