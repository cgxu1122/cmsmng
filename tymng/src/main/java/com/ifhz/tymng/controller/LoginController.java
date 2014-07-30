/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.constants.Active;
import com.ifhz.core.po.auth.SysUser;
import com.ifhz.core.service.auther.SysUserService;
import com.ifhz.core.service.auther.impl.ShiroDbRealm;
import com.ifhz.core.shiro.exception.CaptchaException;
import com.ifhz.core.shiro.exception.UserNamePasswordErrorException;
import com.ifhz.core.util.MD5keyUtil;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping("/tymng")
public class LoginController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

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
            model.addAttribute("error", "用户名密码错误");
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


    @RequestMapping(value = "/mpd", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject mpd(HttpServletRequest req) {
        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        SysUser u = sysUserService.getById(user.userId.longValue());
        JSONObject result = new JSONObject();
        result.put("ret", 1);
        result.put("message", "密码修改成功");

        String oldPassword = req.getParameter("oldPassword");
        if (StringUtils.isBlank(oldPassword)) {
            result.put("ret", -1);
            result.put("message", "请输入密码");
            return result;
        }

        String newPassword = req.getParameter("newPassword");
        if (StringUtils.isBlank(newPassword)) {
            result.put("ret", -1);
            result.put("message", "请输入密码");
            return result;
        }

        String confirmPassword = req.getParameter("confirmPassword");
        if (StringUtils.isBlank(confirmPassword)) {
            result.put("ret", -1);
            result.put("message", "请输入密码");
            return result;
        }
        if (!newPassword.trim().equals(confirmPassword.trim())) {
            result.put("ret", -1);
            result.put("message", "两次输入密码不一致");
            return result;
        }


        oldPassword = oldPassword.trim();
        if (u.getPassword().equals(oldPassword)) {
            SysUser sysUser = new SysUser();
            sysUser.setUserId(u.getUserId());
            sysUser.setPassword(newPassword.trim());
            sysUserService.updatePassword(sysUser);
        } else {
            result.put("ret", -1);
            result.put("message", "原密码错误");
            return result;
        }

        return result;
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

        String retPage = "login";
        SysUser staff = sysUserService.getByLoginName(username);
        if (staff == null) {
            model.addAttribute("error", "用户不存在！");
        } else if (!staff.getPassword().equals(MD5keyUtil.getMD5Str(password))) {
            model.addAttribute("error", "密码错误");
        } else if (!StringUtils.equalsIgnoreCase(staff.getActive(), Active.Y.dbValue)) {
            /**
             * 用户状态  Y.启用 N.禁用
             */
            model.addAttribute("error", "此用户没被启用");
        } else {
            session.setAttribute("staffInfo", staff);
            retPage = "index";
        }

        return retPage;

    }
}
