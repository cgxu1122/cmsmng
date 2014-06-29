/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.hzfmng.controller;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.po.User;
import com.ifhz.core.service.auth.UserService;
import com.ifhz.core.service.auth.impl.ShiroDbRealm;
import com.ifhz.core.shiro.exception.CaptchaException;
import com.ifhz.core.shiro.exception.UserNamePasswordErrorException;
import com.ifhz.core.util.MD5keyUtil;
import com.ifhz.core.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @author luyujian
 */
@Controller
@RequestMapping("/hzfmng")
public class LoginController extends BaseController {

    @Autowired
    private UserService iStaffService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String initLogin() {
        return "login";
    }


    @RequestMapping(value = "/")
    public String loginSuccess() {
        return "index";
    }


    @RequestMapping(value = "/favicon.ico", method = RequestMethod.GET)
    public String favicon() {
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model, HttpServletRequest req) {
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        if (exceptionClassName.equals(CaptchaException.class.getName())) {
            model.addAttribute("error", "验证码错误");
        } else if (exceptionClassName.equals(UserNamePasswordErrorException.class.getName())) {
            model.addAttribute("error", "邮箱或密码错误");
        }
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public String logout() {
        Subject currentUser = SecurityUtils.getSubject();
        String result = "logout";
        currentUser.logout();
        return result;
    }


    @RequestMapping(value = "/mpd", method = RequestMethod.POST)
    @ResponseBody
    public String mpd(HttpServletRequest req) {
        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        User u = iStaffService.findById(user.userId.longValue());
        Result result = new Result();
        result.setCode(1);
        result.setMessage("密码修改成功");

        String oldPassword = req.getParameter("oldPassword");
        if (StringUtils.isBlank(oldPassword)) {
            result.setCode(-1);
            result.setMessage("请输入密码");
            return JSON.toJSONString(result);
        }

        String newPassword = req.getParameter("newPassword");
        if (StringUtils.isBlank(newPassword)) {
            result.setCode(-1);
            result.setMessage("请输入密码");
            return JSON.toJSONString(result);
        }

        String confirmPassword = req.getParameter("confirmPassword");
        if (StringUtils.isBlank(confirmPassword)) {
            result.setCode(-1);
            result.setMessage("请输入密码");
            return JSON.toJSONString(result);
        }
        if (!newPassword.trim().equals(confirmPassword.trim())) {
            result.setCode(-1);
            result.setMessage("两次输入密码不一致");
            return JSON.toJSONString(result);
        }


        oldPassword = MD5keyUtil.getMD5Str(oldPassword.trim());
        if (u.getPassword().equals(oldPassword)) {
            result = iStaffService.updateUserPassword(u.getUserId(), MD5keyUtil.getMD5Str(newPassword.trim()));
        }

        return JSON.toJSONString(result);
    }


    /**
     * 用户登陆（测试用）
     *
     * @param session
     * @param model
     * @param username
     * @param password
     * @return
     * @author luyujian
     */
    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    public String doLogin(HttpSession session, Model model, String username, String password) {

        User staff = iStaffService.findUserByLoginName(username);

        String retPage = "login";

        if (staff == null) {
            model.addAttribute("error", "用户不存在！");

        } else if (!staff.getPassword().equals(MD5keyUtil.getMD5Str(password))) {
            model.addAttribute("error", "密码错误");

        } else if (staff.getStatus() != 2) {
            /**
             * 用户状态 1.初始化 2.启用 3.禁用
             */
            model.addAttribute("error", "此用户没被启用");

        } else {
            session.setAttribute("staffInfo", staff);
            retPage = "index";
        }

        return retPage;

    }
}
