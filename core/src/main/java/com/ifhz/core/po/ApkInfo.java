package com.ifhz.core.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/24
 * Time: 12:56
 */
public class ApkInfo implements Serializable {
    private static final long serialVersionUID = 816717945758726323L;
    /*
    APK_ID         			number(15)           not null,
   APK_NAME            varchar2(50)					not null,
   SOFT_NAME           varchar2(100)					not null,
   FTP_PATH        		varchar2(500)         not null,
   MD5VALUE        		varchar2(500)         not null,
   DOWNLOAD_URL       varchar2(500)         not null,
   ACTIVE              varchar2(2)           not null,
   TYPE                varchar2(2)           not null,
   CREATE_TIME        		date               default SYSDATE not null,
   UPDATE_TIME        		date               default SYSDATE not null
     */

    private Long apkId;
    private String apkName;
    private String softName;
    private String ftpPath;
    private String md5Value;
    private String downloadUrl;
    private String active;
    private String type;
    private Date createTime;
    private Date updateTime;

    private String apkNameCondition;


    public Long getApkId() {
        return apkId;
    }

    public void setApkId(Long apkId) {
        this.apkId = apkId;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getSoftName() {
        return softName;
    }

    public void setSoftName(String softName) {
        this.softName = softName;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    public String getMd5Value() {
        return md5Value;
    }

    public void setMd5Value(String md5Value) {
        this.md5Value = md5Value;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getApkNameCondition() {
        return apkNameCondition;
    }

    public void setApkNameCondition(String apkNameCondition) {
        this.apkNameCondition = apkNameCondition;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ApkInfo{");
        sb.append("apkId=").append(apkId);
        sb.append(", apkName='").append(apkName).append('\'');
        sb.append(", softName='").append(softName).append('\'');
        sb.append(", ftpPath='").append(ftpPath).append('\'');
        sb.append(", md5Value='").append(md5Value).append('\'');
        sb.append(", downloadUrl='").append(downloadUrl).append('\'');
        sb.append(", active='").append(active).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
