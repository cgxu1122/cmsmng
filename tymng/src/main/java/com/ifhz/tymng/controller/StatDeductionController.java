package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.StatDeduction;
import com.ifhz.core.service.stat.StatDeductionService;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping("/tymng/statDeduction")
public class StatDeductionController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatDeductionController.class);
    @Autowired
    private StatDeductionService statDeductionService;

    @RequestMapping("/indexDB")
    public ModelAndView indexDB(HttpServletRequest request) {
        return new ModelAndView("statDeduction/indexDB");
    }

    @RequestMapping("/indexQT")
    public ModelAndView indexQT(HttpServletRequest request) {
        return new ModelAndView("statDeduction/indexQT");
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
        String channelNameCondition = request.getParameter("channelNameCondition");
        String groupId = request.getParameter("groupId");
        StatDeduction sd = new StatDeduction();
        sd.setChannelNameCondition(channelNameCondition);
        if (StringUtils.isNotEmpty(groupId)) {
            sd.setGroupId(Long.parseLong(groupId));
        }
        List<StatDeduction> list = statDeductionService.queryByVo(page, sd);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject update(HttpServletRequest request) {
        String statDeductionId = request.getParameter("id");
        String basicNum = request.getParameter("basicNum");
        String percentage = request.getParameter("percentage");
        String errorMsg = null;
        if (StringUtils.isEmpty(statDeductionId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(basicNum) || !StringUtils.isNumeric(basicNum)) {
            errorMsg = "请正确输入扣量下限值！";
        } else if (StringUtils.isEmpty(percentage) || !StringUtils.isNumeric(percentage) || Integer.parseInt(percentage) < 0 || Integer.parseInt(percentage) > 100) {
            errorMsg = "扣量比例请输入0-100的数值！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        StatDeduction statDeduction = statDeductionService.getById(Long.parseLong(statDeductionId));
        if (statDeduction == null) {
            result.put("errorMsg", "数据已被删除，请刷新!");
            return result;
        }
        statDeduction.setBasicNum(Integer.parseInt(basicNum));
        statDeduction.setPercentage(Integer.parseInt(percentage));
        statDeductionService.update(statDeduction);
        result.put("msg", "修改成功!");
        return result;
    }
}