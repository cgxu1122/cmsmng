package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.SysRoleAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.SysRoleMapper;
import com.ifhz.core.po.auth.SysRole;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 18:53
 */
@Repository("sysRoleAdapter")
public class SysRoleAdapterImpl implements SysRoleAdapter {

    @Resource(name = "sysRoleMapper")
    private SysRoleMapper sysRoleMapper;

    @Override
    public int insert(SysRole po) {
        po.setCreateTime(new Date());
        po.setUpdateTime(new Date());
        return sysRoleMapper.insert(po);
    }

    @Override
    public int update(SysRole po) {
        po.setUpdateTime(new Date());
        return sysRoleMapper.update(po);
    }

    @Override
    public int delete(Long roleId) {
        return sysRoleMapper.delete(roleId);
    }

    @Override
    public SysRole getById(Long roleId) {
        return sysRoleMapper.getById(roleId);
    }

    @Override
    public List<SysRole> queryByVo(Pagination pagination, SysRole record) {
        List<SysRole> list = sysRoleMapper.queryByVo(pagination, record);
        return list == null ? Lists.<SysRole>newArrayList() : list;
    }

    @Override
    public List<SysRole> queryChildListByRoleId(Long roleId) {
        return sysRoleMapper.queryChildListByRoleId(roleId);
    }

    @Override
    public List<SysRole> queryParentListByRoleId(Long roleId) {
        return sysRoleMapper.queryParentListByRoleId(roleId);
    }

    @Override
    public List<SysRole> queryAllRoleList() {
        return sysRoleMapper.queryAllRoleList();
    }
}
