/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.service.auth.impl;


import com.ifhz.core.mapper.UserRoleRefMapper;
import com.ifhz.core.po.UserRoleRef;
import com.ifhz.core.service.auth.UserRoleRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 角色资源管理
 *
 * @author radish
 */
@Service("userRoleRefService")
public class UserRoleRefServiceImpl implements UserRoleRefService{
    @Resource(name = "userRoleRefMapper")
    UserRoleRefMapper userRoleRefMapper;

    @Override
    public UserRoleRef findRoleIdByUserId(Long userId) {
        return userRoleRefMapper.findRoleByUserId(userId);
    }

    @Override
    public Integer insert(UserRoleRef urr) {
        return userRoleRefMapper.insert(urr);
    }

    @Override
    public List<UserRoleRef> findUserListRoleId(long roleId) {
        return userRoleRefMapper.findUserListRoleId(roleId);
    }
}
