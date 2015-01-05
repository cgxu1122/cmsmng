package com.ifhz.core.po;

import java.util.Date;

/**
 * 类描述
 * User: yangjian
 */
public class DeviceSystem {
    private Long systemId;
    private String version;
    private String ftpPath;
    private String downloadUrl;
    private String md5Value;
    private Date createTime;
    private Date updateTime;
    private Date effectiveTime;

    private String versionCondition;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getMd5Value() {
        return md5Value;
    }

    public void setMd5Value(String md5Value) {
        this.md5Value = md5Value;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVersionCondition() {
        return versionCondition;
    }

    public void setVersionCondition(String versionCondition) {
        this.versionCondition = versionCondition;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    @Override
    public String toString() {
        return "DeviceSystem{" +
                "systemId=" + systemId +
                ", version='" + version + '\'' +
                ", ftpPath='" + ftpPath + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", md5Value='" + md5Value + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", effectiveTime=" + effectiveTime +
                ", versionCondition='" + versionCondition + '\'' +
                '}';
    }
}
