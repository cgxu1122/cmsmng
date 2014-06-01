package com.ifhz.core.po;

import java.util.Date;

/**
 * 类描述
 * User: yangjian
 */
public class PartnerInfo {
    private Long partnerId;
    private Long userId;
    private String partnerName;
    private String queryImeiSource;
    private String exportImeiSource;
    private String active;
    private Date createTime;
    private Date updateTime;

    private String partnerNameCondition;
    private String username;
    private String password;

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

    public String getPartnerNameCondition() {
        return partnerNameCondition;
    }

    public void setPartnerNameCondition(String partnerNameCondition) {
        this.partnerNameCondition = partnerNameCondition;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getQueryImeiSource() {
        return queryImeiSource;
    }

    public void setQueryImeiSource(String queryImeiSource) {
        this.queryImeiSource = queryImeiSource;
    }

    public String getExportImeiSource() {
        return exportImeiSource;
    }

    public void setExportImeiSource(String exportImeiSource) {
        this.exportImeiSource = exportImeiSource;
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
}
