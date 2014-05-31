/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.service.auth;

import com.ifhz.core.po.Role;
import com.ifhz.core.po.User;
import com.ifhz.core.vo.UserVo;

import java.util.List;
import java.util.Map;


/**
 * 用户业务接口
 * 
 * @author radish
 */
public interface UserService {

	/**
	 * 用户新增接口
	 * 
	 * @author radish
	 * @param User
	 * @return
	 */
	public boolean insertUser(User User);

	/**
	 * 用户更新接口
	 * 
	 * @author radish
	 * @param User
	 * @return
	 */
	public boolean updateUser(User User);

	/**
	 * 管理员修改用户信息
	 * 
	 * @author wangshaofen
	 * @param User
	 * @return
	 */
	public boolean updateUserAdmin(User User);

	/**
	 * 用户删除接口
	 * 
	 * @author radish
	 * @param id
	 * @return
	 */
	public boolean deleteUser(int id);



	/**
	 * 获取所有角色
	 * 
	 * @author radishlee
	 * @return
	 */
	public List<Role> findAllRole();

	/**
	 * 更新用户密码
	 * 
	 * @author radishlee
	 * @param UserId
	 */
	public boolean updateUserPassword(long UserId, String newPassword);

	/**
	 * 根据角色查询用户
	 * 
	 * @author radishlee
	 * @param roleId
	 * @return
	 */
	public List<User>  findUserByRoleId(long roleId);


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

    Long getUserVoCount(String searchValue);
}
