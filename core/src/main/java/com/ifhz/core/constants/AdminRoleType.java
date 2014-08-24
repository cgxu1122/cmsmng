package com.ifhz.core.constants;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 19:27
 */
public enum AdminRoleType {

    SuperAdmin(1, "超级管理员"),
    Admin(2, "管理员"),
    DWHZ(4, "对外合作组"),
    MngMan(6, "负责人组"),
    User(0, "非管理用户");

    public long rootId;
    public String desc;

    AdminRoleType(long rootId, String desc) {
        this.rootId = rootId;
        this.desc = desc;
    }

    public static boolean checkAdminRole(long rootId) {
        for (AdminRoleType type : AdminRoleType.values()) {
            if (type.rootId == rootId) {
                return true;
            }
        }

        return false;
    }
}
