/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色管理VO
 *
 * @author luyujian
 */
public class RoleVo implements Serializable {
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
    private String parentName;
    /**
     * 全路径
     */
    private String fullPath;

    /**
     * 层级
     */
    private Long levels;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 删除权限
     */
    private Long delDisable;

    /**
     * 类型
     */
    private Long type;

    public Long getDelDisable() {
        return delDisable;
    }

    public void setDelDisable(Long delDisable) {
        this.delDisable = delDisable;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

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

    public Long getLevels() {
        return levels;
    }

    public void setLevels(Long levels) {
        this.levels = levels;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return "Role [roleName=" + roleName + ", createTime=" + createTime + "]";
    }

}
