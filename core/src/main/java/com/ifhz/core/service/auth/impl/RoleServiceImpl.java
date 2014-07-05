/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.service.auth.impl;

import com.ifhz.core.base.commons.anthrity.AuthrityTreeConstants;
import com.ifhz.core.mapper.RoleMapper;
import com.ifhz.core.po.Role;
import com.ifhz.core.po.UserRoleRef;
import com.ifhz.core.service.auth.*;
import com.ifhz.core.vo.RoleVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * 角色管理实现类
 *
 * @author luyujian
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    protected StringBuffer sbXml;
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    ResourceService resourceService;

    @Autowired
    UserRoleRefService userRoleRefService;

    @Autowired
    RoleResourceRefService roleResourceRefService;

    @Autowired
    UserService userService;

    /**
     * @param roleName
     * @return
     */
    @Override
    public List<Role> queryRoleInfoByRoleName(String roleName) {
        List<Role> list = roleMapper.queryRoleByRoleName(roleName);
        return list;
    }


    /**
     * @author luyujian
     */
//    @Override
//    public List<Map> getResourceList() {
//        List<Map> ltResource = new ArrayList<Map>();
//        ltResource = roleMapper.getResourceList();
//        return ltResource;
//    }
    @Override
    public String findRoleTreeXmlStringByRoleId(long roleId) {
        return this.buildTree(AuthrityTreeConstants.NORMAL_ROLE_TREE, roleId);
    }

    @Override
    public List<Role> findAllRoleSon(long roleId) {
        return roleMapper.findAllRoleSon(roleId);
    }

    /**
     * 获取所有角色
     *
     * @author radish
     */
    @Override
    public List<Role> findAllRole() {
        return roleMapper.findAllRole();
    }

    /**
     * 获取所有角色xml
     *
     * @author radish
     */
    @Override
    public String findAllRoleTreeXmlString() {
        return buildTree(AuthrityTreeConstants.NORMAL_ROLE_TREE, 2l);
    }

    final String buildTree(int type, long roleId) {
        sbXml = new StringBuffer();
        sbXml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sbXml.append("<tree id=\"0\">\n");
        switch (type) {
            case 1: {
                Role root = roleMapper.findRootRole();
                if (root.getRoleId() == roleId) {
                    doBuildNormalRoleTree(root);
                } else {
                    RoleVo roleVo = roleMapper.findById(roleId);
                    Role role = new Role();
                    role.setRoleId(roleVo.getRoleId());
                    role.setRoleName(roleVo.getRoleName());
                    doBuildNormalRoleTree(role);
                }

            }
            case 2: {
            }
        }

        sbXml.append("</tree>");
        return sbXml.toString();
    }

    protected void doBuildNormalRoleTree(Role role) {
        long roleId = role.getRoleId();
        List<Role> childrenList = roleMapper.findAllChildrenById(roleId);

        sbXml.append("<item text=\"" + role.getRoleName() + "\" id=\"" + role.getRoleId() + "\"  open=\"1\"" + ">\n");

        for (Iterator iter = childrenList.iterator(); iter.hasNext(); ) {
            Role child = (Role) iter.next();
            doBuildNormalRoleTree(child);
        }
        sbXml.append("</item>\n");
    }

    /**
     * 找到所有当前角色下的资源
     *
     * @author radish
     */
    @Override
    public String findAllRoleResourceXmlString(long id) {
        return resourceService.findAllRoleResourceXmlString(id, false, false);
    }

    /**
     * 授权
     *
     * @author radish
     */
    @Override
    public void authorization(String roleId, List<String> resIdList) {
        roleResourceRefService.authorization(roleId, resIdList);
    }

    /**
     * 根据id查询
     *
     * @author radish
     */
    @Override
    public RoleVo findById(long id) {
        Role role = findParentById(id);
        if (role.getParentId() == -1) {
            RoleVo rv = new RoleVo();
            try {
                BeanUtils.copyProperties(rv, role);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return rv;
        } else {
            RoleVo roleVo = roleMapper.findById(id);
            return roleVo;
        }
    }

    /**
     * 新增角色
     *
     * @author radish
     */
    @Override
    public void insert(Role role) {
        roleMapper.insertRole(role);
    }

    /**
     * 根据名称查询
     *
     * @auther radish
     */
    @Override
    public Role findByRoleName(String roleName) {
        return roleMapper.findByRoleName(roleName);
    }

    /**
     * 保存全路径
     *
     * @auther radish
     */
    @Override
    public void saveRoleFullPath(Role dbRole) {
        roleMapper.updateFullPath(dbRole.getRoleId(), dbRole.getFullPath());
    }

    /**
     * 根据名称查询 except self
     *
     * @auther radish
     */
    @Override
    public Role findByRoleNameBesideSelf(String roleName, long id) {
        return roleMapper.findByRoleNameBesideSelf(null, roleName, id);
    }

    /**
     * 更新role
     *
     * @auther radish
     */
    @Override
    public void update(Role role) {
        roleMapper.updateRole(role);
    }

    /**
     * 删除
     *
     * @author radish
     */
    @Override
    public void delete(long id) {
        this.doDelete(id);
    }

    /**
     * 级联删除
     *
     * @param id
     * @author radishlee
     */
    private void doDelete(long id) {
        List<Role> roleList = roleMapper.findAllChildrenById(id);

        roleMapper.delete(id);
        for (Role role : roleList) {
            this.doDelete(role.getRoleId());
        }
    }

    /**
     * 删除所有角色关联资源
     *
     * @author radishlee
     */
    @Override
    public void deleteAllRefByRoleId(long roleId) {
        roleResourceRefService.deleteAllRefByRoleId(roleId);
    }

    /**
     * 检查用户对角色是否有引用
     */
    @Override
    public boolean check2Delete(long roleId) {
        List<UserRoleRef> userList = userRoleRefService.findUserListRoleId(roleId);
        return userList.size() > 0 ? true : false;
    }


    @Override
    public Role findParentById(long parentId) {
        return roleMapper.findParentById(null, parentId);
    }

    @Override
    public RoleVo getAdminRole() {
        Role role = roleMapper.getAdminRole();
        RoleVo roleMangerVo = new RoleVo();

        Long id_ = role.getRoleId();
        Date createTime = role.getCreateTime();
        Long parentId = role.getParentId();
        String roleName = role.getRoleName();
        Long level = role.getLevels();
        String fullPath = role.getFullPath();

        roleMangerVo.setRoleId(id_);
        roleMangerVo.setCreateTime(createTime);
        roleMangerVo.setFullPath(fullPath);
        roleMangerVo.setRoleName(roleName);
        roleMangerVo.setParentId(parentId);
        roleMangerVo.setLevels(level);

        return roleMangerVo;
    }


}
