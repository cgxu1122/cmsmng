package com.ifhz.core.adapter;

import com.ifhz.core.po.auth.SysUserProductRef;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 11:29
 */
public interface SysUserProductRefAdapter {

    public int insert(SysUserProductRef record);

    public int delete(SysUserProductRef record);

    public List<SysUserProductRef> queryListByUserId(Long userId);
}
