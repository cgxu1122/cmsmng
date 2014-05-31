/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.po;


import java.util.Date;

/**
 * 角色与资源关联信息
 * 
 * @author luyujian
 */
public class RoleResourceRef  {

    /**
     * @author luyujian
     */
    private static final long serialVersionUID = 1240096385785586989L;
    private Long id;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 资源ID
     */
    private Long resourceId;
    /**
     * 据有的操作权限标识
     */
    private Integer acces;

    private Date createTime;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getAcces() {
        return acces;
    }

    public void setAcces(Integer acces) {
        this.acces = acces;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}
