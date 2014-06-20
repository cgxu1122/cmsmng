package com.ifhz.core.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:08
 */
public class DataLog implements Serializable {
    private static final long serialVersionUID = -8831800335295638799L;

    private Long id;
    private String imei;
    private String ua;
    private String modelName;
    private Long channelId;
    private Long groupId;
    private String deviceCode;
    private String batchCode;
    private Date processTime;
    private Date deviceUploadTime;
    private Integer active;
    private Date counterUploadTime;

    private String tableName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public Date getDeviceUploadTime() {
        return deviceUploadTime;
    }

    public void setDeviceUploadTime(Date deviceUploadTime) {
        this.deviceUploadTime = deviceUploadTime;
    }

    public Date getCounterUploadTime() {
        return counterUploadTime;
    }

    public void setCounterUploadTime(Date counterUploadTime) {
        this.counterUploadTime = counterUploadTime;
    }

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DataLog{");
        sb.append("id=").append(id);
        sb.append(", imei='").append(imei).append('\'');
        sb.append(", ua='").append(ua).append('\'');
        sb.append(", modelName='").append(modelName).append('\'');
        sb.append(", channelId='").append(channelId).append('\'');
        sb.append(", groupId=").append(groupId);
        sb.append(", deviceCode='").append(deviceCode).append('\'');
        sb.append(", batchCode='").append(batchCode).append('\'');
        sb.append(", processTime=").append(processTime);
        sb.append(", deviceUploadTime=").append(deviceUploadTime);
        sb.append(", counterUploadTime=").append(counterUploadTime);
        sb.append(", active=").append(active);
        sb.append(", tableName='").append(tableName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
