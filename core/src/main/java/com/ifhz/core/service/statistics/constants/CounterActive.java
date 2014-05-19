package com.ifhz.core.service.statistics.constants;

import org.apache.commons.lang.StringUtils;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/20
 * Time: 1:14
 */
public enum CounterActive {
    Valid(1, "1", "有效到达"),
    Invalid_Replace(2, "2", "无效到达-替换"),
    Invalid_Uninstall(3, "3", "无效到达-卸载");

    public final int type;
    public final String value;
    public final String desc;

    CounterActive(int type, String value, String desc) {
        this.type = type;
        this.value = value;
        this.desc = desc;
    }


    public static int getTypeByValue(String value) throws Exception {
        int ret = -1;
        if (StringUtils.isNotBlank(value)) {
            for (CounterActive ca : CounterActive.values()) {
                if (StringUtils.equalsIgnoreCase(value, ca.value)) {
                    ret = ca.type;
                }
            }
        }
        if (ret == -1) {
            throw new Exception("CounterActive type not defined");
        }
        return ret;
    }
}
