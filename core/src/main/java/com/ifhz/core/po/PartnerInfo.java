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
