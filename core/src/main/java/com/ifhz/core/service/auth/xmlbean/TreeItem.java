package com.ifhz.core.service.auth.xmlbean;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 23:04
 */
public class TreeItem {
    private String id;
    private String text;
    private String open;
    private String checked;
    private List<TreeItem> item;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public List<TreeItem> getItem() {
        return item;
    }

    public void setItem(List<TreeItem> item) {
        this.item = item;
    }
}
