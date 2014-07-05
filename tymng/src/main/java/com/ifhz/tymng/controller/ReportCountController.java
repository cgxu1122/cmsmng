package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.po.ProductStat;
import com.ifhz.core.service.channel.ChannelInfoService;
import com.ifhz.core.service.export.model.BaseExportModel;
import com.ifhz.core.service.stat.LogStatQueryService;
import com.ifhz.core.service.stat.ProductStatQueryService;
import org.apache.commons.collections.CollectionUtils;
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
@RequestMapping("/tymng/reportCount")
public class ReportCountController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportCountController.class);
    @Autowired
    private ChannelInfoService channelInfoService;
    @Autowired
    private LogStatQueryService logStatQueryService;
    @Autowired
    private ProductStatQueryService productStatQueryService;

    @RequestMapping("/indexStore")
    public ModelAndView indexTY(HttpServletRequest request) {
        return new ModelAndView("reportCount/indexStore");
    }

    @RequestMapping("/indexChannelProcess")
    public ModelAndView indexDB(HttpServletRequest request) {
        return new ModelAndView("reportCount/indexChannelProcess");
    }

    @RequestMapping("/indexChannelCounter")
    public ModelAndView indexCP(HttpServletRequest request) {
        return new ModelAndView("reportCount/indexChannelCounter");
    }

    @RequestMapping("/indexProduct")
    public ModelAndView indexLW(HttpServletRequest request) {
        return new ModelAndView("reportCount/indexProduct");
    }

    @RequestMapping(value = "/listLogStat", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject listLogStat(HttpServletRequest request) {
        /**分页*/
        String pageNum = request.getParameter("page");
        String pageSize = request.getParameter("rows");
        Pagination page = new Pagination();
        if (!StringUtils.isEmpty(pageNum)) page.setCurrentPage(Integer.valueOf(pageNum));
        if (!StringUtils.isEmpty(pageSize)) page.setPageSize(Integer.valueOf(pageSize));
        //查询条件
        String ua = request.getParameter("ua");
        String deviceCode = request.getParameter("deviceCode");
        String channelId = request.getParameter("channelId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String groupId = request.getParameter("groupId");
        LogStat logStat = new LogStat();
        if (StringUtils.isNotEmpty(groupId)) {
            logStat.setGroupId(Long.parseLong(groupId));
        }
        if (StringUtils.isNotEmpty(channelId)) {
            logStat.setChannelId(Long.parseLong(channelId));
        }
        if (StringUtils.isNotEmpty(deviceCode)) {
            logStat.setDeviceCode(deviceCode.trim());
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
        if (CollectionUtils.isNotEmpty(list)) {
            LogStat countLogStat = logStatQueryService.queryCountByVo(logStat);
            list.add(countLogStat);
        }
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }


    @RequestMapping(value = "/exportData")
    @ResponseBody
    public void exportData(HttpServletRequest request, HttpServletResponse response) {
        String ua = request.getParameter("ua");
        String deviceCode = request.getParameter("deviceCode");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String groupId = request.getParameter("groupId");
        String channelId = request.getParameter("channelId");
        LogStat logStat = new LogStat();
        if (StringUtils.isNotEmpty(groupId)) {
            logStat.setGroupId(Long.parseLong(groupId));
        }
        if (StringUtils.isNotEmpty(channelId)) {
            logStat.setChannelId(Long.parseLong(channelId));
        }
        if (StringUtils.isNotEmpty(ua)) {
            logStat.setUa(ua.trim());
        }
        if (StringUtils.isNotEmpty(deviceCode)) {
            logStat.setDeviceCode(deviceCode.trim());
        }
        if (StringUtils.isNotEmpty(startDate)) {
            logStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            logStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        List<LogStat> list = logStatQueryService.queryByVo(null, logStat);
        if (CollectionUtils.isNotEmpty(list)) {
            LogStat countLogStat = logStatQueryService.queryCountByVo(logStat);
            logStat.setModelName("合计:");
            list.add(countLogStat);
        }
        String exportType = request.getParameter("exportType");
        BaseExportModel exportModel = new BaseExportModel();
        Map<String, String> titleMap = new LinkedHashMap<String, String>();
        titleMap.put("processDate", "日期");
        titleMap.put("modelName", "机型全称");
        titleMap.put("channelName", "仓库名称");
        if ("1".equals(exportType)) {//按仓库查询
            titleMap.put("devicePrsDayNum", "装机数量");
            titleMap.put("deviceUpdDayNum", "装机到达数量");
            titleMap.put("prsActiveTotalNum", "累计到达数量");
            titleMap.put("prsActiveValidNum", "有效到达数量");
            titleMap.put("prsActiveInvalidNum", "无效到达数量");
            titleMap.put("prsInvalidReplaceNum", "替换数量");
            titleMap.put("prsInvalidUninstallNum", "卸载数量");
        } else if ("2".equals(exportType)) {//按渠道查询加工数据
            titleMap.put("deviceCode", "设备编码");
            titleMap.put("devicePrsDayNum", "装机数量");
        } else if ("3".equals(exportType)) {//按渠道查询到达数据
            titleMap.put("deviceCode", "设备编码");
            titleMap.put("deviceUpdDayNum", "装机到达数量");
            titleMap.put("prsActiveTotalNum", "累计到达数量");
            titleMap.put("prsActiveValidNum", "有效到达数量");
            titleMap.put("prsActiveInvalidNum", "无效到达数量");
            titleMap.put("prsInvalidReplaceNum", "替换数量");
            titleMap.put("prsInvalidUninstallNum", "卸载数量");
        }

        exportModel.setTitleMap(titleMap);
        exportModel.setDataList(list);
        super.exportXLSData(request, response, exportModel);
    }

    @RequestMapping(value = "/listProductStat", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject listProductStat(HttpServletRequest request) {
        /**分页*/
        String pageNum = request.getParameter("page");
        String pageSize = request.getParameter("rows");
        Pagination page = new Pagination();
        if (!StringUtils.isEmpty(pageNum)) page.setCurrentPage(Integer.valueOf(pageNum));
        if (!StringUtils.isEmpty(pageSize)) page.setPageSize(Integer.valueOf(pageSize));
        //查询条件
        String ua = request.getParameter("ua");
        String productId = request.getParameter("productId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String groupId = request.getParameter("groupId");
        ProductStat productStat = new ProductStat();
        if (StringUtils.isNotEmpty(groupId)) {
            productStat.setGroupId(Long.parseLong(groupId));
        }
        if (StringUtils.isNotEmpty(productId)) {
            productStat.setProductId(Long.parseLong(productId));
        }
        if (StringUtils.isNotEmpty(ua)) {
            productStat.setUa(ua.trim());
        }
        if (StringUtils.isNotEmpty(startDate)) {
            productStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            productStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        List<ProductStat> list = productStatQueryService.queryByVo(page, productStat);
        if (CollectionUtils.isNotEmpty(list)) {
            ProductStat countProductStat = productStatQueryService.queryCountByVo(productStat);
            list.add(countProductStat);
        }
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/exportProductData")
    @ResponseBody
    public void exportProductData(HttpServletRequest request, HttpServletResponse response) {
        String ua = request.getParameter("ua");
        String productId = request.getParameter("productId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String groupId = request.getParameter("groupId");
        ProductStat productStat = new ProductStat();
        if (StringUtils.isNotEmpty(groupId)) {
            productStat.setGroupId(Long.parseLong(groupId));
        }
        if (StringUtils.isNotEmpty(productId)) {
            productStat.setProductId(Long.parseLong(productId));
        }
        if (StringUtils.isNotEmpty(ua)) {
            productStat.setUa(ua.trim());
        }
        if (StringUtils.isNotEmpty(startDate)) {
            productStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            productStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        List<ProductStat> list = productStatQueryService.queryByVo(null, productStat);
        if (CollectionUtils.isNotEmpty(list)) {
            ProductStat countProductStat = productStatQueryService.queryCountByVo(productStat);
            list.add(countProductStat);
        }
        BaseExportModel exportModel = new BaseExportModel();
        Map<String, String> titleMap = new LinkedHashMap<String, String>();
        titleMap.put("processDate", "日期");
        titleMap.put("modelName", "机型名称");
        titleMap.put("groupName", "渠道组织");
        titleMap.put("productPrsDayNum", "装机数量");
        titleMap.put("productUpdDayNum", "装机到达数量");
        titleMap.put("prsActiveTotalNum", "累计到达数量");
        exportModel.setTitleMap(titleMap);
        exportModel.setDataList(list);
        super.exportXLSData(request, response, exportModel);
    }

}