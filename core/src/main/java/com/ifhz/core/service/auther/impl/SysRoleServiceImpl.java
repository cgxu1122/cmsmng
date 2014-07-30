package com.ifhz.core.service.auther.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.SysRoleAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.AdminRoleType;
import com.ifhz.core.po.auth.SysRole;
import com.ifhz.core.service.auther.SysRoleService;
import com.ifhz.core.service.auther.jsonBean.TreeNode;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 16:27
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Resource(name = "sysRoleAdapter")
    private SysRoleAdapter sysRoleAdapter;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(SysRole po) {
        return sysRoleAdapter.insert(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(SysRole po) {
        return sysRoleAdapter.update(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long roleId) {
        List<SysRole> list = queryChildListByRoleId(roleId);
        if (CollectionUtils.isNotEmpty(list)) {
            for (SysRole sysRole : list) {
                sysRoleAdapter.delete(sysRole.getRoleId());
            }
        }

        return list.size();
    }

    @Override
    public SysRole getById(Long roleId) {
        return sysRoleAdapter.getById(roleId);
    }

    @Override
    public List<SysRole> queryByVo(Pagination pagination, SysRole record) {
        return sysRoleAdapter.queryByVo(pagination, record);
    }

    @Override
    public List<SysRole> queryChildListByRoleId(Long roleId) {
        return sysRoleAdapter.queryChildListByRoleId(roleId);
    }

    @Override
    public List<SysRole> queryParentListByRoleId(Long roleId) {
        return sysRoleAdapter.queryParentListByRoleId(roleId);
    }

    @Override
    public List<TreeNode> queryTreeNodeList(AdminRoleType type, Long roleId) {
        List<TreeNode> result = Lists.newArrayList();
        List<SysRole> temp = null;
        if (type == AdminRoleType.SuperAdmin) {
            temp = sysRoleAdapter.queryAllRoleList();
        } else if (type == AdminRoleType.Admin) {
            temp = sysRoleAdapter.queryAllRoleList();
            List<SysRole> parentRoleList = sysRoleAdapter.queryParentListByRoleId(roleId);
            for (int i = 0; i < parentRoleList.size(); i++) {
                if (parentRoleList.get(i).getRoleId().longValue() == roleId.longValue()) {
                    parentRoleList.remove(i);
                }
            }
            temp = (List<SysRole>) CollectionUtils.subtract(temp, parentRoleList);
        } else {
            temp = sysRoleAdapter.queryChildListByRoleId(roleId);
        }
        if (CollectionUtils.isNotEmpty(temp)) {
            for (SysRole role : temp) {
                TreeNode node = new TreeNode();
                node.setId(role.getRoleId());
                node.setParentId(role.getParentId());
                node.setName(role.getRoleName());
                node.setOpen(true);
                node.setChecked(false);

                result.add(node);
            }
        }

        return result;
    }


}
