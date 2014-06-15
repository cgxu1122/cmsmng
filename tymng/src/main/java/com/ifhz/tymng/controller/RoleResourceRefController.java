/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.tymng.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ifhz.core.service.auth.RoleResourceRefService;
import com.ifhz.core.service.auth.impl.ShiroDbRealm;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



/**
 * 角色管理
 * 
 * @author luyujian
 */
@Controller
@RequestMapping("/rrr")
public class RoleResourceRefController {
    private static Logger logger = LoggerFactory.getLogger(RoleResourceRefController.class);
    @Autowired
    RoleResourceRefService roleResourceRefService;

    @RequestMapping("/index")
    public String index() {
        return "authoritymgmt/rrr_maint";
    }

    @RequestMapping("/blank")
    public String blank() {
        return "authoritymgmt/blank";
    }

    @RequestMapping("/top")
    public String top() {
        return "authoritymgmt/rrr_top";
    }

    @RequestMapping("/showdetail/{id}")
    public ModelAndView showdetail(@PathVariable("id") long id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("authoritymgmt/rrr_display_area");
        mav.addObject("id", id);
        return mav;
    }

    @RequestMapping("/tree")
    public String tree() {
        return "authoritymgmt/rrr_tree";
    }

    @RequestMapping("/loadrole")
    public void loadrole(HttpServletRequest request, HttpServletResponse response) {
        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        response.setContentType("text/xml; charset=UTF-8");
        String dhtmlXTreeXmlString = roleResourceRefService.findRoleTreeXmlStringByRoleId(user.roleId);
        try {
            response.getWriter().print(dhtmlXTreeXmlString);
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.getWriter().close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

    }

    @RequestMapping("/loadroleresourceref/{id}")
    public void loadroleresourceref(HttpServletRequest request, HttpServletResponse response,
            @PathVariable("id") long id) {
        response.setContentType("text/xml; charset=UTF-8");
        String dhtmlXTreeXmlString = roleResourceRefService.findAllRoleResourceXmlString(id);
        try {
            response.getWriter().print(dhtmlXTreeXmlString);
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.getWriter().close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @RequestMapping("/authorization/{str}")
    public void authorization(HttpServletRequest request, HttpServletResponse response, @PathVariable("str") String str) {
        response.setContentType("text/xml; charset=UTF-8");
        List<String> resIdList = null;

        String roleIdAndResIds[] = str.split("_");

        if (roleIdAndResIds.length < 2) {
            resIdList = new ArrayList<String>();
        } else {
            String resIds[] = roleIdAndResIds[1].split(",");
            resIdList = Arrays.asList(resIds);
        }
        String msg="授权成功";
        try {
            roleResourceRefService.authorization(roleIdAndResIds[0], resIdList);
            response.getWriter().print("授权成功");
            response.getWriter().close();
        }catch(RuntimeException re){
            logger.error(re.getMessage());
            msg=re.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            msg="授权失败";
        }catch(Exception e){
            e.printStackTrace();
            msg="授权失败";
        } finally {
            try {
                response.getWriter().print(msg);
                response.getWriter().close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
