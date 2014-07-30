package com.ifhz.core.service.auther.impl;


import com.ifhz.core.adapter.SysRoleAdapter;
import com.ifhz.core.adapter.SysUserAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.AdminRoleType;
import com.ifhz.core.po.auth.SysRole;
import com.ifhz.core.po.auth.SysUser;
import com.ifhz.core.service.auther.SysUserService;
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
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Resource(name = "sysUserAdapter")
    private SysUserAdapter sysUserAdapter;

    @Resource(name = "sysRoleAdapter")
    private SysRoleAdapter sysRoleAdapter;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(SysUser record) {
        return sysUserAdapter.insert(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(SysUser record) {
        return sysUserAdapter.update(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updatePassword(SysUser record) {
        return sysUserAdapter.updatePassword(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long userId) {
        return sysUserAdapter.delete(userId);
    }

    @Override
    public SysUser getById(Long userId) {
        return sysUserAdapter.getById(userId);
    }

    @Override
    public SysUser getByLoginName(String loginName) {
        return sysUserAdapter.getByLoginName(loginName);
    }

    @Override
    public List<SysUser> queryListByRoleId(Long roleId) {
        return sysUserAdapter.queryListByRoleId(roleId);
    }

    @Override
    public boolean checkAdminMng(long userId) {
        if (userId <= 0) {
            return false;
        }
        SysUser sysUser = sysUserAdapter.getById(userId);
        if (sysUser != null && sysUser.getRoleId() != null) {
            SysRole role = sysRoleAdapter.getById(sysUser.getRoleId());
            return AdminRoleType.checkAdminRole(role.getRootId());
        }

        return false;
    }

    @Override
    public List<SysUser> queryByVo(Pagination pagination, SysUser record) {
        return sysUserAdapter.queryByVo(pagination, record);
    }
}
