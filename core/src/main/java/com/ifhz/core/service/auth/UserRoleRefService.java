/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.service.auth;


import com.ifhz.core.po.UserRoleRef;

import java.util.List;

/**
 * 角色资源管理
 * 
 * @author radish
 */
public interface UserRoleRefService {

    UserRoleRef findRoleIdByUserId(Long userId);

    Integer insert(UserRoleRef urr);
}
