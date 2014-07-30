package com.ifhz.core.service.auther.jsonBean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 23:16
 */
public class TreeNode implements Serializable {
    private static final long serialVersionUID = 6639105047829034375L;
    @JSONField(name = "id")
    private long id;
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "pId")
    private long parentId;
    @JSONField(name = "checked")
    private boolean checked = false;
    @JSONField(name = "chkDisabled")
    private boolean chkDisabled;
    @JSONField(name = "open")
    private boolean open;
    @JSONField(name = "isHidden")
    private boolean isHidden;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }
}
