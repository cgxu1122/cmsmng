package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.commons.util.ExportDataUtil;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.po.ProductStat;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.service.channel.ChannelInfoService;
import com.ifhz.core.service.export.model.BaseExportModel;
import com.ifhz.core.service.imei.StatImeiQueryService;
import com.ifhz.core.service.imei.bean.ImeiQueryType;
import com.ifhz.core.service.imei.bean.QueryActive;
import com.ifhz.core.service.imei.bean.StatImeiRequest;
import com.ifhz.core.service.imei.bean.StatImeiResult;
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
import java.io.File;
import java.util.*;

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
    @Autowired
    private LocalDirCacheService localDirCacheService;
    @Autowired
    private StatImeiQueryService statImeiQueryService;

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
            String exportType = request.getParameter("exportType");
            BaseExportModel exportModel = new BaseExportModel();
            Map<String, String> titleMap = new LinkedHashMap<String, String>();
            titleMap.put("processDate", "日期");
            titleMap.put("modelName", "机型全称");
            titleMap.put("channelName", "仓库名称");
            List<LogStat> list = new ArrayList<LogStat>();
            if ("1".equals(exportType)) {//按仓库查询
                titleMap.put("devicePrsDayNum", "装机数量");
                titleMap.put("deviceUpdDayNum", "装机到达数量");
                titleMap.put("prsActiveTotalNum", "累计到达数量");
                titleMap.put("prsActiveValidNum", "有效到达数量");
                titleMap.put("prsActiveInvalidNum", "无效到达数量");
                titleMap.put("prsInvalidReplaceNum", "替换数量");
                titleMap.put("prsInvalidUninstallNum", "卸载数量");
                list = logStatQueryService.querySumByVo(null, logStat);
            } else if ("2".equals(exportType)) {//按渠道查询加工数据
                titleMap.put("deviceCode", "设备编码");
                titleMap.put("devicePrsDayNum", "装机数量");
                list = logStatQueryService.queryByVo(null, logStat);
            } else if ("3".equals(exportType)) {//按渠道查询到达数据
                titleMap.put("deviceCode", "设备编码");
                titleMap.put("deviceUpdDayNum", "装机到达数量");
                titleMap.put("prsActiveTotalNum", "累计到达数量");
                titleMap.put("prsActiveValidNum", "有效到达数量");
                titleMap.put("prsActiveInvalidNum", "无效到达数量");
                titleMap.put("prsInvalidReplaceNum", "替换数量");
                titleMap.put("prsInvalidUninstallNum", "卸载数量");
                list = logStatQueryService.queryByVo(null, logStat);
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

    @RequestMapping(value = "/exportProductData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject exportProductData(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
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
        StatImeiRequest statImeiRequest = this.getStatImeiRequestByQueryType(queryType);
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
        List<StatImeiResult> list;
        if (StringUtils.isNotEmpty(productId)) {
            list = statImeiQueryService.queryImeiListFromProduct(statImeiRequest);
        } else {
            list = statImeiQueryService.queryImeiListFromLog(statImeiRequest);
        }
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
            StatImeiRequest statImeiRequest = this.getStatImeiRequestByQueryType(queryType);
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
            List<StatImeiResult> list;
            if (StringUtils.isNotEmpty(productId)) {
                list = statImeiQueryService.queryImeiListFromProduct(statImeiRequest);
            } else {
                list = statImeiQueryService.queryImeiListFromLog(statImeiRequest);
            }
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

    private StatImeiRequest getStatImeiRequestByQueryType(String queryType) {
        StatImeiRequest statImeiRequest = new StatImeiRequest(ImeiQueryType.Day_Device_Process);
        if ("1".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Day_Device_Process);
        } else if ("2".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Day_Device_Upload);
        } else if ("3".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Day_Counter_Upload);
            statImeiRequest.setActive(QueryActive.Total);
        } else if ("4".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Day_Counter_Upload);
            statImeiRequest.setActive(QueryActive.Valid);
        } else if ("5".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Day_Counter_Upload);
            statImeiRequest.setActive(QueryActive.Invalid);
        } else if ("6".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Day_Counter_Upload);
            statImeiRequest.setActive(QueryActive.Replace);
        } else if ("7".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Day_Counter_Upload);
            statImeiRequest.setActive(QueryActive.Uninstall);
        }
        return statImeiRequest;
    }
}