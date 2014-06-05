/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.service.auth.impl;

import java.util.List;

import com.ifhz.core.mapper.RoleResourceRefMapper;
import com.ifhz.core.po.RoleResourceRef;
import com.ifhz.core.service.auth.RoleResourceRefService;
import com.ifhz.core.service.auth.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 角色管理实现类
 * 
 * @author radish
 */
@Service
public class RoleResourceRefServiceImpl implements RoleResourceRefService {
    @Autowired
    RoleService roleService;
/*
    @Autowired
    ResourceService resourceService;*/

    @Autowired
    RoleResourceRefMapper roleResourceRefMapper;

    /**
     * 获取角色资源关联
     *
     * @author radish
     */
    @Override
    public List<RoleResourceRef> roleIdAndResourceId(long roleId, long resourceId) {
        return roleResourceRefMapper.getRoleIdAndResourceId(null,roleId, resourceId);
    }

    /**
     * 授权
     * 
     * @author radish
     */
    @Override
    public void authorization(String roleid, List<String> resIdList) {
        long roleId = Long.parseLong(roleid);
        if(roleId == 1l){
            throw new RuntimeException("系统管理员权限不可修改");
        }
        this.deleteAllRefByRoleId(roleId);

        for (String resId : resIdList) {
            this.insert(roleId, Long.parseLong(resId));
        }
    }

    /**
     * 获取角色树
     *
     * @return
     * @author radish
     * @param roleId
     */
    @Override
    public String findRoleTreeXmlStringByRoleId(long roleId) {
        return roleService.findRoleTreeXmlStringByRoleId(roleId );
    }

    /**
     * 找到角色资源关系
     *
     * @param id
     * @return
     * @author radish
     */
    @Override
    public String findAllRoleResourceXmlString(long id) {
        return null;
    }

    public Boolean deleteAllRefByRoleId(long roleid) {
        roleResourceRefMapper.deleteAllRefByRoleId(roleid);
        return true;
    }

    private Boolean insert(long roleId, long resId) {
        RoleResourceRef rrr = new RoleResourceRef();
        rrr.setResourceId(resId);
        rrr.setRoleId(roleId);
        roleResourceRefMapper.insert(rrr);
        return true;
    }

/**
     * 找到所有角色
     * 
     * @author radish
     *//*

    @Override
    public String findRoleTreeXmlStringByRoleId() {
        return roleService.findRoleTreeXmlStringByRoleId();
    }

    */
/**
     * 找到角色对应的所有资源
     * 
     * @author radish
     *//*

    @Override
    public String findAllRoleResourceXmlString(long id) {
        return resourceService.findAllRoleResourceXmlString(id);
    }
*/

    @Override
    public void deleteAllRefByResId(long resId) {
        roleResourceRefMapper.deleteAllRefByResId(resId);
    }
}
