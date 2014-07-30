package com.ifhz.core.service.auther;

import com.ifhz.core.constants.AdminRoleType;
import com.ifhz.core.service.auther.jsonBean.TreeNode;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 16:28
 */
public interface SysAuthService {

    public boolean authSys(Long roleId, List<Long> recordList);

    public List<String> queryResUrlListByRoleId(Long roleId);

    public List<TreeNode> queryTreeNodeList(AdminRoleType type, Long loginRoleId, Long roleId);
}
