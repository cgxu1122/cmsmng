/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.AdminRoleType;
import com.ifhz.core.constants.CanDel;
import com.ifhz.core.po.auth.SysRole;
import com.ifhz.core.service.auther.SysRoleService;
import com.ifhz.core.service.auther.jsonBean.TreeNode;
import com.ifhz.core.shiro.utils.CurrentUserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @author luyujian
 */
@Controller
@RequestMapping("/tymng/auth/role")
public class SysRoleController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/index")
    public String index() {
        return "auth/role/role_maint";
    }


    @RequestMapping("/blank")
    public String blank() {
        return "auth/role/blank";
    }


    @RequestMapping("/roleTree")
    public String tree() {
        return "auth/role/roleTree";
    }


    @RequestMapping("/roleIndex")
    public ModelAndView showdetail(@RequestParam("parentId") long parentId, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("auth/role/role_display_area");
        mav.addObject("parentId", parentId);
        return mav;
    }


    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject list(@RequestParam(value = "page", required = false) Integer pageNum,
                    @RequestParam(value = "rows", required = false) Integer pageSize,
                    @RequestParam(value = "parentId", required = true) Long parentId) {

        /**分页*/
        Pagination pagination = new Pagination();
        if (pageNum != null) {
            pagination.setCurrentPage(pageNum);
        }
        if (pageSize != null) {
            pagination.setPageSize(pageSize);
        }

        SysRole parentRole = sysRoleService.getById(parentId);


        SysRole sysRole = new SysRole();
        sysRole.setParentId(parentId);
        List<SysRole> list = sysRoleService.queryByVo(pagination, sysRole);
        if (CollectionUtils.isEmpty(list)) {
            list = Lists.newArrayList();
        } else {
            for (SysRole role : list) {
                role.setParentRoleName(parentRole.getRoleName());
            }
        }

        JSONObject result = new JSONObject();
        result.put("total", pagination.getTotalCount());
        result.put("rows", list);

        return result;
    }


    @RequestMapping(value = "/initRoleTree", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONArray initRoleTree() {
        AdminRoleType type = CurrentUserUtil.getAdminRoleType();
        long roleId = CurrentUserUtil.getRoleId();

        List<TreeNode> nodeList = sysRoleService.queryTreeNodeList(type, roleId);
        JSONArray array = new JSONArray();
        array.addAll(nodeList);

        return array;
    }

    /**
     * 保存角色
     *
     * @param parentId
     * @return
     * @author radish
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject insert(@RequestParam(value = "parentId", required = true) Long parentId,
                      @RequestParam(value = "roleName", required = true) String roleName) {
        JSONObject result = new JSONObject();
        result.put("ret", 1);
        result.put("message", "保存成功");

        if (StringUtils.isBlank(roleName)) {
            result.put("ret", -1);
            result.put("message", "请输入角色名称");
            return result;
        }

        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(2);
        SysRole param = new SysRole();
        param.setRoleName(roleName.trim());
        List<SysRole> list = sysRoleService.queryByVo(page, param);
        if (CollectionUtils.isNotEmpty(list)) {
            for (SysRole temp : list) {
                if (StringUtils.equals(temp.getRoleName(), roleName.trim())) {
                    result.put("ret", -1);
                    result.put("message", "角色名称重复");
                    return result;
                }
            }
        }

        SysRole parentRole = sysRoleService.getById(parentId);
        SysRole role = new SysRole();
        role.setParentId(parentRole.getRoleId());
        role.setLevels(parentRole.getLevels() + 1);
        role.setRoleName(roleName);
        role.setCanDel(CanDel.Y.dbValue);
        role.setRootId(parentRole.getRootId());
        sysRoleService.insert(role);

        return result;
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject update(@RequestParam(value = "roleId", required = true) Long roleId,
                      @RequestParam(value = "roleName", required = true) String roleName) {
        JSONObject result = new JSONObject();
        result.put("ret", 1);
        result.put("message", "保存成功");
        if (StringUtils.isBlank(roleName)) {
            result.put("ret", -1);
            result.put("message", "请输入角色名称");
            return result;
        }


        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(2);
        SysRole param = new SysRole();
        param.setRoleName(roleName.trim());
        List<SysRole> list = sysRoleService.queryByVo(page, param);
        if (CollectionUtils.isNotEmpty(list)) {
            for (SysRole temp : list) {
                if (temp.getRoleId().longValue() != roleId.longValue()) {
                    if (StringUtils.equals(temp.getRoleName(), roleName.trim())) {
                        result.put("ret", -1);
                        result.put("message", "角色名称重复");
                        return result;
                    }
                }
            }
        }

        SysRole role = sysRoleService.getById(roleId);
        if (!StringUtils.equals(roleName, role.getRoleName())) {
            role.setRoleName(roleName);
            sysRoleService.update(role);
        }

        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject delete(@RequestParam(value = "roleId", required = true) Long roleId) {
        JSONObject result = new JSONObject();
        result.put("ret", 1);
        result.put("message", "角色及其子角色删除成功");

        if (roleId == null) {
            result.put("ret", -1);
            result.put("message", "参数错误，请重新操作");
            return result;
        }

        SysRole sysRole = sysRoleService.getById(roleId);
        if (CanDel.N.dbValue == sysRole.getCanDel()) {
            result.put("ret", -1);
            result.put("message", "此角色不能被删除，如需帮助请联系运维人员");
            return result;
        }

        sysRoleService.delete(roleId);

        return result;
    }
}
