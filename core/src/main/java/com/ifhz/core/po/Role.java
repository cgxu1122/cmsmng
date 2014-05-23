/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Date;

/**
 * 角色信息
 *
 * @author luyujian
 */
public class Role {

    /**
     * @author luyujian
     */
    private static final long serialVersionUID = 8160562170865512904L;

    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 父Id
     */
    private Long parentId;

    /**
     * 全路径
     */
    private String fullPath;

    /**
     * 层级
     */
    private Integer levels;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "Role [roleName=" + roleName + ", createTime=" + createTime + "]";
    }

}