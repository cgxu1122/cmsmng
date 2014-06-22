package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.service.export.model.BaseExportModel;
import com.ifhz.core.service.stat.LogStatQueryService;
import com.ifhz.core.service.stat.ProductStatQueryService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/partnerQuery")
public class PartnerQueryController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerQueryController.class);
    @Autowired
    private LogStatQueryService logStatQueryService;
    @Autowired
    private ProductStatQueryService productStatQueryService;

    @RequestMapping("/indexTY")
    public ModelAndView indexTY(HttpServletRequest request) {
        return new ModelAndView("partnerQuery/indexTY");
    }

    @RequestMapping("/indexDB")
    public ModelAndView indexDB(HttpServletRequest request) {
        return new ModelAndView("partnerQuery/indexDB");
    }

    @RequestMapping("/indexCP")
    public ModelAndView indexCP(HttpServletRequest request) {
        return new ModelAndView("partnerQuery/indexCP");
    }

    @RequestMapping("/indexLW")
    public ModelAndView indexLW(HttpServletRequest request) {
        return new ModelAndView("partnerQuery/indexLW");
    }

    @RequestMapping(value = "/listLogStat", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject list(HttpServletRequest request) {
        /**分页*/
        String pageNum = request.getParameter("page");
        String pageSize = request.getParameter("rows");
        Pagination page = new Pagination();
        if (!StringUtils.isEmpty(pageNum)) page.setCurrentPage(Integer.valueOf(pageNum));
        if (!StringUtils.isEmpty(pageSize)) page.setPageSize(Integer.valueOf(pageSize));
        //查询条件
        String ua = request.getParameter("ua");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String groupId = request.getParameter("groupId");
        LogStat logStat = new LogStat();
        if (StringUtils.isNotEmpty(groupId)) {
            logStat.setGroupId(Long.parseLong(groupId));
        }
        if (StringUtils.isNotEmpty(ua)) {
            logStat.setUa(ua.trim());
        }
        if (StringUtils.isNotEmpty(startDate)) {
            logStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            logStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        List<LogStat> list = logStatQueryService.queryByVo(page, logStat);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }


    @RequestMapping(value = "/exportData")
    @ResponseBody
    public void exportData(HttpServletRequest request, HttpServletResponse response) {
        String ua = request.getParameter("ua");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String groupId = request.getParameter("groupId");
        LogStat logStat = new LogStat();
        if (StringUtils.isNotEmpty(groupId)) {
            logStat.setGroupId(Long.parseLong(groupId));
        }
        if (StringUtils.isNotEmpty(ua)) {
            logStat.setUa(ua.trim());
        }
        if (StringUtils.isNotEmpty(startDate)) {
            logStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            logStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        List<LogStat> list = logStatQueryService.queryByVo(null, logStat);
        BaseExportModel exportModel = new BaseExportModel();
        Map<String, String> titleMap = new LinkedHashMap<String, String>();
        titleMap.put("processDate", "日期");
        titleMap.put("deviceCode", "设备编码");
        titleMap.put("channelName", "仓库名称");
        titleMap.put("modelName", "机型名称");
        titleMap.put("devicePrsDayNum", "装机数量");
        exportModel.setTitleMap(titleMap);
        exportModel.setDataList(list);
        super.exportXLSData(request, response, exportModel);
    }
}