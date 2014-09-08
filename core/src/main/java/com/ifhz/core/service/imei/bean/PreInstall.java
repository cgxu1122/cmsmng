package com.ifhz.core.service.imei.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/8
 * Time: 20:09
 */
@XmlRootElement(name = "PreInstall")
@XmlAccessorType(XmlAccessType.FIELD)
public class PreInstall implements Serializable {
    private static final long serialVersionUID = 238780437833258674L;

    @XmlElement(name = "Model")
    private String ua;
    @XmlElement(name = "IMEI")
    private String imei;
    @XmlElement(name = "Time")
    private String time;
    @XmlElement(name = "copySuc")
    private String apkSuccName;
    @XmlElement(name = "copyFail")
    private String apkFailName;

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getApkSuccName() {
        return apkSuccName;
    }

    public void setApkSuccName(String apkSuccName) {
        this.apkSuccName = apkSuccName;
    }

    public String getApkFailName() {
        return apkFailName;
    }

    public void setApkFailName(String apkFailName) {
        this.apkFailName = apkFailName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreInstall)) return false;
        PreInstall that = (PreInstall) o;
        if (imei != null ? !imei.equals(that.imei) : that.imei != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return imei != null ? imei.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PreInstall{");
        sb.append("ua='").append(ua).append('\'');
        sb.append(", imei='").append(imei).append('\'');
        sb.append(", time='").append(time).append('\'');
        sb.append(", apkSuccName='").append(apkSuccName).append('\'');
        sb.append(", apkFailName='").append(apkFailName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
