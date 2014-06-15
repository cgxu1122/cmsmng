package com.ifhz.core.constants;

import org.apache.commons.lang.StringUtils;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 18:36
 */
public enum AutoRun {

    Y("Y", 1),
    N("N", 0);

    public String dbValue;
    public int infValue;

    AutoRun(String dbValue, int infValue) {
        this.dbValue = dbValue;
        this.infValue = infValue;
    }

    public static AutoRun getByDbValue(String dbValue) {
        if (StringUtils.isNotBlank(dbValue)) {
            for (AutoRun active : AutoRun.values()) {
                if (StringUtils.equalsIgnoreCase(active.dbValue, dbValue.trim())) {
                    return active;
                }
            }
        }
        return null;
    }
}
