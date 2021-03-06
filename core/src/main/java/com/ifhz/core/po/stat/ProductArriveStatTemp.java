package com.ifhz.core.po.stat;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/17
 * Time: 15:41
 */
public class ProductArriveStatTemp implements Serializable {
    private static final long serialVersionUID = -3551096761354681538L;
    private Long id;
    private Long productId;
    private Long channelId;
    private Long groupId;
    private String ua;
    private Date statDate;
    private Date createTime;
    /**
     * 设备当天到达-有效总数
     */
    private Long validNum = 0L;
    /**
     * 设备当天到达-有效总数
     */
    private Long invalidNum = 0L;

    private String modelName;
    private String channelName;
    private String productName;
    private String groupName;
    private Date startDate;
    private Date endDate;

    private Long partnerId;
    private String queryImeiSource;

    public String getQueryImeiSource() {
        return queryImeiSource;
    }

    public void setQueryImeiSource(String queryImeiSource) {
        this.queryImeiSource = queryImeiSource;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public Date getStatDate() {
        return statDate;
    }

    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getValidNum() {
        return validNum;
    }

    public void setValidNum(Long validNum) {
        this.validNum = validNum;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getInvalidNum() {
        return invalidNum;
    }

    public void setInvalidNum(Long invalidNum) {
        this.invalidNum = invalidNum;
    }

    @Override
    public String toString() {
        return "ProductArriveStatTemp{" +
                "id=" + id +
                ", productId=" + productId +
                ", channelId=" + channelId +
                ", groupId=" + groupId +
                ", ua='" + ua + '\'' +
                ", statDate=" + statDate +
                ", createTime=" + createTime +
                ", validNum=" + validNum +
                ", invalidNum=" + invalidNum +
                ", modelName='" + modelName + '\'' +
                ", channelName='" + channelName + '\'' +
                ", productName='" + productName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", partnerId=" + partnerId +
                ", queryImeiSource='" + queryImeiSource + '\'' +
                '}';
    }
}
