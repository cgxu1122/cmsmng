package com.ifhz.api.vo;

import java.io.Serializable;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/2
 * Time: 21:17
 */
public class PackageApkVo implements Serializable {
    private static final long serialVersionUID = -3973223627746268188L;

    private Long apkId;
    private int autoRun;
    private int desktopIcon;
    private int counterApp;

    public Long getApkId() {
        return apkId;
    }

    public void setApkId(Long apkId) {
        this.apkId = apkId;
    }

    public int getAutoRun() {
        return autoRun;
    }

    public void setAutoRun(int autoRun) {
        this.autoRun = autoRun;
    }

    public int getDesktopIcon() {
        return desktopIcon;
    }

    public void setDesktopIcon(int desktopIcon) {
        this.desktopIcon = desktopIcon;
    }

    public int getCounterApp() {
        return counterApp;
    }

    public void setCounterApp(int counterApp) {
        this.counterApp = counterApp;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PackageApkVo{");
        sb.append("apkId=").append(apkId);
        sb.append(", autoRun=").append(autoRun);
        sb.append(", desktopIcon=").append(desktopIcon);
        sb.append(", counterApp=").append(counterApp);
        sb.append('}');
        return sb.toString();
    }
}
