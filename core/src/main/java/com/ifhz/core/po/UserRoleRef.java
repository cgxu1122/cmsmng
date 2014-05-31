/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.po;

import java.util.Date;


/**
 * 后台用户与角色关联信息
 * 
 * @author luyujian
 */
public class UserRoleRef {

	/**
	 * @author luyujian
	 */
	private static final long serialVersionUID = -7619192658000607643L;

    private Long id;
	/**
	 * 后台用户ID
	 */
	private Long userId;


    /**
	 * 角色ID
	 */

	private Long roleId;

	/**
	 * 创建时间
	 */
	private Date createTime;



	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
