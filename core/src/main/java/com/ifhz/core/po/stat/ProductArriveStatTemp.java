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
    private String modelName;
    private Date arriveDate;
    private Date createTime;

    /**
     * 设备当天到达总数
     */
    private Long totalNum = 0L;
    /**
     * 设备当天到达-有效总数
     */
    private Long validNum = 0L;

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

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Long getValidNum() {
        return validNum;
    }

    public void setValidNum(Long validNum) {
        this.validNum = validNum;
    }
}
