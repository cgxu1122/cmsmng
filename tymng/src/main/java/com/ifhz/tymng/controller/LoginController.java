/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.tymng.controller;

import javax.servlet.http.HttpSession;

import com.ifhz.core.po.User;
import com.ifhz.core.service.auth.UserService;
import com.ifhz.core.util.MD5keyUtil;
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


/**
 * @author luyujian
 */
@Controller
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
    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
        model.addAttribute("error", "邮箱或密码错误");
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

    /**
     * 用户登陆（测试用）
     * 
     * @author luyujian
     * @param session
     * @param model
     * @param username
     * @param password
     * @return
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
