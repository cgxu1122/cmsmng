/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */

package com.ifhz.core.po;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class User implements java.io.Serializable {
    private static final long serialVersionUID = -1235934852092626687L;
    private Long userId;
    private String loginName;
    private String realName;
    private String password;
    private String cellphone;
    private String address;
    private int status;
    private int type;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (status != user.status) return false;
        if (type != user.type) return false;
        if (address != null ? !address.equals(user.address) : user.address != null) return false;
        if (cellphone != null ? !cellphone.equals(user.cellphone) : user.cellphone != null) return false;
        if (!createTime.equals(user.createTime)) return false;
        if (!loginName.equals(user.loginName)) return false;
        if (!password.equals(user.password)) return false;
        if (!realName.equals(user.realName)) return false;
        if (!updateTime.equals(user.updateTime)) return false;
        if (!userId.equals(user.userId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + loginName.hashCode();
        result = 31 * result + realName.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (cellphone != null ? cellphone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + type;
        result = 31 * result + createTime.hashCode();
        return result;
    }

    public boolean equalss(User dbUser) {
        boolean flag = false;
        if (this.getUserId() == dbUser.getUserId() && this.getCellphone().equals(dbUser.getCellphone()) && this.getAddress().equals(dbUser.getAddress())) {
            return true;
        }
        return flag;
    }
}
