package com.ifhz.core.service.imei.bean;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/16
 * Time: 22:41
 */
public class ImeiQuery {
    private String deviceTableName;
    private String counterTableName;

    public String getDeviceTableName() {
        return deviceTableName;
    }

    public void setDeviceTableName(String deviceTableName) {
        this.deviceTableName = deviceTableName;
    }

    public String getCounterTableName() {
        return counterTableName;
    }

    public void setCounterTableName(String counterTableName) {
        this.counterTableName = counterTableName;
    }
}
