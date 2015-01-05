package com.ifhz.core.po;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: yangjian
 */
public class BatchInfo {
    private Long batchId;
    private String batchCode;
    private Long groupId;
    private Date startTime;
    private String batchProductName;
    private Integer batchProductNum;
    private String active;
    private Date createTime;
    private Date updateTime;

    private String batchCodeCondition;
    private List<ProductInfo> productInfoList;
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBatchCodeCondition() {
        return batchCodeCondition;
    }

    public void setBatchCodeCondition(String batchCodeCondition) {
        this.batchCodeCondition = batchCodeCondition;
    }

    public String getBatchProductName() {
        return batchProductName;
    }

    public void setBatchProductName(String batchProductName) {
        this.batchProductName = batchProductName;
    }

    public Integer getBatchProductNum() {
        return batchProductNum;
    }

    public void setBatchProductNum(Integer batchProductNum) {
        this.batchProductNum = batchProductNum;
    }

    public List<ProductInfo> getProductInfoList() {
        return productInfoList;
    }

    public void setProductInfoList(List<ProductInfo> productInfoList) {
        this.productInfoList = productInfoList;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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

    @Override
    public String toString() {
        return "BatchInfo{" +
                "batchId=" + batchId +
                ", batchCode='" + batchCode + '\'' +
                ", groupId=" + groupId +
                ", startTime=" + startTime +
                ", batchProductName='" + batchProductName + '\'' +
                ", batchProductNum=" + batchProductNum +
                ", active='" + active + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", batchCodeCondition='" + batchCodeCondition + '\'' +
                ", productInfoList=" + productInfoList +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
