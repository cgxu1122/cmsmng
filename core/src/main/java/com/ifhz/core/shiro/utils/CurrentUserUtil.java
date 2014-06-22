package com.ifhz.core.shiro.utils;

import com.ifhz.core.service.auth.impl.ShiroDbRealm;
import org.apache.shiro.SecurityUtils;

/**
 * @author radishlee
 */
public final class CurrentUserUtil {

    private static ShiroDbRealm.ShiroUser getShiroUser() {
        return (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    public static Long getUserId() {
        ShiroDbRealm.ShiroUser user = getShiroUser();
        if (user != null) {
            return user.userId;
        }

        return null;
    }

    public static Long getRoleId() {
        ShiroDbRealm.ShiroUser user = getShiroUser();
        if (user != null) {
            return user.roleId;
        }

        return null;
    }

    public static String getLoginName() {
        ShiroDbRealm.ShiroUser user = getShiroUser();
        if (user != null) {
            return user.loginName;
        }

        return null;
    }

    public static String getRoleName() {
        ShiroDbRealm.ShiroUser user = getShiroUser();
        if (user != null) {
            return user.roleName;
        }

        return null;
    }

    public static String getRealName() {
        ShiroDbRealm.ShiroUser user = getShiroUser();
        if (user != null) {
            return user.realName;
        }

        return null;
    }
}
