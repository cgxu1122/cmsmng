package com.ifhz.core.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/20
 * Time: 19:33
 */
public class CounterTempLog implements Serializable {
    private static final long serialVersionUID = -838555385099858138L;

    private Long id;
    private String imei;
    private String ua;
    private Integer active;
    private Date createTime;


    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CounterTempLog{");
        sb.append("id=").append(id);
        sb.append(", imei='").append(imei).append('\'');
        sb.append(", ua='").append(ua).append('\'');
        sb.append(", active='").append(active).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}
