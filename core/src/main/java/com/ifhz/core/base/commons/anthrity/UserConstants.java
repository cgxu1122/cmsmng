/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.base.commons.anthrity;

/**
 * @author radishlee
 */
public class UserConstants {

    // 用户类型
    public static final int USER_TYPE_NORMAL = 1; //普通用户
    public static final long USER_TYPE_MANAGER = 6l; //负责人

    // 用户状态
    public static final int USER_STATUS_ENABLE = 1; //启用
    public static final int USER_STATUS_DISABLE = 2; //禁用

    //用户角色
    public static final long QT_QUERY = 12l;//其他渠道查询用户角色
    public static final long LW_QUERY = 11l;//普通用户角色
    public static final long DB_QUERY = 9l; //负责人角色
    public static final long TY_QUERY = 8l; //管理员角色
    public static final long CP_QUERY = 10l; //管理员角色
}
