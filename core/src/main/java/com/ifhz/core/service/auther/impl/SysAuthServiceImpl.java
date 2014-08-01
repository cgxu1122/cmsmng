package com.ifhz.core.service.auther.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.SysResourceAdapter;
import com.ifhz.core.adapter.SysRoleAdapter;
import com.ifhz.core.adapter.SysRoleResRefAdapter;
import com.ifhz.core.constants.AdminRoleType;
import com.ifhz.core.po.auth.SysResource;
import com.ifhz.core.po.auth.SysRole;
import com.ifhz.core.po.auth.SysRoleResRef;
import com.ifhz.core.service.auther.SysAuthService;
import com.ifhz.core.service.auther.jsonBean.TreeNode;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 16:29
 */
@Service("sysAuthService")
public class SysAuthServiceImpl implements SysAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysAuthServiceImpl.class);

    @Resource(name = "sysRoleAdapter")
    private SysRoleAdapter sysRoleAdapter;
    @Resource(name = "sysResourceAdapter")
    private SysResourceAdapter sysResourceAdapter;
    @Resource(name = "sysRoleResRefAdapter")
    private SysRoleResRefAdapter sysRoleResRefAdapter;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean authSys(Long roleId, List<Long> recordList) {
        if (CollectionUtils.isNotEmpty(recordList)) {
            sysRoleResRefAdapter.delete(roleId);
            for (Long resourceId : recordList) {
                SysRoleResRef ref = new SysRoleResRef();
                ref.setResourceId(resourceId);
                ref.setRoleId(roleId);
                ref.setCreateTime(new Date());
                sysRoleResRefAdapter.insert(ref);
            }
        }

        return true;
    }


    @Override
    public List<String> queryResUrlListByRoleId(Long roleId) {
        List<String> result = Lists.newArrayList();
        List<SysResource> resourceList = sysResourceAdapter.queryListByRoleId(roleId);
        if (CollectionUtils.isNotEmpty(resourceList)) {
            for (SysResource resource : resourceList) {
                if (resource != null) {
                    result.add(resource.getResUrl());
                }
            }
        }

        return result;
    }

    @Override
    public List<TreeNode> queryTreeNodeList(AdminRoleType type, Long loginRoleId, Long roleId) {
        List<TreeNode> result = Lists.newArrayList();
        List<SysResource> parentRoleHasResList = null;
        List<SysResource> loginHasResList = null;
        SysRole role = sysRoleAdapter.getById(roleId);
        if (type == AdminRoleType.SuperAdmin) {
            loginHasResList = sysResourceAdapter.queryAllList();
        } else if (type == AdminRoleType.Admin) {
            loginHasResList = sysResourceAdapter.queryListByRoleId(loginRoleId);
        } else {
            loginHasResList = sysResourceAdapter.queryListByRoleId(loginRoleId);
        }
        if (role.getParentId().longValue() == -1) {
            parentRoleHasResList = sysResourceAdapter.queryAllList();
        } else {
            parentRoleHasResList = sysResourceAdapter.queryListByRoleId(role.getParentId());
        }

        List<SysResource> roleHasResList = sysResourceAdapter.queryListByRoleId(roleId);

        if (CollectionUtils.isNotEmpty(parentRoleHasResList)) {
            for (SysResource resource : parentRoleHasResList) {
                TreeNode node = new TreeNode();
                node.setId(resource.getResourceId());
                node.setParentId(resource.getParentId());
                node.setName(resource.getResName());
                node.setOpen(true);
                if (roleHasResList.contains(resource)) {
                    node.setChecked(true);
                } else {
                    node.setChecked(false);
                }
                if (loginHasResList.contains(resource)) {
                    node.setChkDisabled(false);
                } else {
                    node.setChkDisabled(true);
                }

                result.add(node);
            }
        }

        return result;
    }
}
