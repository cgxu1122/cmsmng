package com.ifhz.core.service.imei.bean;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 16:58
 */
public enum ImeiQueryType {

    Day_Device_Process(1),
    Day_Device_Upload(2),
    Day_Counter_Upload(3);

    public final int value;

    ImeiQueryType(int value) {
        this.value = value;
    }
}
