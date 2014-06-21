package com.ifhz.core.enums;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 19:28
 */
public enum GroupType {
    TY(1, "天音渠道"),
    DB(2, "地包渠道"),
    QT(3, "其他渠道"),
    LW(4, "劳务渠道");

    public int VALUE;
    public String DESC;

    GroupType(int VALUE, String DESC) {
        this.VALUE = VALUE;
        this.DESC = DESC;
    }
}
