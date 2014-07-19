/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.service.auth;

import com.ifhz.core.po.Role;
import com.ifhz.core.po.User;
import com.ifhz.core.util.Result;
import com.ifhz.core.vo.UserVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * 用户业务接口
 *
 * @author radish
 */
public interface UserService {

    /**
     * 用户新增接口
     *
     * @param user
     * @return
     * @author radish
     */
    public Result insertUser(User user, long roleId);

    /**
     * 用户更新接口
     *
     * @param user
     * @return
     * @author radish
     */
    public boolean updateUser(User user);

    /**
     * 管理员修改用户信息
     *
     * @param user
     * @return
     * @author wangshaofen
     */
    public Result updateUserAdmin(User user, Long roleId);

    /**
     * 用户删除接口
     *
     * @param id
     * @return
     * @author radish
     */
    public boolean deleteUser(int id);


    /**
     * 获取所有角色
     *
     * @return
     * @author radishlee
     */
    public List<Role> findAllRole();

    /**
     * 更新用户密码
     *
     * @param userId
     * @author radishlee
     */
    public Result updateUserPassword(long userId, String newPassword);

    /**
     * 根据角色查询用户
     *
     * @param roleId
     * @return
     * @author radishlee
     */
    public List<User> findUserByRoleId(long roleId);


    /**
     * 更新User信息
     *
     * @param loginName
     * @param id
     * @return
     */
    public Long findUserByLoginNameAndNotId(String loginName, long id);

    public User findUserByLoginName(String name);

    User findById(Long id);

    List<UserVo> findAllUser(String searchValue);

    List<UserVo> findUsersByType(int type, String searchValue);

    Long getUserVoCount(String searchValue);

    List<Role> findAllRoleSon(long roleId) throws InvocationTargetException, IllegalAccessException;

    boolean checkAdminMng(long userId);
}
