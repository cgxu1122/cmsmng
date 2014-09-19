package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.commons.util.ExportDataUtil;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.po.stat.LogArriveStat;
import com.ifhz.core.po.stat.ProductArriveStat;
import com.ifhz.core.po.stat.ProductInstallStat;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.service.channel.ChannelInfoService;
import com.ifhz.core.service.export.model.BaseExportModel;
import com.ifhz.core.service.imei.StatImeiService;
import com.ifhz.core.service.imei.bean.ImeiQueryType;
import com.ifhz.core.service.imei.bean.QueryActive;
import com.ifhz.core.service.imei.bean.StatImeiRequest;
import com.ifhz.core.service.imei.bean.StatImeiResult;
import com.ifhz.core.service.stat.LogArriveStatService;
import com.ifhz.core.service.stat.LogStatQueryService;
import com.ifhz.core.service.stat.ProductArriveStatService;
import com.ifhz.core.service.stat.ProductInstallStatService;
import com.ifhz.core.shiro.utils.CurrentUserUtil;
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
import java.io.File;
import java.util.*;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/tymng/reportCountNew")
public class ReportCountNewController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportCountNewController.class);
    @Autowired
    private ChannelInfoService channelInfoService;
    @Autowired
    private LogStatQueryService logStatQueryService;
    @Autowired
    private LogArriveStatService logArriveStatService;
    @Autowired
    private ProductInstallStatService productInstallStatService;
    @Autowired
    private ProductArriveStatService productArriveStatService;
    @Autowired
    private LocalDirCacheService localDirCacheService;
    @Autowired
    private StatImeiService statImeiService;

    @RequestMapping("/indexInstallTY")
    public ModelAndView indexInstallTY(HttpServletRequest request) {
        return new ModelAndView("reportCountNew/indexInstallTY");
    }

    @RequestMapping("/indexInstallDB")
    public ModelAndView indexInstallDB(HttpServletRequest request) {
        return new ModelAndView("reportCountNew/indexInstallDB");
    }

    @RequestMapping("/indexInstallQT")
    public ModelAndView indexInstallQT(HttpServletRequest request) {
        return new ModelAndView("reportCountNew/indexInstallQT");
    }

    @RequestMapping("/indexInstallProduct")
    public ModelAndView indexInstallProduct(HttpServletRequest request) {
        return new ModelAndView("reportCountNew/indexInstallProduct");
    }

    @RequestMapping("/indexArriveTY")
    public ModelAndView indexArriveTY(HttpServletRequest request) {
        return new ModelAndView("reportCountNew/indexArriveTY");
    }

    @RequestMapping("/indexArriveDB")
    public ModelAndView indexArriveDB(HttpServletRequest request) {
        return new ModelAndView("reportCountNew/indexArriveDB");
    }

    @RequestMapping("/indexArriveQT")
    public ModelAndView indexArriveQT(HttpServletRequest request) {
        return new ModelAndView("reportCountNew/indexArriveQT");
    }

    @RequestMapping("/indexArriveProduct")
    public ModelAndView indexArriveProduct(HttpServletRequest request) {
        return new ModelAndView("reportCountNew/indexArriveProduct");
    }

    @RequestMapping(value = "/listStoreLogStat", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject listStoreLogStat(HttpServletRequest request) {
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
        String channelIdCondition = request.getParameter("channelIdCondition");
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
        if (StringUtils.isNotEmpty(channelIdCondition)) {
            logStat.setChannelIdCondition(channelIdCondition);
        }
        List<LogStat> list = logStatQueryService.querySumByVo(page, logStat);
        if (CollectionUtils.isNotEmpty(list)) {
            LogStat countLogStat = logStatQueryService.queryCountByVo(logStat);
            list.add(countLogStat);
        }
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
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
        String channelIdCondition = request.getParameter("channelIdCondition");
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

        List<LogStat> list = new ArrayList<LogStat>();
        if (StringUtils.isNotEmpty(channelIdCondition)) {
            logStat.setChannelIdCondition(channelIdCondition);
            list = logStatQueryService.queryByVo(page, logStat);
        } else {//如果是地包或其他渠道的负责人登录，则进行数据过滤
            if ((JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId) || JcywConstants.CHANNEL_GROUP_QT_ID_3.toString().equals(groupId)) && CurrentUserUtil.isManager()) {
                ChannelInfo ci = new ChannelInfo();
                ci.setGroupId(Long.parseLong(groupId));
                ci.setActive(JcywConstants.ACTIVE_Y);
                ci.setMngId(CurrentUserUtil.getUserId());
                List<ChannelInfo> channelInfoList = channelInfoService.queryByVo(null, ci);
                channelIdCondition = "";
                if (CollectionUtils.isNotEmpty(channelInfoList)) {
                    for (ChannelInfo channelInfo : channelInfoList) {
                        channelIdCondition += channelInfo.getChannelId() + ",";
                    }
                    if (channelIdCondition.endsWith(",")) {
                        channelIdCondition = channelIdCondition.substring(0, channelIdCondition.length() - 1);
                    }
                    logStat.setChannelIdCondition(channelIdCondition);
                    list = logStatQueryService.queryByVo(page, logStat);
                }
            } else {
                list = logStatQueryService.queryByVo(page, logStat);
            }
        }
        if (CollectionUtils.isNotEmpty(list)) {
            LogStat countLogStat = logStatQueryService.queryCountByVo(logStat);
            list.add(countLogStat);
        }
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/exportData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject exportData(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            String ua = request.getParameter("ua");
            String deviceCode = request.getParameter("deviceCode");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String groupId = request.getParameter("groupId");
            String channelId = request.getParameter("channelId");
            String channelIdCondition = request.getParameter("channelIdCondition");
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
            if (StringUtils.isNotEmpty(channelIdCondition)) {
                logStat.setChannelIdCondition(channelIdCondition);
            }
            String exportType = request.getParameter("exportType");
            BaseExportModel exportModel = new BaseExportModel();
            Map<String, String> titleMap = new LinkedHashMap<String, String>();
            titleMap.put("processDate", "日期");
            titleMap.put("modelName", "机型全称");
            titleMap.put("channelName", "仓库名称");

            List<LogStat> list = new ArrayList<LogStat>();
            Pagination page = new Pagination();
            page.setCurrentPage(1);
            page.setPageSize(Integer.valueOf(GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.EXPORT_NUM_MAX)));
            if ("1".equals(exportType)) {//天音渠道装机查询
                titleMap.put("devicePrsDayNum", "装机数量");
                titleMap.put("prsActiveTotalNum", "装机到达数量");
                titleMap.put("prsActiveValidNum", "有效到达数量");
                titleMap.put("prsActiveInvalidNum", "无效到达数量");
                titleMap.put("prsInvalidReplaceNum", "替换数量");
                titleMap.put("prsInvalidUninstallNum", "卸载数量");
                titleMap.put("prsInvalidUnAndReNum", "卸载并替换数量");
                list = logStatQueryService.querySumByVo(page, logStat);
            } else if ("2".equals(exportType)) {//地包渠道装机查询
                titleMap.put("deviceCode", "设备编码");
                titleMap.put("devicePrsDayNum", "装机数量");
                titleMap.put("prsActiveTotalNum", "装机到达数量");
                titleMap.put("prsActiveValidNum", "有效到达数量");
                titleMap.put("prsActiveInvalidNum", "无效到达数量");
                titleMap.put("prsInvalidReplaceNum", "替换数量");
                titleMap.put("prsInvalidUninstallNum", "卸载数量");
                titleMap.put("prsInvalidUnAndReNum", "卸载并替换数量");
                if (StringUtils.isNotEmpty(channelIdCondition)) {
                    logStat.setChannelIdCondition(channelIdCondition);
                    list = logStatQueryService.queryByVo(page, logStat);
                } else {//如果是地包渠道的负责人登录，则进行数据过滤
                    if (JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId) && CurrentUserUtil.isManager()) {
                        ChannelInfo ci = new ChannelInfo();
                        ci.setGroupId(Long.parseLong(groupId));
                        ci.setActive(JcywConstants.ACTIVE_Y);
                        ci.setMngId(CurrentUserUtil.getUserId());
                        List<ChannelInfo> channelInfoList = channelInfoService.queryByVo(null, ci);
                        channelIdCondition = "";
                        if (CollectionUtils.isNotEmpty(channelInfoList)) {
                            for (ChannelInfo channelInfo : channelInfoList) {
                                channelIdCondition += channelInfo.getChannelId() + ",";
                            }
                            if (channelIdCondition.endsWith(",")) {
                                channelIdCondition = channelIdCondition.substring(0, channelIdCondition.length() - 1);
                            }
                            logStat.setChannelIdCondition(channelIdCondition);
                            list = logStatQueryService.queryByVo(page, logStat);
                        }
                    } else {
                        list = logStatQueryService.queryByVo(page, logStat);
                    }
                }
            } else if ("3".equals(exportType)) {//其他渠道装机查询
                titleMap.put("devicePrsDayNum", "装机数量");
                titleMap.put("prsActiveTotalNum", "装机到达数量");
                titleMap.put("prsActiveValidNum", "有效到达数量");
                titleMap.put("prsActiveInvalidNum", "无效到达数量");
                titleMap.put("prsInvalidReplaceNum", "替换数量");
                titleMap.put("prsInvalidUninstallNum", "卸载数量");
                titleMap.put("prsInvalidUnAndReNum", "卸载并替换数量");
                if (StringUtils.isNotEmpty(channelIdCondition)) {
                    logStat.setChannelIdCondition(channelIdCondition);
                    list = logStatQueryService.queryByVo(page, logStat);
                } else {//如果是其他渠道的负责人登录，则进行数据过滤
                    if (JcywConstants.CHANNEL_GROUP_QT_ID_3.toString().equals(groupId) && CurrentUserUtil.isManager()) {
                        ChannelInfo ci = new ChannelInfo();
                        ci.setGroupId(Long.parseLong(groupId));
                        ci.setActive(JcywConstants.ACTIVE_Y);
                        ci.setMngId(CurrentUserUtil.getUserId());
                        List<ChannelInfo> channelInfoList = channelInfoService.queryByVo(null, ci);
                        channelIdCondition = "";
                        if (CollectionUtils.isNotEmpty(channelInfoList)) {
                            for (ChannelInfo channelInfo : channelInfoList) {
                                channelIdCondition += channelInfo.getChannelId() + ",";
                            }
                            if (channelIdCondition.endsWith(",")) {
                                channelIdCondition = channelIdCondition.substring(0, channelIdCondition.length() - 1);
                            }
                            logStat.setChannelIdCondition(channelIdCondition);
                            list = logStatQueryService.queryByVo(page, logStat);
                        }
                    } else {
                        list = logStatQueryService.queryByVo(page, logStat);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(list)) {
                LogStat countLogStat = logStatQueryService.queryCountByVo(logStat);
                logStat.setModelName("合计:");
                list.add(countLogStat);
            }
            exportModel.setTitleMap(titleMap);
            exportModel.setDataList(list);
            String localFilePath = localDirCacheService.getExcelTempPath();
            ExportDataUtil.writeData(exportModel, new File(localFilePath));
            result.put("ret", true);
            result.put("path", localFilePath);
        } catch (Exception e) {
            result.put("ret", false);
            result.put("errorMsg", "文件导出失败，请重试或者联系管理员");
            LOGGER.error("exportData", e);
        }

        return result;
    }

    @RequestMapping(value = "/listProductInstallStat", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject listProductInstallStat(HttpServletRequest request) {
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
        String channelIdCondition = request.getParameter("channelIdCondition");
        ProductInstallStat productInstallStat = new ProductInstallStat();
        if (StringUtils.isNotEmpty(channelIdCondition)) {
            productInstallStat.setChannelIdCondition(channelIdCondition);
        }
        if (StringUtils.isNotEmpty(productId)) {
            productInstallStat.setProductId(Long.parseLong(productId));
        }
        if (StringUtils.isNotEmpty(ua)) {
            productInstallStat.setUa(ua.trim());
        }
        if (StringUtils.isNotEmpty(startDate)) {
            productInstallStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            productInstallStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        List<ProductInstallStat> list = productInstallStatService.queryByVo(page, productInstallStat);
        if (CollectionUtils.isNotEmpty(list)) {
            ProductInstallStat countProductInstallStat = productInstallStatService.queryCountByVo(productInstallStat);
            list.add(countProductInstallStat);
        } else {
            list = new ArrayList<ProductInstallStat>();
        }
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/exportProductData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject exportProductData(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            String ua = request.getParameter("ua");
            String productId = request.getParameter("productId");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String channelIdCondition = request.getParameter("channelIdCondition");
            ProductInstallStat productInstallStat = new ProductInstallStat();
            if (StringUtils.isNotEmpty(channelIdCondition)) {
                productInstallStat.setChannelIdCondition(channelIdCondition);
            }
            if (StringUtils.isNotEmpty(productId)) {
                productInstallStat.setProductId(Long.parseLong(productId));
            }
            if (StringUtils.isNotEmpty(ua)) {
                productInstallStat.setUa(ua.trim());
            }
            if (StringUtils.isNotEmpty(startDate)) {
                productInstallStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
            }
            if (StringUtils.isNotEmpty(endDate)) {
                productInstallStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
            }
            Pagination page = new Pagination();
            page.setCurrentPage(1);
            page.setPageSize(Integer.valueOf(GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.EXPORT_NUM_MAX)));
            List<ProductInstallStat> list = productInstallStatService.queryByVo(page, productInstallStat);
            if (CollectionUtils.isNotEmpty(list)) {
                ProductInstallStat countProductInstallStat = productInstallStatService.queryCountByVo(productInstallStat);
                list.add(countProductInstallStat);
            }
            BaseExportModel exportModel = new BaseExportModel();
            Map<String, String> titleMap = new LinkedHashMap<String, String>();
            titleMap.put("processDate", "日期");
            titleMap.put("modelName", "机型名称");
            titleMap.put("productName", "产品名称");
            titleMap.put("groupName", "渠道组织");
            titleMap.put("channelName", "渠道名称");
            titleMap.put("installTotalNum", "装机数量");
            titleMap.put("totalNum", "装机到达数量");
            exportModel.setTitleMap(titleMap);
            exportModel.setDataList(list);
            String localFilePath = localDirCacheService.getExcelTempPath();
            ExportDataUtil.writeData(exportModel, new File(localFilePath));
            result.put("ret", true);
            result.put("path", localFilePath);
        } catch (Exception e) {
            result.put("ret", false);
            result.put("errorMsg", "文件导出失败，请重试或者联系管理员");
            LOGGER.error("exportData", e);
        }

        return result;
    }

    @RequestMapping(value = "/listImei", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject listImei(HttpServletRequest request) {
        //查询条件
        String processDate = request.getParameter("processDate");
        String channelId = request.getParameter("channelId");
        String channelName = request.getParameter("channelName");
        String groupId = request.getParameter("groupId");
        String groupName = request.getParameter("groupName");
        String deviceCode = request.getParameter("deviceCode");
        String modelName = request.getParameter("modelName");
        String ua = request.getParameter("ua");
        String productId = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String queryType = request.getParameter("queryType");
        StatImeiRequest statImeiRequest = this.getStatImeiRequestByQueryType(queryType, false);
        if (StringUtils.isNotEmpty(processDate)) {
            statImeiRequest.setProcessDate(new Date(Long.parseLong(processDate)));
        }
        if (StringUtils.isNotEmpty(channelId)) {
            statImeiRequest.setChannelId(Long.parseLong(channelId));
        }
        if (StringUtils.isNotEmpty(productId)) {
            statImeiRequest.setProductId(Long.parseLong(productId));
        }
        if (StringUtils.isNotEmpty(groupId)) {
            statImeiRequest.setGroupId(Long.parseLong(groupId));
        }
        statImeiRequest.setDeviceCode(deviceCode);
        statImeiRequest.setChannelName(channelName);
        statImeiRequest.setModelName(modelName);
        statImeiRequest.setProductName(productName);
        statImeiRequest.setGroupName(groupName);
        statImeiRequest.setUa(ua);
        List<StatImeiResult> list = statImeiService.queryImeiList(statImeiRequest);
        JSONObject result = new JSONObject();
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/exportImei", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject exportImei(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            //查询条件
            String processDate = request.getParameter("processDate");
            String channelId = request.getParameter("channelId");
            String channelName = request.getParameter("channelName");
            String groupId = request.getParameter("groupId");
            String groupName = request.getParameter("groupName");
            String deviceCode = request.getParameter("deviceCode");
            String modelName = request.getParameter("modelName");
            String ua = request.getParameter("ua");
            String productId = request.getParameter("productId");
            String productName = request.getParameter("productName");
            String queryType = request.getParameter("queryType");
            StatImeiRequest statImeiRequest = this.getStatImeiRequestByQueryType(queryType, true);
            if (StringUtils.isNotEmpty(processDate)) {
                statImeiRequest.setProcessDate(new Date(Long.parseLong(processDate)));
            }
            if (StringUtils.isNotEmpty(channelId)) {
                statImeiRequest.setChannelId(Long.parseLong(channelId));
            }
            if (StringUtils.isNotEmpty(productId)) {
                statImeiRequest.setProductId(Long.parseLong(productId));
            }
            if (StringUtils.isNotEmpty(groupId)) {
                statImeiRequest.setGroupId(Long.parseLong(groupId));
            }
            statImeiRequest.setDeviceCode(deviceCode);
            statImeiRequest.setChannelName(channelName);
            statImeiRequest.setModelName(modelName);
            statImeiRequest.setProductName(productName);
            statImeiRequest.setGroupName(groupName);
            statImeiRequest.setUa(ua);
            List<StatImeiResult> list = statImeiService.queryImeiList(statImeiRequest);

            BaseExportModel exportModel = new BaseExportModel();
            Map<String, String> titleMap = new LinkedHashMap<String, String>();
            String exportType = request.getParameter("exportType");
            if ("1".equals(exportType)) {
                titleMap.put("processDate", "日期");
                titleMap.put("modelName", "机型名称");
                titleMap.put("channelName", "仓库名称");
                titleMap.put("imei", "IMEI号");
            } else if ("2".equals(exportType)) {
                titleMap.put("processDate", "日期");
                titleMap.put("modelName", "机型名称");
                titleMap.put("channelName", "仓库名称");
                titleMap.put("deviceCode", "设备编码");
                titleMap.put("imei", "IMEI号");
            } else if ("3".equals(exportType)) {
                titleMap.put("processDate", "日期");
                titleMap.put("modelName", "机型名称");
                titleMap.put("productName", "产品名称");
                titleMap.put("groupName", "渠道组织");
                titleMap.put("imei", "IMEI号");
            }
            exportModel.setTitleMap(titleMap);
            exportModel.setDataList(list);
            String localFilePath = localDirCacheService.getExcelTempPath();
            ExportDataUtil.writeData(exportModel, new File(localFilePath));
            result.put("ret", true);
            result.put("path", localFilePath);
        } catch (Exception e) {
            result.put("ret", false);
            result.put("errorMsg", "文件导出失败，请重试或者联系管理员");
            LOGGER.error("exportData", e);
        }
        return result;
    }

    private StatImeiRequest getStatImeiRequestByQueryType(String queryType, Boolean expertType) {
        StatImeiRequest statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Install, expertType);
        if ("1".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Install, expertType);
        } else if ("3".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Install_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Total);
        } else if ("4".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Install_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Valid);
        } else if ("5".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Install_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Invalid);
        } else if ("6".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Install_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Replace);
        } else if ("7".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Install_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Uninstall);
        } else if ("8".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Install_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.UnAndRe);
        } else if ("11".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Install, expertType);
        } else if ("12".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Install_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Total);
        } else if ("13".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Install_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Valid);
        } else if ("14".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Install_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Invalid);
        } else if ("15".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Install_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Replace);
        } else if ("16".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Install_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Uninstall);
        } else if ("17".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Install_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.UnAndRe);
        } else if ("21".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Total);
        } else if ("22".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Valid);
        } else if ("23".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Invalid);
        } else if ("24".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Replace);
        } else if ("25".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Uninstall);
        } else if ("26".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.UnAndRe);
        } else if ("31".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Total);
        } else if ("32".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Valid);
        } else if ("33".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Invalid);
        } else if ("34".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Replace);
        } else if ("35".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.Uninstall);
        } else if ("36".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Arrive, expertType);
            statImeiRequest.setActive(QueryActive.UnAndRe);
        }
        return statImeiRequest;
    }


    @RequestMapping(value = "/listStoreLogArriveStat", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject listStoreLogArriveStat(HttpServletRequest request) {
        /**分页*/
        String pageNum = request.getParameter("page");
        String pageSize = request.getParameter("rows");
        Pagination page = new Pagination();
        if (!StringUtils.isEmpty(pageNum)) page.setCurrentPage(Integer.valueOf(pageNum));
        if (!StringUtils.isEmpty(pageSize)) page.setPageSize(Integer.valueOf(pageSize));
        //查询条件
        String ua = request.getParameter("ua");
        String channelId = request.getParameter("channelId");
        String channelIdCondition = request.getParameter("channelIdCondition");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String groupId = request.getParameter("groupId");
        LogArriveStat logArriveStat = new LogArriveStat();
        if (StringUtils.isNotEmpty(groupId)) {
            logArriveStat.setGroupId(Long.parseLong(groupId));
        }
        if (StringUtils.isNotEmpty(channelId)) {
            logArriveStat.setChannelId(Long.parseLong(channelId));
        }
        if (StringUtils.isNotEmpty(ua)) {
            logArriveStat.setUa(ua.trim());
        }
        if (StringUtils.isNotEmpty(startDate)) {
            logArriveStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            logArriveStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        if (StringUtils.isNotEmpty(channelIdCondition)) {
            logArriveStat.setChannelIdCondition(channelIdCondition);
        }
        List<LogArriveStat> list = logArriveStatService.queryByVo(page, logArriveStat);
        if (CollectionUtils.isNotEmpty(list)) {
            LogArriveStat countLogArriveStat = logArriveStatService.queryCountByVo(logArriveStat);
            list.add(countLogArriveStat);
        }
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/listLogArriveStat", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject listLogArriveStat(HttpServletRequest request) {
        /**分页*/
        String pageNum = request.getParameter("page");
        String pageSize = request.getParameter("rows");
        Pagination page = new Pagination();
        if (!StringUtils.isEmpty(pageNum)) page.setCurrentPage(Integer.valueOf(pageNum));
        if (!StringUtils.isEmpty(pageSize)) page.setPageSize(Integer.valueOf(pageSize));
        //查询条件
        String ua = request.getParameter("ua");
        String channelId = request.getParameter("channelId");
        String channelIdCondition = request.getParameter("channelIdCondition");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String groupId = request.getParameter("groupId");
        LogArriveStat logArriveStat = new LogArriveStat();
        if (StringUtils.isNotEmpty(groupId)) {
            logArriveStat.setGroupId(Long.parseLong(groupId));
        }
        if (StringUtils.isNotEmpty(channelId)) {
            logArriveStat.setChannelId(Long.parseLong(channelId));
        }
        if (StringUtils.isNotEmpty(ua)) {
            logArriveStat.setUa(ua.trim());
        }
        if (StringUtils.isNotEmpty(startDate)) {
            logArriveStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            logArriveStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
        }

        List<LogArriveStat> list = new ArrayList<LogArriveStat>();
        if (StringUtils.isNotEmpty(channelIdCondition)) {
            logArriveStat.setChannelIdCondition(channelIdCondition);
            list = logArriveStatService.queryByVo(page, logArriveStat);
        } else {//如果是地包或其他渠道的负责人登录，则进行数据过滤
            if ((JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId) || JcywConstants.CHANNEL_GROUP_QT_ID_3.toString().equals(groupId)) && CurrentUserUtil.isManager()) {
                ChannelInfo ci = new ChannelInfo();
                ci.setGroupId(Long.parseLong(groupId));
                ci.setActive(JcywConstants.ACTIVE_Y);
                ci.setMngId(CurrentUserUtil.getUserId());
                List<ChannelInfo> channelInfoList = channelInfoService.queryByVo(null, ci);
                channelIdCondition = "";
                if (CollectionUtils.isNotEmpty(channelInfoList)) {
                    for (ChannelInfo channelInfo : channelInfoList) {
                        channelIdCondition += channelInfo.getChannelId() + ",";
                    }
                    if (channelIdCondition.endsWith(",")) {
                        channelIdCondition = channelIdCondition.substring(0, channelIdCondition.length() - 1);
                    }
                    logArriveStat.setChannelIdCondition(channelIdCondition);
                    list = logArriveStatService.queryByVo(page, logArriveStat);
                }
            } else {
                list = logArriveStatService.queryByVo(page, logArriveStat);
            }
        }
        if (CollectionUtils.isNotEmpty(list)) {
            LogArriveStat countLogArriveStat = logArriveStatService.queryCountByVo(logArriveStat);
            list.add(countLogArriveStat);
        }
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/exportArriveData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject exportArriveData(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            String ua = request.getParameter("ua");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String groupId = request.getParameter("groupId");
            String channelId = request.getParameter("channelId");
            String channelIdCondition = request.getParameter("channelIdCondition");
            LogArriveStat logArriveStat = new LogArriveStat();
            if (StringUtils.isNotEmpty(groupId)) {
                logArriveStat.setGroupId(Long.parseLong(groupId));
            }
            if (StringUtils.isNotEmpty(channelId)) {
                logArriveStat.setChannelId(Long.parseLong(channelId));
            }
            if (StringUtils.isNotEmpty(ua)) {
                logArriveStat.setUa(ua.trim());
            }
            if (StringUtils.isNotEmpty(startDate)) {
                logArriveStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
            }
            if (StringUtils.isNotEmpty(endDate)) {
                logArriveStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
            }
            if (StringUtils.isNotEmpty(channelIdCondition)) {
                logArriveStat.setChannelIdCondition(channelIdCondition);
            }
            String exportType = request.getParameter("exportType");
            BaseExportModel exportModel = new BaseExportModel();
            Map<String, String> titleMap = new LinkedHashMap<String, String>();
            titleMap.put("processDate", "日期");
            titleMap.put("modelName", "机型全称");
            titleMap.put("channelName", "仓库名称");

            List<LogArriveStat> list = new ArrayList<LogArriveStat>();
            Pagination page = new Pagination();
            page.setCurrentPage(1);
            page.setPageSize(Integer.valueOf(GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.EXPORT_NUM_MAX)));
            if ("1".equals(exportType)) {//天音渠道装机查询
                titleMap.put("totalNum", "累计到达数量");
                titleMap.put("validNum", "有效到达数量");
                titleMap.put("invalidNum", "无效到达数量");
                titleMap.put("replaceNum", "替换数量");
                titleMap.put("uninstallNum", "卸载数量");
                titleMap.put("unAndReNum", "卸载并替换数量");
                list = logArriveStatService.queryByVo(page, logArriveStat);
            } else if ("2".equals(exportType)) {//地包渠道装机查询
                titleMap.put("totalNum", "累计到达数量");
                titleMap.put("validNum", "有效到达数量");
                titleMap.put("invalidNum", "无效到达数量");
                titleMap.put("replaceNum", "替换数量");
                titleMap.put("uninstallNum", "卸载数量");
                titleMap.put("unAndReNum", "卸载并替换数量");
                if (StringUtils.isNotEmpty(channelIdCondition)) {
                    logArriveStat.setChannelIdCondition(channelIdCondition);
                    list = logArriveStatService.queryByVo(page, logArriveStat);
                } else {//如果是地包渠道的负责人登录，则进行数据过滤
                    if ((JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId)) && CurrentUserUtil.isManager()) {
                        ChannelInfo ci = new ChannelInfo();
                        ci.setGroupId(Long.parseLong(groupId));
                        ci.setActive(JcywConstants.ACTIVE_Y);
                        ci.setMngId(CurrentUserUtil.getUserId());
                        List<ChannelInfo> channelInfoList = channelInfoService.queryByVo(null, ci);
                        channelIdCondition = "";
                        if (CollectionUtils.isNotEmpty(channelInfoList)) {
                            for (ChannelInfo channelInfo : channelInfoList) {
                                channelIdCondition += channelInfo.getChannelId() + ",";
                            }
                            if (channelIdCondition.endsWith(",")) {
                                channelIdCondition = channelIdCondition.substring(0, channelIdCondition.length() - 1);
                            }
                            logArriveStat.setChannelIdCondition(channelIdCondition);
                            list = logArriveStatService.queryByVo(page, logArriveStat);
                        }
                    } else {
                        list = logArriveStatService.queryByVo(page, logArriveStat);
                    }
                }
            } else if ("3".equals(exportType)) {//其他渠道装机查询
                titleMap.put("totalNum", "累计到达数量");
                titleMap.put("validNum", "有效到达数量");
                titleMap.put("invalidNum", "无效到达数量");
                titleMap.put("replaceNum", "替换数量");
                titleMap.put("uninstallNum", "卸载数量");
                titleMap.put("unAndReNum", "卸载并替换数量");
                if (StringUtils.isNotEmpty(channelIdCondition)) {
                    logArriveStat.setChannelIdCondition(channelIdCondition);
                    list = logArriveStatService.queryByVo(page, logArriveStat);
                } else {//如果是其他渠道的负责人登录，则进行数据过滤
                    if ((JcywConstants.CHANNEL_GROUP_QT_ID_3.toString().equals(groupId)) && CurrentUserUtil.isManager()) {
                        ChannelInfo ci = new ChannelInfo();
                        ci.setGroupId(Long.parseLong(groupId));
                        ci.setActive(JcywConstants.ACTIVE_Y);
                        ci.setMngId(CurrentUserUtil.getUserId());
                        List<ChannelInfo> channelInfoList = channelInfoService.queryByVo(null, ci);
                        channelIdCondition = "";
                        if (CollectionUtils.isNotEmpty(channelInfoList)) {
                            for (ChannelInfo channelInfo : channelInfoList) {
                                channelIdCondition += channelInfo.getChannelId() + ",";
                            }
                            if (channelIdCondition.endsWith(",")) {
                                channelIdCondition = channelIdCondition.substring(0, channelIdCondition.length() - 1);
                            }
                            logArriveStat.setChannelIdCondition(channelIdCondition);
                            list = logArriveStatService.queryByVo(page, logArriveStat);
                        }
                    } else {
                        list = logArriveStatService.queryByVo(page, logArriveStat);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(list)) {
                LogArriveStat countLogArriveStat = logArriveStatService.queryCountByVo(logArriveStat);
                logArriveStat.setModelName("合计:");
                list.add(countLogArriveStat);
            }
            exportModel.setTitleMap(titleMap);
            exportModel.setDataList(list);
            String localFilePath = localDirCacheService.getExcelTempPath();
            ExportDataUtil.writeData(exportModel, new File(localFilePath));
            result.put("ret", true);
            result.put("path", localFilePath);
        } catch (Exception e) {
            result.put("ret", false);
            result.put("errorMsg", "文件导出失败，请重试或者联系管理员");
            LOGGER.error("exportData", e);
        }

        return result;
    }

    @RequestMapping(value = "/listProductArriveStat", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject listProductArriveStat(HttpServletRequest request) {
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
        String channelIdCondition = request.getParameter("channelIdCondition");
        ProductArriveStat productArriveStat = new ProductArriveStat();
        if (StringUtils.isNotEmpty(channelIdCondition)) {
            productArriveStat.setChannelIdCondition(channelIdCondition);
        }
        if (StringUtils.isNotEmpty(productId)) {
            productArriveStat.setProductId(Long.parseLong(productId));
        }
        if (StringUtils.isNotEmpty(ua)) {
            productArriveStat.setUa(ua.trim());
        }
        if (StringUtils.isNotEmpty(startDate)) {
            productArriveStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            productArriveStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        List<ProductArriveStat> list = productArriveStatService.queryByVo(page, productArriveStat);
        if (CollectionUtils.isNotEmpty(list)) {
            ProductArriveStat countProductArriveStat = productArriveStatService.queryCountByVo(productArriveStat);
            list.add(countProductArriveStat);
        } else {
            list = new ArrayList<ProductArriveStat>();
        }
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/exportProductArriveData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject exportProductArriveData(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            String ua = request.getParameter("ua");
            String productId = request.getParameter("productId");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String channelIdCondition = request.getParameter("channelIdCondition");
            ProductInstallStat productInstallStat = new ProductInstallStat();
            if (StringUtils.isNotEmpty(channelIdCondition)) {
                productInstallStat.setChannelIdCondition(channelIdCondition);
            }
            if (StringUtils.isNotEmpty(productId)) {
                productInstallStat.setProductId(Long.parseLong(productId));
            }
            if (StringUtils.isNotEmpty(ua)) {
                productInstallStat.setUa(ua.trim());
            }
            if (StringUtils.isNotEmpty(startDate)) {
                productInstallStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
            }
            if (StringUtils.isNotEmpty(endDate)) {
                productInstallStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
            }
            Pagination page = new Pagination();
            page.setCurrentPage(1);
            page.setPageSize(Integer.valueOf(GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.EXPORT_NUM_MAX)));
            List<ProductInstallStat> list = productInstallStatService.queryByVo(page, productInstallStat);
            if (CollectionUtils.isNotEmpty(list)) {
                ProductInstallStat countProductInstallStat = productInstallStatService.queryCountByVo(productInstallStat);
                list.add(countProductInstallStat);
            }
            BaseExportModel exportModel = new BaseExportModel();
            Map<String, String> titleMap = new LinkedHashMap<String, String>();
            titleMap.put("processDate", "日期");
            titleMap.put("modelName", "机型名称");
            titleMap.put("productName", "产品名称");
            titleMap.put("groupName", "渠道组织");
            titleMap.put("channelName", "渠道名称");
            titleMap.put("totalNum", "累计到达数量");
            titleMap.put("validNum", "有效到达数量");
            titleMap.put("invalidNum", "无效到达数量");
            titleMap.put("replaceNum", "替换数量");
            titleMap.put("uninstallNum", "卸载数量");
            titleMap.put("unAndReNum", "卸载并替换数量");
            exportModel.setTitleMap(titleMap);
            exportModel.setDataList(list);
            String localFilePath = localDirCacheService.getExcelTempPath();
            ExportDataUtil.writeData(exportModel, new File(localFilePath));
            result.put("ret", true);
            result.put("path", localFilePath);
        } catch (Exception e) {
            result.put("ret", false);
            result.put("errorMsg", "文件导出失败，请重试或者联系管理员");
            LOGGER.error("exportData", e);
        }

        return result;
    }
}