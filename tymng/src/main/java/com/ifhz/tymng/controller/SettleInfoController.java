package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.commons.util.JcywUtils;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.SettleInfo;
import com.ifhz.core.service.auth.impl.ShiroDbRealm;
import com.ifhz.core.service.settle.SettleInfoService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/tymng/settleInfo")
public class SettleInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettleInfoController.class);
    @Autowired
    private SettleInfoService settleInfoService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("settleInfo/index");
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
        String modelNameCondition = request.getParameter("modelNameCondition");
        SettleInfo si = new SettleInfo();
        si.setCreateBy(((ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal()).userId.longValue());
        si.setModelNameCondition(modelNameCondition);
        List<SettleInfo> list = settleInfoService.queryByVo(page, si);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/insert", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject insert(HttpServletRequest request) {
        String modelName = request.getParameter("modelName");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String price = request.getParameter("price");
        String remark = request.getParameter("remark");
        String errorMsg = null;
        if (StringUtils.isEmpty(modelName) || modelName.length() > 100) {
            errorMsg = "请正确输入机型名称！";
        } else if (StringUtils.isEmpty(startTime)) {
            errorMsg = "请选择开始时间！";
        } else if (StringUtils.isEmpty(endTime)) {
            errorMsg = "请选择结束时间！";
        } else if (StringUtils.isEmpty(price) || !JcywUtils.isDouble(price)) {
            errorMsg = "请输入正确的价格！";
        } else if (StringUtils.isEmpty(remark) || modelName.length() > 500) {
            errorMsg = "请正确输入备注！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        SettleInfo si = new SettleInfo();
        si.setModelName(modelName.trim());
        si.setStartTime(DateFormatUtils.parse(startTime, GlobalConstants.DATE_FORMAT_DPT));
        si.setEndTime(DateFormatUtils.parse(endTime, GlobalConstants.DATE_FORMAT_DPT));
        si.setPrice(Double.parseDouble(price));
        si.setRemark(remark);
        settleInfoService.insert(si);
        result.put("msg", "添加成功!");
        return result;
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject update(HttpServletRequest request) {
        String settleId = request.getParameter("settleId");
        String modelName = request.getParameter("modelName");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String price = request.getParameter("price");
        String remark = request.getParameter("remark");
        String errorMsg = null;
        if (StringUtils.isEmpty(settleId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(modelName) || modelName.length() > 100) {
            errorMsg = "请正确输入机型名称！";
        } else if (StringUtils.isEmpty(startTime)) {
            errorMsg = "请选择开始时间！";
        } else if (StringUtils.isEmpty(endTime)) {
            errorMsg = "请选择结束时间！";
        } else if (StringUtils.isEmpty(price) || !JcywUtils.isDouble(price)) {
            errorMsg = "请输入正确的价格！";
        } else if (StringUtils.isEmpty(remark) || modelName.length() > 500) {
            errorMsg = "请正确输入备注！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        SettleInfo settleInfo = settleInfoService.getById(Long.parseLong(settleId));
        if (settleInfo == null) {
            result.put("errorMsg", "数据已被删除，请刷新!");
            return result;
        }
        settleInfo.setModelName(modelName.trim());
        settleInfo.setStartTime(DateFormatUtils.parse(startTime, GlobalConstants.DATE_FORMAT_DPT));
        settleInfo.setEndTime(DateFormatUtils.parse(endTime, GlobalConstants.DATE_FORMAT_DPT));
        settleInfo.setPrice(Double.parseDouble(price));
        settleInfo.setRemark(remark);
        settleInfoService.update(settleInfo);
        result.put("msg", "修改成功!");
        return result;
    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject delete(HttpServletRequest request) {
        String settleId = request.getParameter("settleId");
        String errorMsg = null;
        if (StringUtils.isEmpty(settleId)) {
            errorMsg = "系统错误，请联系管理员！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        SettleInfo si = settleInfoService.getById(Long.parseLong(settleId));
        if (si == null) {
            result.put("errorMsg", "数据已被其他人操作，请刷新!");
        } else {
            settleInfoService.delete(si);
            result.put("msg", "删除成功!");
        }
        return result;
    }

}