package com.ifhz.core.po;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/24
 * Time: 17:41
 */
public class PublishTask {
    private Long publishId;
    private Long packageId;
    private String packageName;
    private String pkgType;
    private Date effectTime;
    private String active;
    private Date createTime;
    private Date updateTime;

    private List<PubChlModRef> pubChlList;
    private List<PubChlModRef> pubModList;
    private String packageNameCondition;

    public String getPackageNameCondition() {
        return packageNameCondition;
    }

    public void setPackageNameCondition(String packageNameCondition) {
        this.packageNameCondition = packageNameCondition;
    }

    public String getPkgType() {
        return pkgType;
    }

    public void setPkgType(String pkgType) {
        this.pkgType = pkgType;
    }

    public List<PubChlModRef> getPubChlList() {
        return pubChlList;
    }

    public void setPubChlList(List<PubChlModRef> pubChlList) {
        this.pubChlList = pubChlList;
    }

    public List<PubChlModRef> getPubModList() {
        return pubModList;
    }

    public void setPubModList(List<PubChlModRef> pubModList) {
        this.pubModList = pubModList;
    }

    public Long getPublishId() {
        return publishId;
    }

    public void setPublishId(Long publishId) {
        this.publishId = publishId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
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
        final StringBuffer sb = new StringBuffer("PublishTask{");
        sb.append("publishId=").append(publishId);
        sb.append(", packageId=").append(packageId);
        sb.append(", packageName='").append(packageName).append('\'');
        sb.append(", effectTime=").append(effectTime);
        sb.append(", active='").append(active).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
