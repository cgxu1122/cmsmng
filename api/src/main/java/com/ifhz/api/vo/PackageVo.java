package com.ifhz.api.vo;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/2
 * Time: 21:13
 */
public class PackageVo implements Serializable {
    private static final long serialVersionUID = 6942901369675318485L;

    private Long packageId;
    private Long name;
    private String batchCode;
    private int type;
    private List<String> modelList = Lists.newArrayList();
    private List<PackageApkVo> apkList = Lists.newArrayList();

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public Long getName() {
        return name;
    }

    public void setName(Long name) {
        this.name = name;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getModelList() {
        return modelList;
    }

    public void setModelList(List<String> modelList) {
        this.modelList = modelList;
    }

    public List<PackageApkVo> getApkList() {
        return apkList;
    }

    public void setApkList(List<PackageApkVo> apkList) {
        this.apkList = apkList;
    }
}
