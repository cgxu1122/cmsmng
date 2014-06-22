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

    private Long productPrsDayNum;
    private Long productUpdDayNum;
    private Long prsActiveTotalNum;
    private Long prsActiveValidNum;
    private Long prsActiveInvalidNum;
    private Long prsInvalidReplaceNum;
    private Long prsInvalidUninstallNum;
    private Long counterProductDayNum;

    private String queryKey;
    private String md5Key;


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

    public Long getCounterProductDayNum() {
        return counterProductDayNum;
    }

    public void setCounterProductDayNum(Long counterProductDayNum) {
        this.counterProductDayNum = counterProductDayNum;
    }

    public String getQueryKey() {
        return queryKey;
    }

    public void setQueryKey(String queryKey) {
        this.queryKey = queryKey;
    }
}
