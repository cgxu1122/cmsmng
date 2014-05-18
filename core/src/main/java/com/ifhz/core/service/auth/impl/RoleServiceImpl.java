/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.service.auth.impl;

import com.ifhz.core.constants.AuthrityTreeConstants;
import com.ifhz.core.mapper.RoleMapper;
import com.ifhz.core.po.Role;
import com.ifhz.core.service.auth.RoleService;
import com.ifhz.core.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 角色管理实现类
 *
 * @author luyujian
 */
@Service
public class RoleServiceImpl implements RoleService {
    protected StringBuffer sbXml;

    @Autowired
    RoleMapper roleMapper;

//    @Autowired
//    ResourceService resourceService;
//
//    @Autowired
//    RoleResourceRefService roleResourceRefService;
//
//    @Autowired
//    StaffService staffService;

    /**
     * @param roleName
     * @return
     */
    @Override
    public List<RoleVo> queryRoleInfoByRoleName(String roleName) {
        List<RoleVo> RoleVos = new ArrayList<RoleVo>();
        RoleVos = roleMapper.queryRoleInfoByRoleName(roleName);
        return RoleVos;
    }

//    /**
//     * @author luyujian
//     * @see com.caikee.mgmt.service.RoleService#getResourceList()
//     */
//    @Override
//    public List<Map> getResourceList() {
//        List<Map> ltResource = new ArrayList<Map>();
//        ltResource = roleMapper.getResourceList();
//        return ltResource;
//    }

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
        return buildTree(AuthrityTreeConstants.NORMAL_ROLE_TREE, 0l);
    }

    final String buildTree(int type, long id) {
        sbXml = new StringBuffer();
        sbXml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sbXml.append("<tree id=\"0\">\n");
        switch (type) {
            case 1: {
                Role role = roleMapper.findRootRole(AuthrityTreeConstants.ROOT_ROLE_PARENTID);
                doBuildNormalRoleTree(role);
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

//    /**
//     * 找到所有当前角色下的资源
//     *
//     * @author radish
//     */
//    @Override
//    public String findAllRoleResourceXmlString(long id) {
//        return resourceService.findAllRoleResourceXmlString(id);
//    }

//    /**
//     * 授权
//     *
//     * @author radish
//     */
//    @Override
//    public void authorization(String roleId, List<String> resIdList) {
//        roleResourceRefService.authorization(roleId, resIdList);
//    }

    /**
     * 根据id查询
     *
     * @author radish
     */
    @Override
    public RoleVo findById(long id) {
        Map map = roleMapper.findById(id);
        RoleVo roleMangerVo = new RoleVo();

        Long id_ = (Long) (map.get("id") == null ? 0 : map.get("id"));

        Date createTime = (Date) (map.get("createTime") == null ? "" : map.get("createTime"));
        String ext = map.get("ext") == null ? "" : (String) map.get("ext");
        Long parentId = (Long) (map.get("parentId") == null ? 0 : map.get("parentId"));
        String roleName = map.get("roleName") == null ? "" : (String) map.get("roleName");
        String parentName = map.get("parentName") == null ? "" : (String) map.get("parentName");
        String icon = map.get("icon") == null ? "" : (String) map.get("icon");
        Integer level = (Integer) (map.get("level") == null ? 0 : map.get("level"));

        String fullPath = map.get("fullPath") == null ? "" : (String) map.get("fullPath");
        roleMangerVo.setCreateTime(createTime.toString());
        roleMangerVo.setFullPath(fullPath);
        roleMangerVo.setParentName(parentName);
        roleMangerVo.setRoleName(roleName);
        roleMangerVo.setRoleId(id_);
        roleMangerVo.setParentId(parentId);
        roleMangerVo.setLevel(level);

        return roleMangerVo;
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
//    @Override
//    public Role findByRoleNameBesideSelf(String roleName, long id) {
//        return roleMapper.findByRoleNameBesideSelf(roleName, id);
//    }

    /**
     * 更新role
     *
     * @auther radish
     */
//    @Override
//    public void update(Role role) {
//        roleMapper.updateRole(role);
//    }

    /**
     * 删除
     *
     * @author radish
     */
//    @Override
//    public void delete(long id) {
//        this.doDelete(id);
//    }

//    /**
//     * 级联删除
//     *
//     * @author radishlee
//     * @param id
//     */
//    private void doDelete(long id) {
//        List<Role> roleList = roleMapper.findAllChildrenById(id);
//
//        roleMapper.delete(id);
//        for (Role role : roleList) {
//            this.doDelete(role.getId());
//        }
//    }

//    /**
//     * 删除所有角色关联资源
//     *
//     * @author radishlee
//     */
//    @Override
//    public void deleteAllRefByRoleId(long roleId) {
//        roleResourceRefService.deleteAllRefByRoleId(roleId);
//    }

//    /**
//     * 检查用户对角色是否有引用
//     */
//    @Override
//    public boolean check2Delete(long roleId) {
//        List<StaffDto> staffList = staffService.findStaffByRoleId(roleId);
//        return staffList.size() > 0 ? true : false;
//    }
    @Override
    public Role findParentById(long parentId) {
        return roleMapper.findParentById(parentId);
    }

    /*
     * (non-Javadoc)
     * 
     * @author radish
     * 
     * @see com.caikee.mgmt.service.RoleService#getAdminRole()
     */
//    @Override
//    public RoleMangerVo getAdminRole() {
//        Map map = roleMapper.getAdminRole();
//        RoleMangerVo roleMangerVo = new RoleMangerVo();
//
//        Long id_ = (Long) (map.get("id") == null ? 0 : map.get("id"));
//
//        Date createTime = (Date) (map.get("createTime") == null ? "" : map.get("createTime"));
//        String ext = map.get("ext") == null ? "" : (String) map.get("ext");
//        Long parentId = (Long) (map.get("parentId") == null ? 0 : map.get("parentId"));
//        String roleName = map.get("roleName") == null ? "" : (String) map.get("roleName");
//        String parentName = map.get("parentName") == null ? "" : (String) map.get("parentName");
//        String icon = map.get("icon") == null ? "" : (String) map.get("icon");
//        Integer level = (Integer) (map.get("level") == null ? 0 : map.get("level"));
//
//        String fullPath = map.get("fullPath") == null ? "" : (String) map.get("fullPath");
//        roleMangerVo.setCreateTime(createTime.toString());
//        roleMangerVo.setExt(ext);
//        roleMangerVo.setFullPath(fullPath);
//        roleMangerVo.setIcon(icon);
//        roleMangerVo.setParentName(parentName);
//        roleMangerVo.setRoleName(roleName);
//        roleMangerVo.setId(id_);
//        roleMangerVo.setParentId(parentId);
//        roleMangerVo.setLevels(level);
//
//        return roleMangerVo;
//    }
}
