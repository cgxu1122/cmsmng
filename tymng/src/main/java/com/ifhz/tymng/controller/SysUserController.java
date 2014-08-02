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
import com.ifhz.core.constants.Active;
import com.ifhz.core.constants.AdminRoleType;
import com.ifhz.core.po.auth.SysRole;
import com.ifhz.core.po.auth.SysUser;
import com.ifhz.core.service.auther.SysRoleService;
import com.ifhz.core.service.auther.SysUserService;
import com.ifhz.core.service.auther.jsonBean.TreeNode;
import com.ifhz.core.shiro.utils.CurrentUserUtil;
import com.ifhz.core.utils.PatternUtils;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * @author luyujian
 */
@Controller
@RequestMapping("/tymng/auth/user")
public class SysUserController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/index")
    public String indexUser() {
        return "auth/user/index";
    }

    @RequestMapping("/updateindex")
    public String updateindex() {
        return "auth/user/updateindex";
    }

    @RequestMapping("/tree")
    public String tree() {
        return "auth/user/radioTree";
    }


    @RequestMapping(value = "/initRoleTree", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONArray initRoleTree(@RequestParam(value = "roleId", required = false) Long roleId) {
        AdminRoleType type = CurrentUserUtil.getAdminRoleType();
        long currRoleId = CurrentUserUtil.getRoleId();

        List<TreeNode> nodeList = sysRoleService.queryTreeNodeList(type, currRoleId);
        if (roleId != null && roleId.longValue() != -1) {
            for (TreeNode node : nodeList) {
                if (node.getId() == roleId.longValue()) {
                    node.setChecked(true);
                }
            }
        }

        JSONArray array = new JSONArray();
        array.addAll(nodeList);

        return array;
    }


    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject list(@RequestParam(value = "page", required = true) Integer pageNum,
                           @RequestParam(value = "rows", required = true) Integer pageSize,
                           @RequestParam(value = "roleId", required = false) Long roleId,
                           @RequestParam(value = "searchValue", required = false) String searchValue,
                           HttpServletRequest request) {
        /**分页*/
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(pageNum);
        pagination.setPageSize(pageSize);
        //查询条件
        SysUser sysUser = new SysUser();
        sysUser.setRoleId(roleId);
        sysUser.setSearchValue(searchValue);
        List<SysUser> list = sysUserService.queryByVo(pagination, sysUser);
        if (CollectionUtils.isEmpty(list)) {
            list = Lists.newArrayList();
        } else {
            for (SysUser user : list) {
                SysRole role = sysRoleService.getById(user.getRoleId());
                user.setRoleName(role.getRoleName());
            }
        }
        JSONObject result = new JSONObject();
        result.put("total", pagination.getTotalCount());
        result.put("rows", list);
        return result;
    }


    @RequestMapping(value = "/insert", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject insert(HttpServletRequest req, SysUser user) {
        JSONObject result = new JSONObject();
        result.put("ret", 1);
        result.put("message", "保存成功");

        if (!PatternUtils.verifyLoginName(user.getLoginName())) {
            result.put("ret", -1);
            result.put("message", "用户名包含非法字符，请正确输入用户名");
            return result;
        }

        String roleId = req.getParameter("roleId");
        if (StringUtils.isBlank(roleId)) {
            result.put("ret", -1);
            result.put("message", "请选择角色");
            return result;
        }

        boolean flag = this.checkUnique(user.getLoginName());
        if (!flag) {
            result.put("ret", -1);
            result.put("message", "用户名称重复");
            return result;
        }
        user.setCreateTime(new Date());
        String password = StringUtils.trim(user.getPassword());
        user.setPassword(password);
        user.setActive(Active.Y.dbValue);
        sysUserService.insert(user);

        return result;
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject update(HttpServletRequest req, SysUser user) {
        JSONObject result = new JSONObject();
        result.put("ret", 1);
        result.put("message", "保存成功");

        if (null == user.getUserId()) {
            result.put("ret", -1);
            result.put("message", "用户为空");
            return result;
        }

        SysUser dbuser = sysUserService.getById(user.getUserId());

        String realName = req.getParameter("realName");
        if (org.apache.commons.lang3.StringUtils.isBlank(realName)) {
            result.put("ret", -1);
            result.put("message", "手机号码为空");
            return result;
        }
        if (!org.apache.commons.lang3.StringUtils.equals(realName, dbuser.getRealName())) {
            dbuser.setRealName(realName);
        }

        String cellPhone = req.getParameter("cellPhone");
        if (org.apache.commons.lang3.StringUtils.isBlank(cellPhone)) {
            result.put("ret", -1);
            result.put("message", "手机号码为空");
            return result;
        }
        if (!org.apache.commons.lang3.StringUtils.equals(cellPhone, dbuser.getCellPhone())) {
            dbuser.setCellPhone(cellPhone);
        }

        String address = req.getParameter("address");
        if (org.apache.commons.lang3.StringUtils.isBlank(address)) {
            result.put("ret", -1);
            result.put("message", "地址为空");
            return result;
        }
        if (!org.apache.commons.lang3.StringUtils.equals(address, dbuser.getAddress())) {
            dbuser.setAddress(address);
        }

        if (user.getRoleId() == null) {
            result.put("ret", -1);
            result.put("message", "请选择角色");
            return result;
        }
        if (user.getRoleId() != dbuser.getRoleId()) {
            dbuser.setRoleId(user.getRoleId());
        }

        sysUserService.update(dbuser);
        return result;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject updatePassword(@RequestParam(value = "userId", required = true) Long userId,
                              @RequestParam(value = "password", required = true) String password,
                              @RequestParam(value = "confirmPwd", required = true) String confirmPwd) {
        LOGGER.info("userId={},password={}, confirmPwd={}", userId, password, confirmPwd);

        JSONObject result = new JSONObject();
        result.put("ret", 1);
        result.put("message", "密码修改成功");

        try {
            if (StringUtils.isBlank(password) || StringUtils.isBlank(confirmPwd)) {
                result.put("ret", -1);
                result.put("message", "密码或确认密码必须填写");
                return result;
            }

            if (!StringUtils.equals(password.trim(), confirmPwd.trim())) {
                result.put("ret", -1);
                result.put("message", "密码和确认密码不匹配，请重新填写");
                return result;
            }
            SysUser sysUser = new SysUser();
            sysUser.setUserId(userId);
            sysUser.setPassword(password.trim());

            sysUserService.updatePassword(sysUser);
        } catch (Exception e) {
            LOGGER.error("updatePassword error", e);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject delete(@RequestParam(value = "userId", required = true) Long userId,
                      HttpServletRequest request) {
        JSONObject result = new JSONObject();
        result.put("ret", 1);
        result.put("message", "删除成功");
        sysUserService.delete(userId);
        return result;
    }


    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject updateStatus(HttpServletRequest req) {

        JSONObject result = new JSONObject();
        result.put("ret", 1);
        result.put("message", "状态修改成功");

        try {
            String active = req.getParameter("active");
            if (StringUtils.isBlank(active)) {
                result.put("ret", -1);
                result.put("message", "状态必须填写");
                return result;
            }

            String userId = req.getParameter("userId");
            if (StringUtils.isBlank(userId)) {
                result.put("ret", -1);
                result.put("message", "用户id必须填写");
                return result;
            }

            SysUser dbuser = sysUserService.getById(Long.valueOf(userId));
            dbuser.setActive(active);
            sysUserService.updateStatus(dbuser);
        } catch (Exception e) {
            LOGGER.error("updatePassword error", e);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
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
        SysUser user = sysUserService.getByLoginName(name);
        if (user == null) {
            flag = true;
        }
        return flag;
    }
}
