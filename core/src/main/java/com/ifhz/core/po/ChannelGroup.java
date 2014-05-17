package com.ifhz.core.po;

import java.util.Date;

/**
 * 类描述
 * User: yangjian
 */
public class ChannelGroup {
    private Long groupId;
    private String groupName;
    private Date createTime;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
