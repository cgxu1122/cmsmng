package com.ifhz.core.po;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/16
 * Time: 21:02
 */
public class SettleInfo {
    private Long settleId;
    private String modelName;
    private Date startTime;
    private Date endTime;
    private Double price;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private Long createBy;

    private String modelNameCondition;

    public String getModelNameCondition() {
        return modelNameCondition;
    }

    public void setModelNameCondition(String modelNameCondition) {
        this.modelNameCondition = modelNameCondition;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getSettleId() {
        return settleId;
    }

    public void setSettleId(Long settleId) {
        this.settleId = settleId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "SettleInfo{" +
                "settleId=" + settleId +
                ", modelName='" + modelName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", price=" + price +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createBy=" + createBy +
                ", modelNameCondition='" + modelNameCondition + '\'' +
                '}';
    }
}
