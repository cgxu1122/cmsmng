package com.ifhz.core.adapter.impl;

import com.ifhz.core.adapter.SysResourceAdapter;
import com.ifhz.core.mapper.SysResourceMapper;
import com.ifhz.core.po.auth.SysResource;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 18:53
 */
@Repository("sysResourceAdapter")
public class SysResourceAdapterImpl implements SysResourceAdapter {

    @Resource(name = "sysResourceMapper")
    private SysResourceMapper sysResourceMapper;


    @Override
    public SysResource getById(Long resourceId) {
        return sysResourceMapper.getById(resourceId);
    }

    @Override
    public List<SysResource> queryListByRoleId(Long roleId) {
        return sysResourceMapper.queryListByRoleId(roleId);
    }

    @Override
    public List<SysResource> queryAllList() {
        return sysResourceMapper.queryAllList();
    }
}
