package com.ifhz.core.po;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 15:17
 */
public class PubChlModRef {
    private Long id;
    private Long publishId;
    private Long groupId;
    private Long channelId;
    private Long packageId;
    private Long modelId;
    private String pkgType;
    private Date createTime;
    private Date updateTime;
    private String active;

    private String groupName;
    private String channelName;
    private String modelName;

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

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPublishId() {
        return publishId;
    }

    public void setPublishId(Long publishId) {
        this.publishId = publishId;
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

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getPkgType() {
        return pkgType;
    }

    public void setPkgType(String pkgType) {
        this.pkgType = pkgType;
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "PubChlModRef{" +
                "id=" + id +
                ", publishId=" + publishId +
                ", groupId=" + groupId +
                ", channelId=" + channelId +
                ", packageId=" + packageId +
                ", modelId=" + modelId +
                ", pkgType='" + pkgType + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", active='" + active + '\'' +
                ", groupName='" + groupName + '\'' +
                ", channelName='" + channelName + '\'' +
                ", modelName='" + modelName + '\'' +
                '}';
    }
}
