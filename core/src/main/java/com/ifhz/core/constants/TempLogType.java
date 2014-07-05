package com.ifhz.core.constants;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/5
 * Time: 14:01
 */
public enum TempLogType {
    //0：未上传 1：未统计 2：已统计
    UnDo(0, "未上传"),
    UnStat(1, "未统计"),
    Done(2, "已统计");
    public int value;
    public String Desc;

    TempLogType(int value, String desc) {
        this.value = value;
        Desc = desc;
    }

    public static TempLogType fromValue(int value) {
        for (TempLogType type : TempLogType.values()) {
            if (value == type.value) {
                return type;
            }
        }
        return null;
    }
}
