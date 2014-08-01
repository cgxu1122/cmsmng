package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.SysUserAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.SysUserMapper;
import com.ifhz.core.po.auth.SysUser;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 18:54
 */
@Repository("sysUserAdapter")
public class SysUserAdapterImpl implements SysUserAdapter {

    @Resource(name = "sysUserMapper")
    private SysUserMapper sysUserMapper;

    @Override
    public int insert(SysUser po) {
        po.setCreateTime(new Date());
        po.setUpdateTime(new Date());
        return sysUserMapper.insert(po);
    }

    @Override
    public int update(SysUser po) {
        po.setUpdateTime(new Date());
        return sysUserMapper.update(po);
    }

    @Override
    public int updatePassword(SysUser po) {
        po.setUpdateTime(new Date());
        return sysUserMapper.updatePassword(po);
    }

    @Override
    public int delete(Long userId) {
        return sysUserMapper.delete(userId);
    }

    @Override
    public SysUser getById(Long userId) {
        return sysUserMapper.getById(userId);
    }

    @Override
    public SysUser getByLoginName(String loginName) {
        return sysUserMapper.getByLoginName(loginName);
    }

    @Override
    public List<SysUser> queryListByRoleId(Long roleId) {
        List<SysUser> list = sysUserMapper.queryListByRoleId(roleId);
        return list == null ? Lists.<SysUser>newArrayList() : list;
    }

    @Override
    public List<SysUser> queryByVo(Pagination pagination, SysUser record) {
        return sysUserMapper.queryByVo(pagination, record);
    }
}
