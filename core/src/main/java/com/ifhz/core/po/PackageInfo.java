package com.ifhz.core.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/24
 * Time: 16:53
 */
public class PackageInfo implements Serializable {
    private static final long serialVersionUID = 8826954392921248033L;

    private Long packageId;
    private String packageName;
    private Long groupId;
    private Long batchId;
    private String batchCode;
    private String type;
    private String active;
    private String remark;
    private Date createTime;
    private Date updateTime;

    private List<PackageApkRef> packageApkRefList;

    private String packageNameCondition;
    private Long groupIdCondition;
    private String groupName;

    public List<PackageApkRef> getPackageApkRefList() {
        return packageApkRefList;
    }

    public Long getGroupIdCondition() {
        return groupIdCondition;
    }

    public void setGroupIdCondition(Long groupIdCondition) {
        this.groupIdCondition = groupIdCondition;
    }

    public void setPackageApkRefList(List<PackageApkRef> packageApkRefList) {
        this.packageApkRefList = packageApkRefList;
    }

    public String getPackageNameCondition() {
        return packageNameCondition;
    }

    public void setPackageNameCondition(String packageNameCondition) {
        this.packageNameCondition = packageNameCondition;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
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
        final StringBuffer sb = new StringBuffer("PackageInfo{");
        sb.append("packageId=").append(packageId);
        sb.append(", batchId=").append(batchId);
        sb.append(", batchCode='").append(batchCode).append('\'');
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
