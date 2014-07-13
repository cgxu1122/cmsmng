/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.service.auth.impl;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.UserMapper;
import com.ifhz.core.mapper.UserRoleRefMapper;
import com.ifhz.core.po.Role;
import com.ifhz.core.po.User;
import com.ifhz.core.po.UserRoleRef;
import com.ifhz.core.service.auth.RoleService;
import com.ifhz.core.service.auth.UserService;
import com.ifhz.core.util.Result;
import com.ifhz.core.vo.RoleVo;
import com.ifhz.core.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;


/**
 * 用户业务层接口
 *
 * @author radish
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name = "userMapper")
    private UserMapper userMapper;
    @Resource(name = "roleService")
    private RoleService roleService;
    @Resource(name = "userRoleRefMapper")
    private UserRoleRefMapper userRoleRefMapper;

    /**
     * 用户新增接口
     *
     * @param user
     * @return
     * @author radish
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insertUser(User user, long roleId) {
        Result result = new Result();
        result.setCode(1);
        result.setMessage("添加成功");

        try {
            //save user
            Integer num = userMapper.insertUser(user);
            if (num != 1) {
                throw new Exception("用户保存失败");
            }

            User dbuser = userMapper.findUserByLoginName(user.getLoginName());

            UserRoleRef urr = new UserRoleRef();
            urr.setRoleId(roleId);
            urr.setUserId(dbuser.getUserId());
            urr.setCreateTime(new Date());
            //save ref
            num = userRoleRefMapper.insert(urr);
            if (num != 1) {
                throw new Exception("用户角色关联保存失败");
            }
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage(e.getMessage());
            return result;
        }
        return result;
    }

    /**
     * 用户更新接口
     *
     * @param User
     * @return
     * @author radish
     */
    @Override
    public boolean updateUser(User User) {
        return convertRtn(userMapper.updateUser(User));
    }

    /**
     * 管理员修改用户信息
     *
     * @param user
     * @return
     * @author wangshaofen
     */
    @Override
    public Result updateUserAdmin(User user, Long roleId) {
        Result result = new Result();
        result.setCode(1);
        result.setMessage("更新成功");


        long userId = user.getUserId();
        User dbUser = userMapper.findById(userId);
        UserRoleRef dbUserRoleRef = userRoleRefMapper.findRoleByUserId(userId);
        try {
            if (user.equalss(dbUser)) {

            } else {
                dbUser.setAddress(user.getAddress());
                dbUser.setCellphone(user.getCellphone());
                dbUser.setType(user.getType());
                dbUser.setStatus(user.getStatus());
                userMapper.updateUserAdmin(dbUser);
            }

            if (!(roleId == dbUserRoleRef.getRoleId())) {
                dbUserRoleRef.setRoleId(roleId);
                userRoleRefMapper.deleteAllRefByUserId(userId);
                userRoleRefMapper.insert(dbUserRoleRef);
            }
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage(e.getMessage());
            return result;
        }
        return result;
    }

    /**
     * 更新密码
     *
     * @param userId
     * @return
     * @author radish
     */
    @Override
    public Result updateUserPassword(long userId, String password) {
        Result result = new Result();
        result.setCode(1);
        result.setMessage("更新成功");

        try {
            userMapper.updateUserPassword(userId, password);
        } catch (Exception e) {
            result.setCode(-1);
            result.setMessage(e.getMessage());
            return result;
        }
        return result;
    }

    /**
     * 用户删除接口
     *
     * @param id
     * @return
     * @author radish
     */
    @Override
    public boolean deleteUser(int id) {
        userMapper.deleteUser(id);
        return true;
    }


    /**
     * 转换返回值接口
     *
     * @param parm
     * @return
     * @author radish
     */
    private boolean convertRtn(int parm) {
        return parm > 0 ? true : false;
    }


    /*
     * 获取所有角色
     *
     * @author radish
     * @return
     */
    @Override
    public List<Role> findAllRole() {
        return roleService.findAllRole();
    }

    /**
     * 根据角色查询用户
     *
     * @param roleId
     * @return
     * @author radishlee
     */
    @Override
    public List<User> findUserByRoleId(long roleId) {
        return userMapper.findUserByRoleId(roleId);
    }


    /**
     * 更新User信息
     *
     * @param loginName
     * @param id
     * @return
     */
    @Override
    public Long findUserByLoginNameAndNotId(String loginName, long id) {
        return userMapper.findUserByLoginNameAndNotId(null, loginName, id);
    }

    @Override
    public User findUserByLoginName(String name) {
        return userMapper.findUserByLoginName(name);
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public List<UserVo> findAllUser(String searchValue) {
        return userMapper.queryAllUser(new Pagination(), searchValue);
    }

    @Override
    public Long getUserVoCount(String searchValue) {
        return userMapper.queryUserVoCount(new Pagination(), searchValue);
    }

    @Override
    public List<Role> findAllRoleSon(long roleId) throws InvocationTargetException, IllegalAccessException {
        RoleVo role;
        if (roleId == 1) {
            role = roleService.findRootRole();
        }
        role = roleService.findById(roleId);
        if (role.getParentId() == -1) {
            return roleService.findAllRole();
        } else {
            return roleService.findAllRoleSon(roleId);
        }

    }


    public List<UserVo> findUsersByType(int type, String searchValue) {
        return userMapper.queryAllUserByType(type, searchValue);
    }
}
