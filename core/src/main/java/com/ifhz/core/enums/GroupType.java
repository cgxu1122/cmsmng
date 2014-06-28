package com.ifhz.core.enums;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 19:28
 */
public enum GroupType {
    TY(1L, "天音渠道"),
    DB(2L, "地包渠道"),
    QT(3L, "其他渠道"),
    LW(4L, "劳务渠道");

    public long VALUE;
    public String DESC;

    GroupType(long VALUE, String DESC) {
        this.VALUE = VALUE;
        this.DESC = DESC;
    }
}
