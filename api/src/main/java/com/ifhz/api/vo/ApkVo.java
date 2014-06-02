package com.ifhz.api.vo;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.commons.codec.DesencryptUtils;
import com.ifhz.core.service.pkgmng.constants.ApiEnums;

import java.io.Serializable;

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

    public static void main(String[] args) throws Exception {
        JSONObject root = new JSONObject();
        root.put("", "");
        root.put("", "");
        root.put("", "");
        root.put("", "");
        root.put("", "");

        ApkVo apkVo1 = new ApkVo();
        apkVo1.setApkId(1L);
        apkVo1.setPath("http://nzyw.com/test/apk1.apk");
        apkVo1.setMd5value(DesencryptUtils.md5Str("chenggang.xu"));
        apkVo1.setType(ApiEnums.UpdateType.Add.VALUE);


        ApkVo apkVo2 = new ApkVo();
    }
}
