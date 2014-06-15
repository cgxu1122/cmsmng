package com.ifhz.core.vo;

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
    private int run;
    private int icon;
    private int counter;
    private int sort;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Long getApkId() {
        return apkId;
    }

    public void setApkId(Long apkId) {
        this.apkId = apkId;
    }

    public int getRun() {
        return run;
    }

    public void setRun(int run) {
        this.run = run;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PackageApkVo{");
        sb.append("apkId=").append(apkId);
        sb.append(", run=").append(run);
        sb.append(", icon=").append(icon);
        sb.append(", counter=").append(counter);
        sb.append('}');
        return sb.toString();
    }
}
