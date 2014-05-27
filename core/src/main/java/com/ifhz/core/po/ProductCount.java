package com.ifhz.core.po;

import java.util.Date;

/**
 * Created by lm on 14-5-21.
 * liming
 */
public class ProductCount {
    private Long id;
    private String ua;
    private String modleName;
    private Long productId;
    private Long groupId;
    private Date countTime;
    private Long processDayCount;
    private Long allCount;
    private Long activeCount;
    private Long nonActiveCount;
    private Long nonActiveUninstallCount;
    private Long nonActiveReplaceCount;

    public Long getProcessDayCount() {
        return processDayCount;
    }

    public void setProcessDayCount(Long processDayCount) {
        this.processDayCount = processDayCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getModleName() {
        return modleName;
    }

    public void setModleName(String modleName) {
        this.modleName = modleName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Date getCountTime() {
        return countTime;
    }

    public void setCountTime(Date countTime) {
        this.countTime = countTime;
    }

    public Long getAllCount() {
        return allCount;
    }

    public void setAllCount(Long allCount) {
        this.allCount = allCount;
    }

    public Long getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(Long activeCount) {
        this.activeCount = activeCount;
    }

    public Long getNonActiveCount() {
        return nonActiveCount;
    }

    public void setNonActiveCount(Long nonActiveCount) {
        this.nonActiveCount = nonActiveCount;
    }

    public Long getNonActiveUninstallCount() {
        return nonActiveUninstallCount;
    }

    public void setNonActiveUninstallCount(Long nonActiveUninstallCount) {
        this.nonActiveUninstallCount = nonActiveUninstallCount;
    }

    public Long getNonActiveReplaceCount() {
        return nonActiveReplaceCount;
    }

    public void setNonActiveReplaceCount(Long nonActiveReplaceCount) {
        this.nonActiveReplaceCount = nonActiveReplaceCount;
    }
}
