package com.ifhz.core.service.auther;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.auth.SysUser;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 16:26
 */
public interface SysUserService {

    public int insert(SysUser record);

    public int update(SysUser record);

    public int updateStatus(SysUser record);

    public int updatePassword(SysUser record);

    public int delete(Long userId);

    public SysUser getById(Long userId);

    public SysUser getByLoginName(String loginName);

    public List<SysUser> queryListByRoleId(Long roleId);

    public boolean checkAdminMng(long userId);

    public List<SysUser> queryByVo(Pagination pagination, SysUser record);

    public List<SysUser> queryMngListByVo(Pagination pagination, String searchValue);


}
