/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.tymng.controller;

import com.ifhz.core.po.User;
import com.ifhz.core.service.auth.UserService;
import com.ifhz.core.service.auth.impl.ShiroDbRealm;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author luyujian
 */
public class BaseController {
	@Autowired
    private UserService userService;

    private User staffInfo;
    
    public User getCuffStaff(){
		ShiroDbRealm.ShiroUser staff = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        staffInfo = userService.findById(staff.userId.longValue());
        return staffInfo;
    }

    public long getStaffId() {
        return this.getCuffStaff().getUserId();
    }

    public String getStaffName() {
        return this.getCuffStaff().getLoginName();
    }

}
