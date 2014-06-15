package com.ifhz.core.po;

import java.util.Date;

/**
 * 类描述
 * User: yangjian
 */
public class DeviceInfo {
    private Long deviceId;
    private String deviceCode;
    private Long groupId;
    private Long channelId;
    private String active;
    private Date createTime;
    private Date updateTime;

    private String deviceCodeCondition;
    private String groupName;
    private String channelName;

    public String getDeviceCodeCondition() {
        return deviceCodeCondition;
    }

    public void setDeviceCodeCondition(String deviceCodeCondition) {
        this.deviceCodeCondition = deviceCodeCondition;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DeviceInfo{");
        sb.append("deviceId=").append(deviceId);
        sb.append(", deviceCode='").append(deviceCode).append('\'');
        sb.append(", groupId=").append(groupId);
        sb.append(", channelId=").append(channelId);
        sb.append(", active='").append(active).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deviceCodeCondition='").append(deviceCodeCondition).append('\'');
        sb.append(", groupName='").append(groupName).append('\'');
        sb.append(", channelName='").append(channelName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
