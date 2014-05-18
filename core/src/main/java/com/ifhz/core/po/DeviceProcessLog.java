package com.ifhz.core.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:08
 */
public class DeviceProcessLog implements Serializable {
    private static final long serialVersionUID = -8831800335295638799L;
    /*
     process_id   NUMBER(15) not null,
  imei         varchar2(50),
  ua           varchar2(100),
  channel_id   number(15),
  device_code  varchar2(100),
  group_id     number(15),
  batch_code   varchar2(100),
  process_time date,
  create_time  date default SYSDATE not null,
     */


    private Long processId;
    private String imei;
    private String ua;
    private Long channelId;
    private Long groupId;
    private String deviceCode;
    private String batchCode;
    private Date processTime;
    private Date createTime;

    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DeviceProcessLog{");
        sb.append("processId=").append(processId);
        sb.append(", imei='").append(imei).append('\'');
        sb.append(", ua='").append(ua).append('\'');
        sb.append(", channelId=").append(channelId);
        sb.append(", groupId=").append(groupId);
        sb.append(", deviceCode='").append(deviceCode).append('\'');
        sb.append(", batchCode='").append(batchCode).append('\'');
        sb.append(", processTime=").append(processTime);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}
