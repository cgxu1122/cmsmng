package com.ifhz.core.service.stat.bean;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/22
 * Time: 15:30
 */
public enum ImeiRequestEnums {
    DeviceProcess("设备当天累计加工"),
    DeviceUpload("设备当天累计上传"),
    CounterTotal("设备当天累计上传"),
    CounterValid("设备当天累计上传"),
    CounterInvalid("设备当天累计上传"),
    InvalidReplace("设备当天累计上传"),
    InvalidUninstall("设备当天累计上传"),

    ProductProcess("产品当天累计上传"),
    ProductUpload("产品当天累计上传");

    private String desc;

    ImeiRequestEnums(String desc) {
        this.desc = desc;
    }
}
