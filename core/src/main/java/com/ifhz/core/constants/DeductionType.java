package com.ifhz.core.constants;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/13
 * Time: 19:54
 */
public enum DeductionType {
    Normal(0, "基础扣量"),
    Ladder(1, "阶梯扣量");
    public int dbValue;
    public String desc;

    DeductionType(int dbValue, String desc) {
        this.dbValue = dbValue;
        this.desc = desc;
    }

    public static DeductionType getByDbValue(Integer dbValue) {
        if (dbValue != null) {
            for (DeductionType active : DeductionType.values()) {
                if (active.dbValue == dbValue) {
                    return active;
                }
            }
        }
        return null;
    }
}
