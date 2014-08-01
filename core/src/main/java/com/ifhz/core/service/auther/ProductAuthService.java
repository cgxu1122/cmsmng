package com.ifhz.core.service.auther;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.Active;
import com.ifhz.core.po.ProductInfo;
import com.ifhz.core.po.auth.SysUser;
import com.ifhz.core.po.auth.SysUserProductRef;

import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 19:08
 */
public interface ProductAuthService {

    public boolean authProduct(Active active, SysUserProductRef record);

    public List<SysUserProductRef> queryListByUserId(Long userId);

    public List<SysUser> queryUserList(Pagination pagination, String searchValue);

    public Map<String, List<ProductInfo>> queryProductList(Long userId);
}
