package com.ifhz.core.constants;

import org.apache.commons.lang.StringUtils;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 16:32
 */
public enum PkgType {

    Normal("N", 2),
    Common("Y", 1);
    public String dbValue;
    public int apiValue;

    PkgType(String dbValue, int apiValue) {
        this.dbValue = dbValue;
        this.apiValue = apiValue;
    }

    public static PkgType getByDbValue(String dbValue) {
        if (StringUtils.isNotBlank(dbValue)) {
            for (PkgType type : PkgType.values()) {
                if (StringUtils.equalsIgnoreCase(type.dbValue, dbValue.trim())) {
                    return type;
                }
            }
        }
        return null;
    }
}
