/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.po.Role;
import com.ifhz.core.po.User;
import com.ifhz.core.service.auth.UserService;
import com.ifhz.core.util.MD5keyUtil;
import com.ifhz.core.vo.UserVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * 用户管理
 *
 * @useror radish moon
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

    /**
     * 登陆管理主页
     *
     * @return
     * @useror radish
     */
    @RequestMapping("/index")
    public String indexUser() {
        System.out.println("/user/index");
        return "user/index";
    }

    /**
     * 修改个人信息主页
     *
     * @return
     * @useror radish
     */
    @RequestMapping("/updateindex")
    public String updateindex() {
        return "user/updateindex";
    }

    /**
     * 修改密码页面
     *
     * @useror radish
     * @return
     */
    /*@RequestMapping("/updatepassword")
	public String updatepasswor() {
		return "user/updatepassword";
	}*/

    /**
     * 保存用户类型
     *
     * @param req
     * @return
     * @useror radish
     */
    @RequestMapping("/insert")
    @ResponseBody()
    public String insert(HttpServletRequest req, User user) {
		boolean flag = this.checkUnique(user.getLoginName());
		if (!flag) {
			return "用户名称重复";
		}
        user.setCreateTime(new Date());

		/**
		 * 密码加密
		 */
		String password = StringUtils.trim(user.getPassword());
        user.setPassword(MD5keyUtil.getMD5Str(password));
		// user.setStatus(UserStatusEnum.ENABLE.getStatusValue());
		userService.insertUser(user);
        return "添加成功";
    }

    /**
     * 更新用户类型保存
     *
     * @param user
     * @return
     * @useror radish
     */
    @RequestMapping("/update")
    @ResponseBody()
    public String update(User user) {
		Long id = user.getUserId();
		User _user = userService.findById(id);

		if(user.equals(_user)){

        }else{
            Long otherId = userService.findUserByLoginNameAndNotId(user.getLoginName(), id);
            if (null != otherId) {
                return "该邮箱已经被其他用户占用！";
            }
            try {
                BeanUtils.copyProperties(_user,user);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            userService.updateUserAdmin(_user);
        }

		return "更新成功";
    }

    /**
     * 更新用户密码
     *
     * @param
     * @return
     * @useror wangshaofen
     */
    @RequestMapping("/updatePassowrd")
    @ResponseBody()
    public String updatePassword(HttpServletRequest req) {
		Long id=Long.parseLong(req.getParameter("id"));
		String password=req.getParameter("password");
		boolean flag=userService.updateUserPassword(id,MD5keyUtil.getMD5Str(password));
		if(flag == false){
			return "修改密码失败";
		}
        return  "修改密码成功";
    }



    /**
     * 删除
     *
     * @param request
     * @return
     * @useror radish
     */
    @RequestMapping("/delete")
    @ResponseBody()
    public String delete(HttpServletRequest request) {
		String id = request.getParameter("id");
		userService.deleteUser(Integer.parseInt(id));
        return "";
    }

    /**
     * 名称唯一性验证
     *
     * @param name
     * @return true/false 有/没有
     * @useror radish
     */
    private boolean checkUnique(String name) {
        boolean flag = false;
		User user = userService.findUserByLoginName(name);
		if (user == null) {
			flag = true;
		}
        return flag;
    }

    /**
     * 获取所有用户类型
     *
     * @param request
     * @return
     * @useror radish
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ResponseBody
    public String getAllUser(HttpServletRequest request) {
		String searchValue = request.getParameter("searchValue");
		// 查询记录
		List<UserVo> userDtoList = userService.findAllUser(searchValue);
		List<UserVo> userVoList = new ArrayList();
		UserVo userVo = null;
		User user = null;
		Role role = null;
		for (int i = 0; i < userDtoList.size(); i++) {
			userVo = new UserVo();
			user = userDtoList.get(i).getUser();
			role = userDtoList.get(i).getRole();
			userVo.setCellphone(user.getCellphone());
			userVo.setCreateTime(user.getCreateTime());
			userVo.setStatus(user.getStatus());
			userVo.setUserId(user.getUserId());
			userVoList.add(userVo);
		}
		// 记录数
		Long counts = userService.getUserVoCount(searchValue);

		Map dataGridJsonData = new HashMap();
		dataGridJsonData.put("total", counts);
		dataGridJsonData.put("rows", userVoList);
		return JSON.toJSONString(dataGridJsonData);

    }

    /**
     * 获取所有用户类型
     *
     * @param req
     * @return
     * @useror radish
     */
    @RequestMapping("/getByName/{name}")
    @ResponseBody
    public String getByName(HttpServletRequest req,
                            @PathVariable("name") String name) {
		if (StringUtils.isBlank(name)) {
			return this.getAllUser(req);
		} else {
			User user = userService.findUserByLoginName(name);
			return JSON.toJSONString(user);
		}
    }

    /**
     * 获取所有用户类型
     *
     * @param
     * @return
     * @useror radish
     */
    @RequestMapping("/getAllRole")
    @ResponseBody
    public JSONArray getAllRole() {
		List<Role> roleList = userService.findAllRole();
		return JSON.parseArray(JSON.toJSONString(roleList));
    }

}
