/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 角色管理VO
 *
 * @author luyujian
 */
public class RoleVo implements Serializable {

    /**
     * @author luyujian
     */
    private static final long serialVersionUID = -571082159946899587L;

    /**
     * 角色ID
     */
    private long roleId;
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 父Id
     */
    private long parentId;

    /**
     * 父名称
     */
    private String parentName;

    /**
     * 全路径
     */
    private String fullPath;

    /**
     * 层级
     */
    private int level;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String createTime;


    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RoleMangerVo [roleId=" + roleId + ", roleName=" + roleName + ", createTime=" + createTime + "]";
    }

}
