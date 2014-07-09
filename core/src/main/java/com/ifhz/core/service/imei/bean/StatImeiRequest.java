package com.ifhz.core.service.imei.bean;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 16:40
 */
public class StatImeiRequest implements Serializable {
    private static final long serialVersionUID = -5013021453932599349L;

    private ImeiQueryType type;
    private Long channelId;
    private Long groupId;
    private Long productId;
    private String ua;
    private Date processDate;
    private String deviceCode;
    private QueryActive active;
    private String channelName;
    private String groupName;
    private String productName;

    public StatImeiRequest(@Nonnull ImeiQueryType type) {
        this.type = type;
    }

    public ImeiQueryType getType() {
        return type;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public QueryActive getActive() {
        return active;
    }

    public void setActive(QueryActive active) {
        this.active = active;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
