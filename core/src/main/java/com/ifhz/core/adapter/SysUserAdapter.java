package com.ifhz.core.adapter;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.auth.SysUser;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 16:39
 */
public interface SysUserAdapter {

    public int insert(SysUser po);

    public int update(SysUser po);

    public int updatePassword(SysUser po);

    public int delete(Long userId);

    public SysUser getById(Long userId);

    public SysUser getByLoginName(String loginName);

    public List<SysUser> queryListByRoleId(Long roleId);

    public List<SysUser> queryByVo(Pagination pagination, SysUser record);
}
