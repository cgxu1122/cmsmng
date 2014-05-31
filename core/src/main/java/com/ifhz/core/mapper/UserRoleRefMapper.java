/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.mapper;

import com.ifhz.core.po.RoleResourceRef;
import com.ifhz.core.po.UserRoleRef;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色资源管理
 * 
 * @author radish
 */
public interface UserRoleRefMapper {

    UserRoleRef findRoleIdByUserId(Long userId);

    Integer deleteAllRefByRoleId(Long roleId);

    Integer deleteAllRefByUserId(Long userId);

    Integer insert(UserRoleRef userRoleRef);
}
