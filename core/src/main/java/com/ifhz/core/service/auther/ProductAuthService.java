package com.ifhz.core.service.auther;

import com.ifhz.core.po.auth.SysUserProductRef;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 19:08
 */
public interface ProductAuthService {

    public boolean authProduct(Long userId, List<SysUserProductRef> recordList);

    public List<SysUserProductRef> queryListByUserId(Long userId);
}
