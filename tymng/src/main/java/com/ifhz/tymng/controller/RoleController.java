/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.po.Role;
import com.ifhz.core.service.auth.RoleService;
import com.ifhz.core.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 角色管理
 *
 * @author luyujian
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    private static Logger logger = LoggerFactory
            .getLogger(RoleController.class);
    @Autowired
    RoleService roleService;

    @RequestMapping("/index")
    public String index() {
        return "authoritymgmt/role_maint";
    }

    @RequestMapping("/blank")
    public String blank() {
        return "authoritymgmt/blank";
    }

    @RequestMapping("/top")
    public String top() {
        return "authoritymgmt/role_top";
    }

    @RequestMapping("/showdetail/{id}")
    public ModelAndView showdetail(@PathVariable("id") long id,
                                   HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("authoritymgmt/role_display_area");
        mav.addObject("parentId", id);
        return mav;
    }

    @RequestMapping("/getById/{id}")
    public void getById(@PathVariable("id") long id,
                        HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/xml; charset=UTF-8");
        RoleVo role = roleService.findById(id);

        String rtnJson = JSON.toJSONString(role);
        try {
            response.getWriter().print(rtnJson);
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

    @RequestMapping("/tree")
    public String tree() {
        return "authoritymgmt/role_tree";
    }

    @RequestMapping("/loadrole")
    public void loadrole(HttpServletRequest request,
                         HttpServletResponse response) {
        response.setContentType("text/xml; charset=UTF-8");
        String dhtmlXTreeXmlString = roleService.findAllRoleTreeXmlString();
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
    public void loadroleresourceref(HttpServletRequest request,
                                    HttpServletResponse response, @PathVariable("id") long id) {
        ModelAndView mav = new ModelAndView("authoritymgmt/role_display_area");
        mav.addObject("id", id);
    }

    /**
     * 保存角色
     *
     * @param req
     * @return
     * @author radish
     */
    @RequestMapping("/insert/{parentId}")
    @ResponseBody()
    public String insert(@PathVariable("parentId") long parentId,
                         HttpServletRequest req) {
        String roleName = StringUtils.trim(req.getParameter("roleName"));
        if (StringUtils.isBlank(roleName)) {
            return JSON.toJSONString("请输入角色名称");
        }
        if (!checkUnique(roleName)) {
            return JSON.toJSONString("角色名称重复");
        }

        Role parentRole = roleService.findParentById(parentId);

        Role role = new Role();
        role.setParentId(parentRole.getRoleId());
        role.setLevels(parentRole.getLevels() + 1);
        role.setRoleName(roleName);
        roleService.insert(role);

        Role dbRole = roleService.findByRoleName(roleName);
        dbRole.setFullPath(parentRole.getFullPath() + "/" + dbRole.getRoleId());
        roleService.saveRoleFullPath(dbRole);

        return JSON.toJSONString("添加成功");
    }

    /**
     * 名称唯一性验证
     *
     * @param name
     * @return true/false 有/没有
     * @author radish
     */
    private boolean checkUnique(String name) {
        boolean flag = false;
        Role role = roleService.findByRoleName(name);
        if (role == null) {
            flag = true;
        }
        return flag;
    }

    /**
     * @param roleName
     * @return
     * @author radishlee
     */
    private boolean checkUniqueBesideSelf(String roleName, long id) {
        boolean flag = false;
//		Role role = roleService.findByRoleNameBesideSelf(roleName, id);
//		if (role == null) {
//			flag = true;
//		}
        return flag;
    }

    /**
     * 更新角色
     *
     * @param req
     * @return
     * @author radish
     */
    @RequestMapping("/update")
    @ResponseBody()
    public String update(HttpServletRequest req) {
//		String icon = StringUtils.trim(req.getParameter("icon"));
//		String roleName = StringUtils.trim(req.getParameter("roleName"));
//		String id = StringUtils.trim(req.getParameter("id"));
//		long roleId = Long.parseLong(id);
//		if (StringUtils.isBlank(roleName)) {
//			return JsonUtils.singleErrorMsg("请输入角色名称");
//		}
//		if (!checkUniqueBesideSelf(roleName, roleId)) {
//			return JsonUtils.singleErrorMsg("角色名称重复");
//		}
//
//		RoleVo dbrole = roleService.findById(roleId);
//		if (StringUtils.equals(dbrole.getRoleName(), roleName)
//				&& StringUtils.equals(dbrole.getIcon(), icon)) {
//			return JsonUtils.RETURN_NOTHING;
//		} else {
//			Role role = new Role();
//			role.setId(dbrole.getId());
//			role.setIcon(icon);
//			role.setRoleName(roleName);
//			roleService.update(role);
//		}

        return "";
    }

    /**
     * 删除
     *
     * @param request
     * @return
     * @author radish
     */
    @RequestMapping("/delete")
    @ResponseBody()
    public String delete(HttpServletRequest request) {
//		String id = request.getParameter("id");
//		long roleId = Long.parseLong(id);
//
//		if (this.check2Delete(roleId)) {
//			return JsonUtils.singleErrorMsg("请先删除引用用户");
//		}
//		roleService.deleteAllRefByRoleId(roleId);
//		roleService.delete(roleId);
        return "";
    }

    /**
     * @param roleId
     * @return
     * @author radishlee
     */
    private boolean check2Delete(long roleId) {
        return false;
        //return roleService.check2Delete(roleId);
    }
}
