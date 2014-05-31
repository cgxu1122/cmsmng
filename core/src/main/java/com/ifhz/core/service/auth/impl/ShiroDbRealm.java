/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.ifhz.core.service.auth.impl;

import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.enums.UserStatusEnum;
import com.ifhz.core.po.User;
import com.ifhz.core.po.UserRoleRef;
import com.ifhz.core.service.auth.ResourceService;
import com.ifhz.core.service.auth.RoleService;
import com.ifhz.core.service.auth.UserRoleRefService;
import com.ifhz.core.service.auth.UserService;
import com.ifhz.core.util.MD5keyUtil;
import com.ifhz.core.vo.RoleVo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

public class ShiroDbRealm extends AuthorizingRealm {
    private static Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);

    private UserService userService;

    private RoleService roleService;

    private ResourceService resourceService;

    private UserRoleRefService userRoleRefService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public ResourceService getResourceService() {
        return resourceService;
    }

    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    public UserRoleRefService getUserRoleRefService() {
        return userRoleRefService;
    }

    public void setUserRoleRefService(UserRoleRefService userRoleRefService) {
        this.userRoleRefService = userRoleRefService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo shiro = new SimpleAuthorizationInfo();
        Long id = shiroUser.roleId;
        List<String> pathList = resourceService.findFullpathByRoleId(id);
        shiro.addStringPermissions(pathList);
        shiro.addRole(shiroUser.roleName);
        return shiro;
    }

    /**
     * 用户认证
     * 
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        upToken.setRememberMe(true);
        String loginName = upToken.getUsername();
        String password = String.valueOf(upToken.getPassword());
        try {
            // 查询登陆用户
            User user = userService.findUserByLoginName(loginName);
            // 进行密码校验
            if (user != null && user.getPassword().equals(MD5keyUtil.getMD5Str(password)) && user.getStatus()==(UserStatusEnum.ENABLE.getStatusValue())) {
                RoleVo role = null;

                UserRoleRef userRoleRef = userRoleRefService.findRoleIdByUserId(user.getUserId());
                // 获取角色名称
                if (GlobalConstants.ADMIN_ROLE_ID == userRoleRef.getRoleId().intValue()) {// 如果是超级管理员
                    role = roleService.getAdminRole();
                } else {// 普通角色
                    role = roleService.findById(userRoleRef.getRoleId());
                }

                ShiroUser shiroUser = new ShiroUser(user.getUserId(), user.getLoginName(), user.getRealName(), role.getRoleId(), role.getRoleName());
                return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), getName());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationException();
        }

        return null;
    }

    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("MD5");
        matcher.setHashIterations(1);
        setCredentialsMatcher(matcher);
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        public Long userId;
        public String loginName;
        public String realName;
        public Long roleId;
        public String roleName;

        public ShiroUser(Long userId, String loginName,String realName, Long roleId, String roleName) {
            this.userId = userId;
            this.loginName = loginName;
            this.realName = realName;
            this.roleName = roleName;
            this.roleId = roleId;
        }

        public String getLoginName() {
            return loginName;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return loginName;
        }
    }
}
