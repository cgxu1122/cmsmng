package com.ifhz.core.vo;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ifhz.core.base.commons.codec.DesencryptUtils;
import com.ifhz.core.constants.ApiEnums;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/2
 * Time: 21:04
 */
public class ApkVo implements Serializable {
    private static final long serialVersionUID = -7672683477532719536L;
    private Long apkId;
    private String path;
    private String md5value;
    private String packagePath;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getApkId() {
        return apkId;
    }

    public void setApkId(Long apkId) {
        this.apkId = apkId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMd5value() {
        return md5value;
    }

    public void setMd5value(String md5value) {
        this.md5value = md5value;
    }


    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public static void main(String[] args) throws Exception {
        initApk();
        increatmentApk();

    }


    private static void initApk() {
        JSONObject root = new JSONObject();
        root.put("status", 0);
        root.put("apkVersion", String.valueOf(new Date().getTime()));


        List<ApkVo> voList = Lists.newArrayList();
        ApkVo apkVo1 = new ApkVo();
        apkVo1.setApkId(1L);
        apkVo1.setPath("http://nzyw.com/test/apk1.apk");
        apkVo1.setMd5value(DesencryptUtils.md5Str("apk1.apk"));
        apkVo1.setType(ApiEnums.UpdateType.Add.VALUE);


        ApkVo apkVo2 = new ApkVo();
        apkVo2.setApkId(2L);
        apkVo2.setPath("http://nzyw.com/test/apk2.apk");
        apkVo2.setMd5value(DesencryptUtils.md5Str("apk2.apk"));
        apkVo2.setType(ApiEnums.UpdateType.Add.VALUE);

        voList.add(apkVo1);
        voList.add(apkVo2);
        root.put("apkList", voList);

        System.out.println(root.toJSONString());
    }

    private static void increatmentApk() {
        JSONObject root = new JSONObject();
        root.put("status", 0);
        root.put("apkVersion", String.valueOf(new Date().getTime()));


        List<ApkVo> voList = Lists.newArrayList();
        ApkVo apkVo1 = new ApkVo();
        apkVo1.setApkId(1L);
        apkVo1.setPath("http://nzyw.com/test/apk1.apk");
        apkVo1.setMd5value(DesencryptUtils.md5Str("apk1.apk"));
        apkVo1.setType(ApiEnums.UpdateType.Add.VALUE);


        ApkVo apkVo2 = new ApkVo();
        apkVo2.setApkId(2L);
        apkVo2.setPath("http://nzyw.com/test/apk2.apk");
        apkVo2.setMd5value(DesencryptUtils.md5Str("apk2.apk"));
        apkVo2.setType(ApiEnums.UpdateType.Modify.VALUE);

        ApkVo apkVo3 = new ApkVo();
        apkVo3.setApkId(3L);
        apkVo3.setPath("http://nzyw.com/test/apk3.apk");
        apkVo3.setMd5value(DesencryptUtils.md5Str("apk3.apk"));
        apkVo3.setType(ApiEnums.UpdateType.Delete.VALUE);

        voList.add(apkVo1);
        voList.add(apkVo2);
        voList.add(apkVo3);
        root.put("apkList", voList);

        System.out.println(root.toJSONString());
    }
}
