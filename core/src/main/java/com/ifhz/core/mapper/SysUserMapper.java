package com.ifhz.core.mapper;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.auth.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 16:39
 */
public interface SysUserMapper {

    public int insert(SysUser po);

    public int update(SysUser po);

    public int updateStatus(SysUser po);

    public int updatePassword(SysUser po);

    public int delete(@Param("userId") Long userId);

    public SysUser getById(@Param("userId") Long userId);

    public SysUser getByLoginName(@Param("loginName") String loginName);

    public List<SysUser> queryListByRoleId(@Param("roleId") Long roleId);

    public List<SysUser> queryByVo(Pagination pagination, @Param(value = "record") SysUser record);
}
