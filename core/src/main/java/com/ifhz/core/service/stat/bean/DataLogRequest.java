package com.ifhz.core.service.stat.bean;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/22
 * Time: 10:27
 */
public class DataLogRequest {

    private Date date;
    private String tableName;
    private String md5Key;
    private Date startTime;
    private Date endTime;
    private Integer active;
    //true 产品统计查询， false，流水统计
    private boolean productSwitch = false;
    //true 总条数 ，false 非总条数
    private boolean statSwitch = false;


    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isProductSwitch() {
        return productSwitch;
    }

    public void setProductSwitch(boolean productSwitch) {
        this.productSwitch = productSwitch;
    }

    public boolean isStatSwitch() {
        return statSwitch;
    }

    public void setStatSwitch(boolean statSwitch) {
        this.statSwitch = statSwitch;
    }
}
