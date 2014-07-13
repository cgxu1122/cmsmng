/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.anthrity.UserConstants;
import com.ifhz.core.po.Role;
import com.ifhz.core.po.User;
import com.ifhz.core.service.auth.UserService;
import com.ifhz.core.service.auth.impl.ShiroDbRealm;
import com.ifhz.core.util.MD5keyUtil;
import com.ifhz.core.util.Result;
import com.ifhz.core.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;


/**
 * 用户管理
 *
 * @useror radish moon
 */
@Controller
@RequestMapping("/tymng/user")
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
     * 保存用户类型
     *
     * @param req
     * @return
     * @useror radish
     */
    @RequestMapping("/insert")
    @ResponseBody()
    public String insert(HttpServletRequest req, User user) {
        Result result = new Result();
        result.setCode(1);
        result.setMessage("添加成功");

        String roleId = req.getParameter("roleId");
        if (StringUtils.isBlank(roleId)) {
            result.setCode(-1);
            result.setMessage("请选择角色");
            return JSON.toJSONString(result);
        }
        if (!(user.getStatus() == UserConstants.USER_STATUS_DISABLE || user.getStatus() == UserConstants.USER_STATUS_ENABLE)) {
            result.setCode(-1);
            result.setMessage("请选择用户状态");
            return JSON.toJSONString(result);
        }

        boolean flag = this.checkUnique(user.getLoginName());
        if (!flag) {
            result.setCode(-1);
            result.setMessage("用户名称重复");
            return JSON.toJSONString(result);
        }
        user.setCreateTime(new Date());
        String password = StringUtils.trim(user.getPassword());
        user.setPassword(password);

        //save user
        result = userService.insertUser(user, Long.valueOf(roleId));
        return JSON.toJSONString(result);
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
    public String update(User user, HttpServletRequest req) {
        Result result = new Result();
        result.setCode(1);
        result.setMessage("添加成功");

        String roleId = req.getParameter("roleId");
        if (StringUtils.isBlank(roleId)) {
            result.setCode(-1);
            result.setMessage("请选择角色");
            return JSON.toJSONString(result);
        }
        result = userService.updateUserAdmin(user, Long.valueOf(roleId));
        return JSON.toJSONString(result);
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
        Result result = new Result();
        result.setCode(1);
        result.setMessage("密码修改成功");

        String userId = req.getParameter("userIdUpPw");
        if (StringUtils.isBlank(userId)) {
            result.setCode(-1);
            result.setMessage("请选择用户");
            return JSON.toJSONString(result);
        }

        String password = req.getParameter("password");
        if (StringUtils.isBlank(password)) {
            result.setCode(-1);
            result.setMessage("请输入密码");
            return JSON.toJSONString(result);
        }

        String newPassword = MD5keyUtil.getMD5Str(password);
        result = userService.updateUserPassword(Long.parseLong(userId), newPassword);

        return JSON.toJSONString(result);
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
        Result result = new Result();
        result.setCode(1);
        result.setMessage("删除成功");
        String id = request.getParameter("id");
        userService.deleteUser(Integer.parseInt(id));
        return JSON.toJSONString(result);
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
    public JSONObject getAllUser(HttpServletRequest request) {
        String searchValue = request.getParameter("searchValue");
        if (StringUtils.isBlank(searchValue)) {
            searchValue = null;
        }

        // 查询记录
        List<UserVo> userDtoList = userService.findAllUser(searchValue);
        // 记录数
        Long counts = userService.getUserVoCount(searchValue);

        JSONObject result = new JSONObject();
        result.put("total", counts);
        result.put("rows", userDtoList);
        return result;

    }

    /**
     * 获取所有用户类型
     *
     * @param req
     * @return
     * @useror radish
     */
//    @RequestMapping("/getByName/{name}")
//    @ResponseBody
//    public String getByName(HttpServletRequest req,
//                            @PathVariable("name") String name) {
//		if (StringUtils.isBlank(name)) {
//			return this.getAllUser(req);
//		} else {
//			User user = userService.findUserByLoginName(name);
//			return JSON.toJSONString(user);
//		}
//    }

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
        Result result = new Result();
        result.setMessage("系统错误");
        result.setCode(-1);
        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        long roleId = user.roleId;
        List<Role> roleList = null;
        try {
            roleList = userService.findAllRoleSon(roleId);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return JSON.parseArray(JSON.toJSONString(result));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return JSON.parseArray(JSON.toJSONString(result));
        }
        return JSON.parseArray(JSON.toJSONString(roleList));
    }

}

