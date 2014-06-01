/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.mapper;

import java.util.List;
import java.util.Map;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.User;
import com.ifhz.core.vo.UserVo;
import org.apache.ibatis.annotations.Param;


/**
 * 用户
 * 
 * @author radish
 */
public interface UserMapper {

	/**
	 * 新增用户信息
	 * 
	 * @author radish
	 * @param User
	 * @return
	 */
	public Integer insertUser(User User);


    /**
     * 根据id查询用户信息
     *
     * @author radish
     * @param id
     * @return
     */
    public User findById(long id);

    /**
     * 管理员修改用户信息
     *
     * @author wangshaofen
     * @param User
     * @return
     */

    public Integer updateUserAdmin(User User);

	/**
	 * 修改用户信息
	 *
	 * @author radish
	 * @param User
	 * @return
	 */
	public Integer updateUser(User User);

    /**
     *
     * @param userId
     * @param password
     * @return
     */
    public Integer updateUserPassword(@Param(value = "userId") long userId,
                                  @Param(value = "password") String password);

	/**
	 * 删除用户信息
	 *
	 * @author radish
	 * @param id
	 * @return
	 */
	public Integer deleteUser(long id);

	/**
	 * 查询所有用户信息
	 *
	 * @author radish
	 * @return
	 */
	public List<UserVo> queryAllUser(Pagination page,@Param(value = "userVo") UserVo userVo);

    /**
     * 获取后台用户管理条数
     *
     * @param userVo
     * @return
     */

    public Long queryUserVoCount(Pagination page,@Param(value = "userVo")UserVo userVo);


    /**
     * 根据用户名查找
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
    List<User> findUserByCondition(Pagination page,@Param(value = "userVo")UserVo userVo);

    /**
     * 根据角色查询用户
     *
     * @author radishlee
     * @param roleId
     * @return
     */
    public List<User> findUserByRoleId(long roleId);


    public Long findUserByLoginNameAndNotId(Pagination page,@Param("loginName") String loginName,
                                               @Param("userId") long userId);


    List<UserVo> queryAllUser(Pagination pape ,@Param("searchValue") String searchValue);

    Long queryUserVoCount(Pagination pape ,@Param("searchValue") String searchValue);
}
