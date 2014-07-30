package com.ifhz.core.service.auther;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.AdminRoleType;
import com.ifhz.core.po.auth.SysRole;
import com.ifhz.core.service.auther.jsonBean.TreeNode;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 16:27
 */
public interface SysRoleService {

    public int insert(SysRole po);

    public int update(SysRole po);

    public int delete(Long roleId);

    public SysRole getById(Long roleId);

    public List<SysRole> queryByVo(Pagination pagination, SysRole record);

    public List<SysRole> queryChildListByRoleId(Long roleId);

    public List<SysRole> queryParentListByRoleId(Long roleId);

    public List<TreeNode> queryTreeNodeList(AdminRoleType type, Long roleId);
}
