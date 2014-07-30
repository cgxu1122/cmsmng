package com.ifhz.core.constants;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 23:25
 */
public enum CanDel {

    N(0, "不能删除"),
    Y(1, "能删除");
    public int dbValue;
    public String desc;

    CanDel(int dbValue, String desc) {
        this.dbValue = dbValue;
        this.desc = desc;
    }

    public static CanDel getByDbValue(Integer dbValue) {
        if (dbValue != null) {
            for (CanDel active : CanDel.values()) {
                if (active.dbValue == dbValue) {
                    return active;
                }
            }
        }
        return null;
    }
}
