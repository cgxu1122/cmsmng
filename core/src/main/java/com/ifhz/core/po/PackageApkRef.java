package com.ifhz.core.po;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/24
 * Time: 17:07
 */
public class PackageApkRef {
    private Long id;
    private Long packageId;
    private Long apkId;
    private String apkName;
    private String autoRun;
    private String desktopIcon;
    private String active;
    private Integer sort;
    private Integer apkType;
    private Date createTime;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

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

    public String getAutoRun() {
        return autoRun;
    }

    public void setAutoRun(String autoRun) {
        this.autoRun = autoRun;
    }

    public String getDesktopIcon() {
        return desktopIcon;
    }

    public void setDesktopIcon(String desktopIcon) {
        this.desktopIcon = desktopIcon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getApkType() {
        return apkType;
    }

    public void setApkType(Integer apkType) {
        this.apkType = apkType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PackageApkRef{");
        sb.append("id=").append(id);
        sb.append(", packageId=").append(packageId);
        sb.append(", apkId=").append(apkId);
        sb.append(", apkName='").append(apkName).append('\'');
        sb.append(", autoRun='").append(autoRun).append('\'');
        sb.append(", desktopIcon='").append(desktopIcon).append('\'');
        sb.append(", active='").append(active).append('\'');
        sb.append(", sort=").append(sort);
        sb.append(", apkType=").append(apkType);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}
