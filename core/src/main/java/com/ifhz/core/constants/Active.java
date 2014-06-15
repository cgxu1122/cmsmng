package com.ifhz.core.constants;

import org.apache.commons.lang.StringUtils;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 17:21
 */
public enum Active {

    Y("Y", 1),
    N("N", 2);
    public String dbValue;
    public int infValue;

    Active(String dbValue, int infValue) {
        this.dbValue = dbValue;
        this.infValue = infValue;
    }

    public static Active getByDbValue(String dbValue) {
        if (StringUtils.isNotBlank(dbValue)) {
            for (Active active : Y.values()) {
                if (StringUtils.equalsIgnoreCase(active.dbValue, dbValue.trim())) {
                    return active;
                }
            }
        }
        return null;
    }
}
