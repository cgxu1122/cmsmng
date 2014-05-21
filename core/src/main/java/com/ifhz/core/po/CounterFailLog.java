package com.ifhz.core.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:11
 */
public class CounterFailLog implements Serializable {
    private static final long serialVersionUID = 3556396710839964295L;

    private Long failId;
    private String imei;
    private String ua;
    private String channelId;
    private String deviceCode;
    private String batchCode;
    private String processTime;
    private Date createTime;
    private String active;

    public Long getFailId() {
        return failId;
    }

    public void setFailId(Long failId) {
        this.failId = failId;
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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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

    public String getProcessTime() {
        return processTime;
    }

    public void setProcessTime(String processTime) {
        this.processTime = processTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CounterUploadLog{");
        sb.append("failId=").append(failId);
        sb.append(", imei='").append(imei).append('\'');
        sb.append(", ua='").append(ua).append('\'');
        sb.append(", channelId=").append(channelId);
        sb.append(", deviceCode='").append(deviceCode).append('\'');
        sb.append(", batchCode='").append(batchCode).append('\'');
        sb.append(", processTime=").append(processTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", active=").append(active);
        sb.append('}');
        return sb.toString();
    }
}
