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
package com.ifhz.core.service.auther.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.constants.Active;
import com.ifhz.core.constants.AdminRoleType;
import com.ifhz.core.po.auth.SysRole;
import com.ifhz.core.po.auth.SysUser;
import com.ifhz.core.service.auther.SysAuthService;
import com.ifhz.core.service.auther.SysRoleService;
import com.ifhz.core.service.auther.SysUserService;
import com.ifhz.core.shiro.exception.CaptchaException;
import com.ifhz.core.shiro.exception.UserNamePasswordErrorException;
import com.ifhz.core.shiro.token.UsernamePasswordCaptchaToken;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

public class ShiroDbRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroDbRealm.class);
    private SysUserService sysUserService;
    private SysRoleService sysRoleService;
    private SysAuthService sysAuthService;

    @Log
    public SysUserService getSysUserService() {
        return sysUserService;
    }

    @Log
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Log
    public SysRoleService getSysRoleService() {
        return sysRoleService;
    }

    @Log
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Log
    public SysAuthService getSysAuthService() {
        return sysAuthService;
    }

    @Log
    public void setSysAuthService(SysAuthService sysAuthService) {
        this.sysAuthService = sysAuthService;
    }

    @Override
    @Log
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo shiro = new SimpleAuthorizationInfo();
        List<String> resUrlList = sysAuthService.queryResUrlListByRoleId(shiroUser.roleId);
        shiro.addStringPermissions(resUrlList);
        shiro.addRole(shiroUser.roleName);

        return shiro;
    }

    /**
     * 用户认证
     */
    @Override
    @Log
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordCaptchaToken upToken = (UsernamePasswordCaptchaToken) token;
        upToken.setRememberMe(true);
        String loginName = upToken.getUsername();
        String password = String.valueOf(upToken.getPassword());
        String captcha = ((UsernamePasswordCaptchaToken) token).getCaptcha();
        String exitCode = (String) SecurityUtils.getSubject().getSession().getAttribute("validateCode");

        try {
            if (null == captcha || !captcha.equalsIgnoreCase(exitCode)) {
                throw new CaptchaException("验证码错误");
            }
            // 查询登陆用户
            SysUser user = sysUserService.getByLoginName(loginName);
            LOGGER.info("login_user:{}", JSON.toJSONString(user));
            // 进行密码校验
            if (user != null && user.getPassword().equals(password) && StringUtils.equalsIgnoreCase(user.getActive(), Active.Y.dbValue)) {
                Long roleId = user.getRoleId();
                SysRole role = sysRoleService.getById(roleId);
                AdminRoleType type = AdminRoleType.User;
                // 获取角色名称
                if (role.getRootId().longValue() == AdminRoleType.SuperAdmin.rootId) {// 超级管理员
                    type = AdminRoleType.SuperAdmin;
                } else if (role.getRootId().longValue() == AdminRoleType.Admin.rootId) {// 管理员角色
                    type = AdminRoleType.Admin;
                } else if (role.getRootId().longValue() == AdminRoleType.MngMan.rootId) {// 负责人
                    type = AdminRoleType.MngMan;
                } else if (role.getRootId().longValue() == AdminRoleType.DWHZ.rootId) {//对外合作组
                    type = AdminRoleType.DWHZ;
                }

                ShiroUser shiroUser = new ShiroUser(user.getUserId(), user.getLoginName(), user.getRealName(), role.getRoleId(), role.getRoleName(), type);
                return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), getName());
            } else {
                throw new UserNamePasswordErrorException("用户名密码错误");
            }
        } catch (CaptchaException e) {
            LOGGER.error("CaptchaException error", e);
            throw e;
        } catch (UserNamePasswordErrorException e) {
            LOGGER.error("UserNamePasswordErrorException error", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Exception error", e);
            throw new AuthenticationException();
        }
    }

    @PostConstruct
    @Log
    public void initCredentialsMatcher() {
        SimpleCredentialsMatcher matcher = new SimpleCredentialsMatcher();
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
        public AdminRoleType type;

        public ShiroUser(Long userId, String loginName, String realName, Long roleId, String roleName, AdminRoleType type) {
            this.userId = userId;
            this.loginName = loginName;
            this.realName = realName;
            this.roleName = roleName;
            this.roleId = roleId;
            this.type = type;
        }

        public String getLoginName() {
            return loginName;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        @Log
        public String toString() {
            return loginName;
        }
    }
}
