package com.ifhz.core.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 1:06
 */
public class ProductStat implements Serializable {
    private static final long serialVersionUID = 3002207540023716277L;
    private Long id;
    private Long productId;
    private Long groupId;
    private String ua;
    private String modelName;
    private Date processDate;
    private String batchCode;
    private Date createTime;

    /**
     * 产品当天安装数量
     */
    private Long productPrsDayNum = 0L;
    /**
     * 产品当天安装上传数量
     */
    private Long productUpdDayNum = 0L;
    /**
     * 产品安装数量中已到达总数
     */
    private Long prsActiveTotalNum = 0L;
    /**
     * 产品安装数量中已到达-有效总数
     */
    private Long prsActiveValidNum = 0L;
    /**
     * 产品安装数量中已到达-无效总数
     */
    private Long prsActiveInvalidNum = 0L;
    /**
     * 产品安装数量中已到达-无效替换总数
     */
    private Long prsInvalidReplaceNum = 0L;
    /**
     * 产品安装数量中已到达-无效卸载总数
     */
    private Long prsInvalidUninstallNum = 0L;
    /**
     * 产品安装数量中已到达-无效卸载+替换总数
     */
    private Long prsInvalidUnAndReNum = 0L;
    private Integer version;

    private String md5Key;
    private Date startDate;
    private Date endDate;

    private String groupName;
    private String productName;
    private Long partnerId;
    private String queryImeiSource;

    public String getQueryImeiSource() {
        return queryImeiSource;
    }

    public void setQueryImeiSource(String queryImeiSource) {
        this.queryImeiSource = queryImeiSource;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

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

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public Long getProductPrsDayNum() {
        return productPrsDayNum;
    }

    public void setProductPrsDayNum(Long productPrsDayNum) {
        this.productPrsDayNum = productPrsDayNum;
    }

    public Long getProductUpdDayNum() {
        return productUpdDayNum;
    }

    public void setProductUpdDayNum(Long productUpdDayNum) {
        this.productUpdDayNum = productUpdDayNum;
    }

    public Long getPrsActiveTotalNum() {
        return prsActiveTotalNum;
    }

    public void setPrsActiveTotalNum(Long prsActiveTotalNum) {
        this.prsActiveTotalNum = prsActiveTotalNum;
    }

    public Long getPrsActiveValidNum() {
        return prsActiveValidNum;
    }

    public void setPrsActiveValidNum(Long prsActiveValidNum) {
        this.prsActiveValidNum = prsActiveValidNum;
    }

    public Long getPrsActiveInvalidNum() {
        return prsActiveInvalidNum;
    }

    public void setPrsActiveInvalidNum(Long prsActiveInvalidNum) {
        this.prsActiveInvalidNum = prsActiveInvalidNum;
    }

    public Long getPrsInvalidReplaceNum() {
        return prsInvalidReplaceNum;
    }

    public void setPrsInvalidReplaceNum(Long prsInvalidReplaceNum) {
        this.prsInvalidReplaceNum = prsInvalidReplaceNum;
    }

    public Long getPrsInvalidUninstallNum() {
        return prsInvalidUninstallNum;
    }

    public void setPrsInvalidUninstallNum(Long prsInvalidUninstallNum) {
        this.prsInvalidUninstallNum = prsInvalidUninstallNum;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getPrsInvalidUnAndReNum() {
        return prsInvalidUnAndReNum;
    }

    public void setPrsInvalidUnAndReNum(Long prsInvalidUnAndReNum) {
        this.prsInvalidUnAndReNum = prsInvalidUnAndReNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
