/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.service.auth.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.mapper.RoleResourceRefMapper;
import com.ifhz.core.po.RoleResourceRef;
import com.ifhz.core.service.auth.ResourceService;
import com.ifhz.core.service.auth.RoleResourceRefService;
import com.ifhz.core.service.auth.RoleService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 角色管理实现类
 *
 * @author radish
 */
@Service
public class RoleResourceRefServiceImpl implements RoleResourceRefService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleResourceRefServiceImpl.class);

    @Autowired
    RoleService roleService;

    @Autowired
    ResourceService resourceService;

    @Autowired
    RoleResourceRefMapper roleResourceRefMapper;

    /**
     * 获取角色资源关联
     *
     * @author radish
     */
    @Override
    public List<RoleResourceRef> roleIdAndResourceId(long roleId, long resourceId) {
        return roleResourceRefMapper.getRoleIdAndResourceId(null, roleId, resourceId);
    }

    /**
     * 授权
     *
     * @author radish
     */
    @Override
    public void authorization(String roleid, List<String> resIdList) {
        long roleId = Long.parseLong(roleid);
        List<RoleResourceRef> rrrOriginList = roleResourceRefMapper.findAllResourceForRoleByRoleId(roleId);

        List<RoleResourceRef> toMarkAccessList = new ArrayList<RoleResourceRef>();
        Map<String, Long> toAddMap = new HashMap<String, Long>();
        Map<String, Long> toMarkNotAccessMap = new HashMap<String, Long>();


        for (String resid : resIdList) {
            toAddMap.put(resid, Long.valueOf(resid));
        }

        for (RoleResourceRef rrr : rrrOriginList) {
            toMarkNotAccessMap.put(rrr.getResourceId().toString(), rrr.getResourceId());
        }


        if (rrrOriginList.size() == 0 && resIdList.size() > 0) {
            for (String resid : resIdList) {
                this.insert(roleId, Long.valueOf(resid));
            }
            return;
        }

        for (int i = 0; i < rrrOriginList.size(); i++) {
            long orgResId = rrrOriginList.get(i).getResourceId();
            for (String resid : resIdList) {
                long resId = Long.valueOf(resid);
                if (resId == orgResId) {
                    if (rrrOriginList.get(i).getAcces() == 0) {
                        toMarkAccessList.add(rrrOriginList.get(i));
                        continue;
                    }
                    toMarkNotAccessMap.remove(String.valueOf(resId));
                    toAddMap.remove(String.valueOf(resId));
                }
            }
        }

        for (int i = 0; i < toMarkAccessList.size(); i++) {
            roleResourceRefMapper.updateRoleResourceRefAccess(roleId, toMarkAccessList.get(i).getResourceId());
        }


        Set set = toMarkNotAccessMap.keySet();
        for (Iterator itr = set.iterator(); itr.hasNext(); ) {
            roleResourceRefMapper.updateRoleResourceRefNotAccess(roleId, toMarkNotAccessMap.get(itr.next()));
        }

        Set addSet = toAddMap.keySet();
        for (Iterator itr = addSet.iterator(); itr.hasNext(); ) {
            RoleResourceRef rrr = new RoleResourceRef();
            rrr.setAcces(1);
            rrr.setCreateTime(new Date());
            String resId = (String) itr.next();
            rrr.setResourceId(Long.valueOf(resId));
            rrr.setRoleId(roleId);
            roleResourceRefMapper.insert(rrr);
        }

        ShiroDbRealm.ShiroUser staff = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        LOGGER.debug("RoleResourceRefServiceImpl==>method[authorization]==>line[54] 用户[" + staff.loginName + "]给角色[" + roleid + "]授于资源[" + JSON.toJSON(resIdList) + "]成功！");
    }

    /**
     * 获取角色树
     *
     * @param roleId
     * @return
     * @author radish
     */
    @Override
    public String findRoleTreeXmlStringByRoleId(long roleId) {
        return roleService.findRoleTreeXmlStringByRoleId(roleId);
    }

    /**
     * 找到角色资源关系
     *
     * @param roleId
     * @return
     * @author radish
     */
    @Override
    public String findAllRoleResourceXmlString(long roleId, boolean adminFlag, boolean noResFlag) {
        return resourceService.findAllRoleResourceXmlString(roleId, adminFlag, noResFlag);
    }

    public Boolean deleteAllRefByRoleId(long roleid) {
        roleResourceRefMapper.deleteAllRefByRoleId(roleid);
        return true;
    }

    private Boolean insert(long roleId, long resId) {
        RoleResourceRef rrr = new RoleResourceRef();
        rrr.setResourceId(resId);
        rrr.setRoleId(roleId);
        rrr.setAcces(1);
        rrr.setCreateTime(new Date());
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

    @Override
    public List<RoleResourceRef> findAllResourceForRoleByRoleId(long roleId) {
        return roleResourceRefMapper.findAllResourceForRoleByRoleId(roleId);
    }

    @Override
    public RoleResourceRef findByRoleIdAndResourceId(long roleId, Long resourceId) {
        List<RoleResourceRef> rrr = this.roleIdAndResourceId(roleId, resourceId);
        if (rrr.size() == 0) {
            return null;
        }
        return rrr.get(0);
    }

    @Override
    public List<RoleResourceRef> findAllAvaiableRes4RoleByRoleId(Long parentId) {
        return roleResourceRefMapper.findAllAvaiableRes4RoleByRoleId(parentId);
    }
}
