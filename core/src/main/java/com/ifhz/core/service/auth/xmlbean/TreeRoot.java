package com.ifhz.core.service.auth.xmlbean;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 22:45
 */
public class TreeRoot {
    private String id;
    private List<TreeItem> item;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TreeItem> getItem() {
        return item;
    }

    public void setItem(List<TreeItem> item) {
        this.item = item;
    }
}
