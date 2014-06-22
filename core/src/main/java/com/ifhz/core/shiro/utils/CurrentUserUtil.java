package com.ifhz.core.shiro.utils;

import com.ifhz.core.po.User;
import com.ifhz.core.service.auth.UserService;
import com.ifhz.core.service.auth.impl.ShiroDbRealm;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author radishlee
 */
public class CurrentUserUtil {
    private Long userId;
    private Long roleId;
    private String loginName;
    private String roleName;
    private String realName;


    public  CurrentUserUtil(){
        ShiroDbRealm.ShiroUser staff = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        userId = staff.userId.longValue();
        roleId = staff.roleId.longValue();
        loginName = staff.loginName.toString();
        String realName = staff.realName.toString();
        String roleName = staff.roleName.toString();
    }

    public long getUserId() {
        return this.userId;
    }

    public long getRoleId() {
        return this.roleId;
    }

    public String getLoginName() {
        return this.loginName;
    }
    public String getRoleName() {
        return this.roleName;
    }
    public String getRealName() {
        return this.realName;
    }
}
