package com.ifhz.core.service.stat.constants;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 17:22
 */
public enum CounterActive {

    Valid(1, "有效到达"),
    Invalid_Replace(2, "无效到达-替换"),
    Invalid_Uninstall(3, "无效到达-卸载"),
    Invalid_Re_And_Un(4, "无效到达-替换-卸载");

    public final int value;
    public final String desc;

    CounterActive(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
