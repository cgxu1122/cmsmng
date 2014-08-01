/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.constants.AdminRoleType;
import com.ifhz.core.service.auther.SysAuthService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * @author luyujian
 */
@Controller
@RequestMapping("/tymng/auth/sysauth")
public class SysAuthController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysAuthController.class);

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysAuthService sysAuthService;

    @RequestMapping("/index")
    public String index() {
        return "auth/sys/sys_maint";
    }

    @RequestMapping("/roleTree")
    public String tree() {
        return "auth/sys/roleTree";
    }

    @RequestMapping("/blank")
    public String blank() {
        return "auth/sys/blank";
    }

    @RequestMapping("/resourceIndex")
    public ModelAndView resourceIndex(HttpServletRequest request) {
        String roleId = request.getParameter("roleId");
        Map<String, Object> result = Maps.newHashMap();
        result.put("roleId", roleId);

        return new ModelAndView("auth/sys/resourceTree", result);
    }


    @RequestMapping(value = "/initRoleTree", produces = {"application/json;charset=UTF-8"})
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

    @RequestMapping(value = "/initResourceTree", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONArray initResourceTree(@RequestParam(value = "roleId", required = true) Long roleId) {
        AdminRoleType type = CurrentUserUtil.getAdminRoleType();
        long loginRoleId = CurrentUserUtil.getRoleId();
        List<TreeNode> nodeList = sysAuthService.queryTreeNodeList(type, loginRoleId, roleId);
        JSONArray array = new JSONArray();
        array.addAll(nodeList);

        return array;
    }

    @RequestMapping(value = "/auth", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String sysAuth(@RequestParam(value = "roleId", required = true) Long roleId,
                   @RequestParam(value = "resIds", required = true) String resIds) {
        String msg = "授权成功";
        try {
            long loginRoleId = CurrentUserUtil.getRoleId();
            if (loginRoleId == roleId.longValue()) {
                msg = "不能给自己赋权";
            }
            List<Long> resIdList = Lists.newArrayList();
            if (StringUtils.isBlank(resIds)) {
                msg = "请先选择权限再授权.";
            } else {
                String[] ids = StringUtils.split(resIds, "\\,");
                for (int i = 0; i < ids.length; i++) {
                    if (StringUtils.isNotBlank(ids[i])) {
                        resIdList.add(Long.parseLong(ids[i]));
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(resIdList)) {
                sysAuthService.authSys(roleId, resIdList);
            }
        } catch (Exception e) {
            LOGGER.error("sysAuth error", e);
            msg = "授权失败";
        }

        return msg;
    }
}
