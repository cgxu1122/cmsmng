package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.SysRoleResRefAdapter;
import com.ifhz.core.mapper.SysRoleResRefMapper;
import com.ifhz.core.po.auth.SysRoleResRef;
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
@Repository("sysRoleResRefAdapter")
public class SysRoleResRefAdapterImpl implements SysRoleResRefAdapter {

    @Resource(name = "sysRoleResRefMapper")
    private SysRoleResRefMapper sysRoleResRefMapper;

    @Override
    public int insert(SysRoleResRef record) {
        record.setCreateTime(new Date());
        return sysRoleResRefMapper.insert(record);
    }

    @Override
    public int delete(Long roleId) {
        return sysRoleResRefMapper.delete(roleId);
    }

    @Override
    public List<SysRoleResRef> queryListByRoleId(Long roleId) {
        List<SysRoleResRef> list = sysRoleResRefMapper.queryListByRoleId(roleId);
        return list == null ? Lists.<SysRoleResRef>newArrayList() : list;
    }
}
