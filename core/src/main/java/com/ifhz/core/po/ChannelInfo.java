package com.ifhz.core.po;

import java.util.Date;

/**
 * 类描述
 * User: yangjian
 */
public class ChannelInfo {
    private Long channelId;
    private Long parentId;
    private Long groupId;
    private Long mngId;
    private Long userId;
    private String channelName;
    private String desc;
    private String leaf;
    private Long laowuId;
    private String queryImeiSource;
    private String type;
    private String active;
    private Date createTime;
    private Date updateTime;

    private String groupIds;//供查询用,多选渠道组情况
    private String channelNameCondition;
    private String groupName;
    private String laowuName;
    private String username;
    private String password;
    private String address;
    private String contact;
    private String phone;
    private String mngName;

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public String getMngName() {
        return mngName;
    }

    public void setMngName(String mngName) {
        this.mngName = mngName;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getMngId() {
        return mngId;
    }

    public void setMngId(Long mngId) {
        this.mngId = mngId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getLaowuId() {
        return laowuId;
    }

    public void setLaowuId(Long laowuId) {
        this.laowuId = laowuId;
    }

    public String getQueryImeiSource() {
        return queryImeiSource;
    }

    public void setQueryImeiSource(String queryImeiSource) {
        this.queryImeiSource = queryImeiSource;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChannelNameCondition() {
        return channelNameCondition;
    }

    public void setChannelNameCondition(String channelNameCondition) {
        this.channelNameCondition = channelNameCondition;
    }

    public String getLaowuName() {
        return laowuName;
    }

    public void setLaowuName(String laowuName) {
        this.laowuName = laowuName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ChannelInfo{" +
                "channelId=" + channelId +
                ", parentId=" + parentId +
                ", groupId=" + groupId +
                ", mngId=" + mngId +
                ", userId=" + userId +
                ", channelName='" + channelName + '\'' +
                ", desc='" + desc + '\'' +
                ", leaf='" + leaf + '\'' +
                ", laowuId=" + laowuId +
                ", queryImeiSource='" + queryImeiSource + '\'' +
                ", type='" + type + '\'' +
                ", active='" + active + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", groupIds='" + groupIds + '\'' +
                ", channelNameCondition='" + channelNameCondition + '\'' +
                ", groupName='" + groupName + '\'' +
                ", laowuName='" + laowuName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", mngName='" + mngName + '\'' +
                '}';
    }
}
