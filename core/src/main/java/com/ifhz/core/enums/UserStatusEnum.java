/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.enums;

/**
 * 用户状态
 * 
 * @author radishlee
 */
public enum UserStatusEnum {
    /**
     * 默认启用
     */
    ENABLE(1, "启用"),
    /**
     * 禁用
     */
    DISABLE(2, "禁用");

    /**
     * 状态名称
     */
    private final String statusName;
    /**
     * 状态值
     */
    private final int statusValue;

    UserStatusEnum(int statusValue, String statusName) {
        this.statusName = statusName;
        this.statusValue = statusValue;
    }

    public String getStatusName() {
        return statusName;
    }

    public int getStatusValue() {
        return statusValue;
    }

    @Override
    public String toString() {
        return "StaffStatus{" + "statusName=" + statusName + ", statusValue=" + statusValue + '}';
    }

}
