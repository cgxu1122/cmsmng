package com.ifhz.core.po;

import java.util.Date;

/**
 * 类描述
 * User: yangjian
 */
public class ApkInfo {
    private Long apkId;
    private String productName;
    private String apkName;
    private String ftpPath;
    private String active;
    private Date createTime;
    private Date updateTime;

    private String productNameCondition;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Long getApkId() {
        return apkId;
    }

    public void setApkId(Long apkId) {
        this.apkId = apkId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
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

    public String getProductNameCondition() {
        return productNameCondition;
    }

    public void setProductNameCondition(String productNameCondition) {
        this.productNameCondition = productNameCondition;
    }
}
