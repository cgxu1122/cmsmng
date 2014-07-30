package com.ifhz.core.po.auth;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 17:18
 */
public class SysResource implements Serializable {

    private static final long serialVersionUID = 2323646283939923081L;
    private Long resourceId;
    private Long parentId;
    private String resName;
    private String resUrl;
    private String fullPath;
    private Long levels;
    private Date createTime;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Long getLevels() {
        return levels;
    }

    public void setLevels(Long levels) {
        this.levels = levels;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysResource)) return false;

        SysResource resource = (SysResource) o;
        if (!resourceId.equals(resource.resourceId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return resourceId.hashCode();
    }
}
