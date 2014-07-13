package com.ifhz.core.constants;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/13
 * Time: 23:33
 */
public enum GroupEnums {
    TY(1, "天音渠道"),
    DB(2, "地包渠道"),
    QT(3, "其他渠道"),
    LW(4, "劳务渠道"),
    None(5, "未知");

    public long value;
    public String name;

    GroupEnums(long value, String name) {
        this.value = value;
        this.name = name;
    }

    public static GroupEnums fromByValue(Long value) {
        if (value == null) {
            return None;
        }
        for (GroupEnums groupEnums : GroupEnums.values()) {
            if (groupEnums.value == value.longValue()) {
                return groupEnums;
            }
        }
        return None;
    }


}
