package com.ifhz.core.po;

import java.util.Date;

/**
 * 类描述
 * User: yangjian
 */
public class ProductInfo {
    private Long productId;
    private String productName;
    private Long partnerId;
    private String queryDataSource;
    private String active;
    private Date createTime;
    private Date updateTime;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getQueryDataSource() {
        return queryDataSource;
    }

    public void setQueryDataSource(String queryDataSource) {
        this.queryDataSource = queryDataSource;
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
