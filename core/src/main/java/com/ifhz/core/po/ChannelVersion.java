package com.ifhz.core.po;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 19:10
 */
public class ChannelVersion {

    public static final Long ApkLib_ChannelId = -1L;
    /*
    create table TY_CHANNEL_VERSION
(
  version_id  NUMBER(15) not null,
  channel_id  NUMBER(15) not null,
  md5value    VARCHAR2(50) not null,
  path        VARCHAR2(500) not null,
  version    VARCHAR2(50) not null,
  create_time DATE not null,
  update_time DATE not null
)
     */
    private Long versionId;
    private Long channelId;
    private String md5Value;
    private String path;
    private String version;
    private Date createTime;
    private Date updateTime;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getMd5Value() {
        return md5Value;
    }

    public void setMd5Value(String md5Value) {
        this.md5Value = md5Value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        return "ChannelVersion{" +
                "versionId=" + versionId +
                ", channelId=" + channelId +
                ", md5Value='" + md5Value + '\'' +
                ", path='" + path + '\'' +
                ", version='" + version + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
