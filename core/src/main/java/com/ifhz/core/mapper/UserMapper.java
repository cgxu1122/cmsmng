/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.mapper;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.Role;
import com.ifhz.core.po.User;
import com.ifhz.core.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户
 *
 * @author radish
 */
public interface UserMapper {

    /**
     * 新增用户信息
     *
     * @param User
     * @return
     * @author radish
     */
    public Integer insertUser(User User);


    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     * @author radish
     */
    public User findById(long id);

    /**
     * 管理员修改用户信息
     *
     * @param User
     * @return
     * @author wangshaofen
     */

    public Integer updateUserAdmin(User User);

    /**
     * 修改用户信息
     *
     * @param User
     * @return
     * @author radish
     */
    public Integer updateUser(User User);

    /**
     * @param userId
     * @param password
     * @return
     */
    public Integer updateUserPassword(@Param(value = "userId") long userId,
                                      @Param(value = "password") String password);

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     * @author radish
     */
    public Integer deleteUser(long id);

    /**
     * 查询所有用户信息
     *
     * @return
     * @author radish
     */
    public List<UserVo> queryAllUser(Pagination page, @Param(value = "userVo") UserVo userVo);

    /**
     * 获取后台用户管理条数
     *
     * @param userVo
     * @return
     */

    public Long queryUserVoCount(Pagination page, @Param(value = "userVo") UserVo userVo);


    /**
     * 根据用户名查找
     *
     * @param loginName
     * @return
     */
    User findUserByLoginName(String loginName);

    /**
     * 根据条件查询用户
     *
     * @param userVo
     * @return
     */
    List<User> findUserByCondition(Pagination page, @Param(value = "userVo") UserVo userVo);

    /**
     * 根据角色查询用户
     *
     * @param roleId
     * @return
     * @author radishlee
     */
    public List<User> findUserByRoleId(long roleId);


    public Long findUserByLoginNameAndNotId(Pagination page, @Param("loginName") String loginName,
                                            @Param("userId") long userId);


    List<UserVo> queryAllUser(Pagination pape, @Param("searchValue") String searchValue);

    Long queryUserVoCount(Pagination pape, @Param("searchValue") String searchValue);

    List<UserVo> queryAllUserByType(int type);

    List<UserVo> queryAllUserByType(@Param("type") int type, @Param("searchValue") String searchValue);

    List<Role> findAllRoleSon(long roleId);

}
