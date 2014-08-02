package com.ifhz.core.service.api.bean;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/2
 * Time: 14:33
 */
public enum ImeiStatus {
    Success(0),
    Invalid(1),
    Repeat(2),
    Failure(3);


    public int status;

    ImeiStatus(int status) {
        this.status = status;
    }
}
