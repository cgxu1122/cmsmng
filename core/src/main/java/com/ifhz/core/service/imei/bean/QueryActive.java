package com.ifhz.core.service.imei.bean;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 16:45
 */

public enum QueryActive {
    Total(1, "全部查询"),
    Valid(2, "有效到达"),
    Invalid(3, "无效到达"),
    Replace(4, "无效替换"),
    Uninstall(5, "无效卸载"),
    UnAndRe(6, "无效卸载+替换");

    public final int value;
    public final String desc;

    QueryActive(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
