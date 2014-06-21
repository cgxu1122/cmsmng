package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PartnerInfo;
import com.ifhz.core.po.User;
import com.ifhz.core.service.auth.UserService;
import com.ifhz.core.service.partner.PartnerInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/partnerInfo")
public class PartnerInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerInfoController.class);
    @Autowired
    private PartnerInfoService partnerInfoService;
    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("partnerInfo/index");
    }

    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject list(HttpServletRequest request) {
        /**分页*/
        String pageNum = request.getParameter("page");
        String pageSize = request.getParameter("rows");
        Pagination page = new Pagination();
        if (!StringUtils.isEmpty(pageNum)) page.setCurrentPage(Integer.valueOf(pageNum));
        if (!StringUtils.isEmpty(pageSize)) page.setPageSize(Integer.valueOf(pageSize));
        //查询条件
        String partnerNameCondition = request.getParameter("partnerNameCondition");
        PartnerInfo pi = new PartnerInfo();
        pi.setActive(JcywConstants.ACTIVE_Y);
        pi.setPartnerNameCondition(partnerNameCondition);
        List<PartnerInfo> list = partnerInfoService.queryByVo(page, pi);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/insert", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject insert(HttpServletRequest request) {
        String partnerName = request.getParameter("partnerName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String queryImeiSource = request.getParameter("queryImeiSource");
        String exportImeiSource = request.getParameter("exportImeiSource");
        String errorMsg = null;
        if (StringUtils.isEmpty(partnerName) || partnerName.length() > 50) {
            errorMsg = "请正确输入合作方名称！";
        } else if (StringUtils.isEmpty(username) || username.length() > 50) {
            errorMsg = "请正确输入用户名！";
        } else if (StringUtils.isEmpty(password) || password.length() > 50) {
            errorMsg = "请正确输入密码！";
        } else if (StringUtils.isEmpty(queryImeiSource)) {
            errorMsg = "请选择查看imei权限！";
        } else if (StringUtils.isEmpty(exportImeiSource)) {
            errorMsg = "请选择导出imei权限！";
        }
        //校验用户名唯一性
        User user = userService.findUserByLoginName(username);
        if (user != null) {
            errorMsg = "用户名重复，请重新输入！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        //设备编码唯一性校验
        PartnerInfo pi = new PartnerInfo();
        pi.setPartnerName(partnerName.trim());
        pi.setActive(JcywConstants.ACTIVE_Y);
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(1);
        List<PartnerInfo> list = partnerInfoService.queryByVo(page, pi);
        if (list != null && list.size() > 0) {
            errorMsg = "设备编码重复，请重新输入！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        pi.setExportImeiSource(exportImeiSource);
        pi.setQueryImeiSource(queryImeiSource);
        pi.setUsername(username);
        pi.setPassword(password);
        partnerInfoService.insert(pi);
        result.put("msg", "添加成功!");
        return result;
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject update(HttpServletRequest request) {
        String partnerId = request.getParameter("partnerId");
        String partnerName = request.getParameter("partnerName");
        String password = request.getParameter("password");
        String queryImeiSource = request.getParameter("queryImeiSource");
        String exportImeiSource = request.getParameter("exportImeiSource");
        String errorMsg = null;
        if (StringUtils.isEmpty(partnerId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(partnerName) || partnerName.length() > 50) {
            errorMsg = "请正确输入合作方名称！";
        } else if (StringUtils.isEmpty(password) || password.length() > 50) {
            errorMsg = "请正确输入密码！";
        } else if (StringUtils.isEmpty(queryImeiSource)) {
            errorMsg = "请选择查看imei权限！";
        } else if (StringUtils.isEmpty(exportImeiSource)) {
            errorMsg = "请选择导出imei权限！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        PartnerInfo partnerInfo = partnerInfoService.getById(Long.parseLong(partnerId));
        if (partnerInfo == null) {
            result.put("errorMsg", "数据已被删除，请刷新!");
            return result;
        }
        //设备编码唯一性校验
        PartnerInfo pi = new PartnerInfo();
        pi.setPartnerName(partnerName.trim());
        pi.setActive(JcywConstants.ACTIVE_Y);
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(1);
        List<PartnerInfo> list = partnerInfoService.queryByVo(page, pi);
        if (list != null && list.size() > 0) {
            for (PartnerInfo repeatCodePi : list) {
                if (repeatCodePi.getPartnerId() != partnerInfo.getPartnerId()) {
                    result.put("errorMsg", "合作方名称重复，请重新输入！");
                    return result;
                }
            }
        }
        partnerInfo.setPartnerName(partnerName.trim());
        partnerInfo.setQueryImeiSource(queryImeiSource);
        partnerInfo.setExportImeiSource(exportImeiSource);
        partnerInfo.setPassword(password);
        partnerInfoService.update(partnerInfo);
        result.put("msg", "修改成功!");
        return result;
    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject delete(HttpServletRequest request) {
        String partnerId = request.getParameter("partnerId");
        String errorMsg = null;
        if (StringUtils.isEmpty(partnerId)) {
            errorMsg = "系统错误，请联系管理员！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        PartnerInfo pi = partnerInfoService.getById(Long.parseLong(partnerId));
        if (pi == null) {
            result.put("errorMsg", "数据已被其他人操作，请刷新!");
        } else {
            partnerInfoService.delete(pi);
            result.put("msg", "删除成功!");
        }
        return result;
    }

}