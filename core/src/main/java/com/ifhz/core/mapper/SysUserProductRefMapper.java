package com.ifhz.core.mapper;

import com.ifhz.core.po.auth.SysUserProductRef;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 11:29
 */
public interface SysUserProductRefMapper {

    public int insert(SysUserProductRef record);

    public int delete(SysUserProductRef record);

    public List<Long> queryProductIdListByUserId(@Param("userId") Long userId);
}
