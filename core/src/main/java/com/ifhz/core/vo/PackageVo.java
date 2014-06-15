package com.ifhz.core.vo;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ifhz.core.constants.ApiEnums;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/2
 * Time: 21:13
 */
public class PackageVo implements Serializable {
    private static final long serialVersionUID = 6942901369675318485L;

    private Long packageId;
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
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


    public static void main(String[] args) throws Exception {
        init();
        getPkgVersion();
    }

    private static void init() {
        Random random = new Random();
        JSONObject root = new JSONObject();
        root.put("status", 0);
        root.put("pkgVersion", String.valueOf(new Date().getTime()));


        PackageVo commonVo = new PackageVo();
        commonVo.setPackageId(2L);
        commonVo.setBatchCode("1234");
        commonVo.setName("Common#1234");
        commonVo.setType(ApiEnums.UpdateType.Add.VALUE);
        commonVo.setApkList(getPackageApkVoList());
        root.put("commonPkg", commonVo);


        List<PackageVo> voList = Lists.newArrayList();
        PackageVo pkgVo1 = new PackageVo();
        pkgVo1.setPackageId(Math.abs(random.nextLong()));
        pkgVo1.setBatchCode("1234");
        pkgVo1.setName("H30_T2#1234");
        pkgVo1.setType(ApiEnums.UpdateType.Add.VALUE);
        pkgVo1.setModelList(Lists.newArrayList("H30_T2", "H50_T1"));
        pkgVo1.setApkList(getPackageApkVoList());

        PackageVo pkgVo2 = new PackageVo();
        pkgVo2.setPackageId(Math.abs(random.nextLong()));
        pkgVo2.setBatchCode("1224");
        pkgVo2.setName("H70_T2#1224");
        pkgVo2.setType(ApiEnums.UpdateType.Add.VALUE);
        pkgVo2.setModelList(Lists.newArrayList("H70_T2"));
        pkgVo2.setApkList(getPackageApkVoList());

        voList.add(pkgVo1);
        voList.add(pkgVo2);
        root.put("pkgList", voList);

        System.out.println(root.toJSONString());

    }

    private static void getPkgVersion() {
        Random random = new Random();
        JSONObject root = new JSONObject();
        root.put("status", 0);
        root.put("pkgVersion", String.valueOf(new Date().getTime()));
        PackageVo commonVo = new PackageVo();
        commonVo.setPackageId(2L);
        commonVo.setBatchCode("1234");
        commonVo.setName("Common#1234");
        commonVo.setType(ApiEnums.UpdateType.Add.VALUE);
        commonVo.setApkList(getPackageApkVoList());
        root.put("commonPkg", commonVo);


        List<PackageVo> voList = Lists.newArrayList();
        PackageVo pkgVo1 = new PackageVo();
        pkgVo1.setPackageId(Math.abs(random.nextLong()));
        pkgVo1.setBatchCode("1234");
        pkgVo1.setName("H30_T2#1234");
        pkgVo1.setType(ApiEnums.UpdateType.Add.VALUE);
        pkgVo1.setModelList(Lists.newArrayList("H30_T2", "H50_T1"));
        pkgVo1.setApkList(getPackageApkVoList());

        PackageVo pkgVo2 = new PackageVo();
        pkgVo2.setPackageId(Math.abs(random.nextLong()));
        pkgVo2.setBatchCode("1224");
        pkgVo2.setName("H70_T2#1224");
        pkgVo2.setType(ApiEnums.UpdateType.Modify.VALUE);
        pkgVo2.setModelList(Lists.newArrayList("H70_T2"));
        pkgVo2.setApkList(getPackageApkVoList());


        PackageVo pkgVo3 = new PackageVo();
        pkgVo2.setPackageId(Math.abs(random.nextLong()));
        pkgVo2.setType(ApiEnums.UpdateType.Delete.VALUE);

        voList.add(pkgVo1);
        voList.add(pkgVo2);
        voList.add(pkgVo3);
        root.put("pkgList", voList);

        System.out.println(root.toJSONString());

    }


    private static List<PackageApkVo> getPackageApkVoList() {
        Random random = new Random();
        List<PackageApkVo> list = Lists.newArrayList();
        PackageApkVo vo1 = new PackageApkVo();
        vo1.setApkId(Math.abs(random.nextLong()));
        vo1.setCounter(0);
        vo1.setIcon(random.nextInt(2));
        vo1.setRun(random.nextInt(2));
        vo1.setSort(1);

        PackageApkVo vo2 = new PackageApkVo();
        vo1.setApkId(Math.abs(random.nextLong()));
        vo1.setCounter(0);
        vo1.setIcon(random.nextInt(2));
        vo1.setRun(random.nextInt(2));
        vo1.setSort(2);


        PackageApkVo vo3 = new PackageApkVo();
        vo3.setApkId(Math.abs(random.nextLong()));
        vo3.setCounter(1);
        vo3.setIcon(1);
        vo3.setRun(0);
        vo3.setSort(3);

        PackageApkVo vo4 = new PackageApkVo();
        vo4.setApkId(Math.abs(random.nextLong()));
        vo4.setCounter(0);
        vo4.setIcon(random.nextInt(2));
        vo4.setRun(random.nextInt(2));
        vo4.setSort(4);

        PackageApkVo vo5 = new PackageApkVo();
        vo5.setApkId(10L);
        vo5.setCounter(0);
        vo5.setIcon(0);
        vo5.setRun(1);
        vo5.setSort(5);

        list.add(vo1);
        list.add(vo2);
        list.add(vo3);
        list.add(vo4);
        list.add(vo5);

        return list;
    }
}
