package com.ifhz.core.po;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/24
 * Time: 16:59
 */
public class PublishChannelRef {
    private Long id;
    private Long publishId;
    private Long packageId;
    private Long channelId;
    private Date createTime;

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

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PublishChannelRef{");
        sb.append("id=").append(id);
        sb.append(", publishId=").append(publishId);
        sb.append(", packageId=").append(packageId);
        sb.append(", channelId=").append(channelId);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}
