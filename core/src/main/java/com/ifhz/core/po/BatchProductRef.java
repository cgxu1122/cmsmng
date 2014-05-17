package com.ifhz.core.po;

import java.util.Date;

/**
 * 类描述
 * User: yangjian
 */
public class BatchProductRef {
    private Long batchId;
    private Long productId;
    private Date createTime;

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
}
