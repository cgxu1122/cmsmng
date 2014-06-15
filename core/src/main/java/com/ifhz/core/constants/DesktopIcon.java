package com.ifhz.core.constants;

import org.apache.commons.lang.StringUtils;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 18:37
 */
public enum DesktopIcon {

    Y("Y", 1),
    N("N", 0);
    public String dbValue;
    public int infValue;

    DesktopIcon(String dbValue, int infValue) {
        this.dbValue = dbValue;
        this.infValue = infValue;
    }

    public static DesktopIcon getByDbValue(String dbValue) {
        if (StringUtils.isNotBlank(dbValue)) {
            for (DesktopIcon active : DesktopIcon.values()) {
                if (StringUtils.equalsIgnoreCase(active.dbValue, dbValue.trim())) {
                    return active;
                }
            }
        }
        return null;
    }
}
