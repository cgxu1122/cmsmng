package com.ifhz.core.po;

import java.util.Date;

/**
 * 类描述
 * User: yangjian
 */
public class BatchProductRef {
    private Long batchId;
    private String batchCode;
    private Long productId;
    private Date createTime;

    private String productName;

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BatchProductRef{" +
                "batchId=" + batchId +
                ", batchCode='" + batchCode + '\'' +
                ", productId=" + productId +
                ", createTime=" + createTime +
                ", productName='" + productName + '\'' +
                '}';
    }
}
