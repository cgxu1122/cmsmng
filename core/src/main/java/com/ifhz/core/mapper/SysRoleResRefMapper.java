package com.ifhz.core.mapper;

import com.ifhz.core.po.auth.SysRoleResRef;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 16:40
 */
public interface SysRoleResRefMapper {

    public int insert(SysRoleResRef record);

    public int delete(@Param("roleId") Long roleId);

    public List<SysRoleResRef> queryListByRoleId(@Param("roleId") Long roleId);
}
