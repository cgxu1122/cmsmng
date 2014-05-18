package com.ifhz.core.adapter;

import com.ifhz.core.po.DictInfo;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 12:30
 */
public interface DictInfoAdapter {

    public void insert(DictInfo po);

    public DictInfo getByKeyCode(String keyCode);
}
