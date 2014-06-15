package com.ifhz.api.constants;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/19
 * Time: 23:32
 */
public enum ResultType {
    SuccNonUpgrade(0, "成功或者成功，不需要更新"),
    Fail(1, "失败"),
    SuccUpgrade(2, "成功且需要更新");

    public final int VALUE;
    public final String DESC;

    ResultType(int VALUE, String DESC) {
        this.VALUE = VALUE;
        this.DESC = DESC;
    }
}
