package com.ifhz.core.constants;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 17:59
 */
public enum ApkType {
    NotCounter(1, 0),
    CounterApk(2, 1);
    public int dbValue;
    public int infValue;

    ApkType(int dbValue, int infValue) {
        this.dbValue = dbValue;
        this.infValue = infValue;
    }

    public static ApkType getByDbValue(int dbValue) {
        for (ApkType type : ApkType.values()) {
            if (type.dbValue == dbValue) {
                return type;
            }
        }

        return null;
    }
}
