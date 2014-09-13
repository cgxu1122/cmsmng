package com.ifhz.core.po.stat;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/9
 * Time: 0:28
 */
public class StatDeduction implements Serializable {
    private static final long serialVersionUID = 8570398987901981413L;

    private Long id;
    private Long channelId;
    private Long groupId;
    private Integer type;
    private Integer basicNum;
    private Double percentage;
    private Date createTime;
    private Date updateTime;

    private String channelNameCondition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBasicNum() {
        return basicNum;
    }

    public void setBasicNum(Integer basicNum) {
        this.basicNum = basicNum;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
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

    public String getChannelNameCondition() {
        return channelNameCondition;
    }

    public void setChannelNameCondition(String channelNameCondition) {
        this.channelNameCondition = channelNameCondition;
    }
}
