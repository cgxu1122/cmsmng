package com.ifhz.hzfmng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.commons.util.ExportDataUtil;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.po.PartnerInfo;
import com.ifhz.core.po.ProductStat;
import com.ifhz.core.service.auth.UserService;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.service.channel.ChannelInfoService;
import com.ifhz.core.service.export.model.BaseExportModel;
import com.ifhz.core.service.imei.StatImeiQueryService;
import com.ifhz.core.service.imei.bean.ImeiQueryType;
import com.ifhz.core.service.imei.bean.StatImeiRequest;
import com.ifhz.core.service.imei.bean.StatImeiResult;
import com.ifhz.core.service.partner.PartnerInfoService;
import com.ifhz.core.service.stat.LogStatQueryService;
import com.ifhz.core.service.stat.ProductStatQueryService;
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
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/hzfmng/partnerQuery")
public class PartnerQueryController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerQueryController.class);
    @Autowired
    private ChannelInfoService channelInfoService;
    @Autowired
    private PartnerInfoService partnerInfoService;
    @Autowired
    private LogStatQueryService logStatQueryService;
    @Autowired
    private ProductStatQueryService productStatQueryService;
    @Autowired
    private StatImeiQueryService statImeiQueryService;
    @Autowired
    private LocalDirCacheService localDirCacheService;
    @Autowired
    private UserService userService;


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
    public JSONObject listLogStat(HttpServletRequest request) {
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
        ChannelInfo channelInfo = channelInfoService.getChannelInfoByUserId(CurrentUserUtil.getUserId());
        if (channelInfo != null) {
            logStat.setChannelId(channelInfo.getChannelId());
        }
        if (JcywConstants.CHANNEL_GROUP_LW_ID_4 == logStat.getGroupId()) {
            //如果是劳务渠道合作方查询
            String channelId = request.getParameter("channelId");
            if (StringUtils.isNotEmpty(channelId)) {
                logStat.setChannelId(Long.parseLong(channelId));
            }
            if (channelInfo != null) {
                logStat.setLaowuId(channelInfo.getChannelId());
            }
            logStat.setGroupId(null);
        }
        List<LogStat> list = logStatQueryService.queryByVo(page, logStat);
        if (CollectionUtils.isNotEmpty(list)) {
            LogStat countLogStat = logStatQueryService.queryCountByVo(logStat);
            list.add(countLogStat);
            if (channelInfo != null) {
                for (LogStat logStat1 : list) {
                    logStat1.setQueryImeiSource(channelInfo.getQueryImeiSource());
                }
            }
        }
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }


    @RequestMapping(value = "/exportData", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject exportData(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        String ua = request.getParameter("ua");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String groupId = request.getParameter("groupId");
        try {
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
            ChannelInfo channelInfo = channelInfoService.getChannelInfoByUserId(CurrentUserUtil.getUserId());
            if (channelInfo != null) {
                logStat.setChannelId(channelInfo.getChannelId());
            }
            if (JcywConstants.CHANNEL_GROUP_LW_ID_4 == logStat.getGroupId()) {
                //如果是劳务渠道合作方查询
                String channelId = request.getParameter("channelId");
                if (StringUtils.isNotEmpty(channelId)) {
                    logStat.setChannelId(Long.parseLong(channelId));
                }
                if (channelInfo != null) {
                    logStat.setLaowuId(channelInfo.getChannelId());
                }
                logStat.setGroupId(null);
            }
            List<LogStat> list = logStatQueryService.queryByVo(null, logStat);
            if (CollectionUtils.isNotEmpty(list)) {
                LogStat countLogStat = logStatQueryService.queryCountByVo(logStat);
                list.add(countLogStat);
            }
            BaseExportModel exportModel = new BaseExportModel();
            Map<String, String> titleMap = new LinkedHashMap<String, String>();
            titleMap.put("processDate", "日期");
            titleMap.put("deviceCode", "设备编码");
            titleMap.put("channelName", "仓库名称");
            titleMap.put("modelName", "机型名称");
            titleMap.put("devicePrsDayNum", "装机数量");
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
        String productId = request.getParameter("productId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        ProductStat productStat = new ProductStat();
        PartnerInfo partnerInfo = partnerInfoService.getPartnerInfoByUserId(CurrentUserUtil.getUserId());
        if (partnerInfo != null) {
            productStat.setPartnerId(partnerInfo.getPartnerId());
        }
        if (StringUtils.isNotEmpty(productId)) {
            productStat.setProductId(Long.parseLong(productId));
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
            if (partnerInfo != null) {
                for (ProductStat productStat1 : list) {
                    if (!userService.checkAdminMng(CurrentUserUtil.getUserId())) {
                        productStat1.setQueryImeiSource(partnerInfo.getQueryImeiSource());
                    } else {
                        productStat1.setQueryImeiSource("Y");
                    }
                }
            }
        }
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/exportProductData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject exportProductData(HttpServletRequest request, HttpServletResponse response) {
        String productId = request.getParameter("productId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String groupId = request.getParameter("groupId");
        JSONObject result = new JSONObject();
        try {
            ProductStat productStat = new ProductStat();
            PartnerInfo partnerInfo = partnerInfoService.getPartnerInfoByUserId(CurrentUserUtil.getUserId());
            if (partnerInfo != null) {
                productStat.setPartnerId(partnerInfo.getPartnerId());
            }
            if (StringUtils.isNotEmpty(productId)) {
                productStat.setProductId(Long.parseLong(productId));
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
            titleMap.put("productPrsDayNum", "装机数量");
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
        JSONObject result = new JSONObject();
        //查询条件
        String processDate = request.getParameter("processDate");
        String channelId = request.getParameter("channelId");
        String productId = request.getParameter("productId");
        String groupId = request.getParameter("groupId");
        String channelName = request.getParameter("channelName");
        String deviceCode = request.getParameter("deviceCode");
        String modelName = request.getParameter("modelName");
        String ua = request.getParameter("ua");
        StatImeiRequest statImeiRequest = new StatImeiRequest(ImeiQueryType.Day_Device_Process);
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
        statImeiRequest.setUa(ua);
        List<StatImeiResult> list = statImeiQueryService.queryImeiListFromLog(statImeiRequest);
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
            String productId = request.getParameter("productId");
            String groupId = request.getParameter("groupId");
            String ua = request.getParameter("ua");
            String deviceCode = request.getParameter("deviceCode");
            String modelName = request.getParameter("modelName");
            String userType = request.getParameter("userType");
            if ("lw".equals(userType)) {
                if (!userService.checkAdminMng(CurrentUserUtil.getUserId())) {
                    ChannelInfo channelInfo = channelInfoService.getChannelInfoByUserId(CurrentUserUtil.getUserId());
                    if (JcywConstants.BASE_CONSTANT_N.equals(channelInfo.getQueryImeiSource())) {
                        result.put("ret", false);
                        result.put("errorMsg", "对不起，你没有导出此imei的权限！");
                        return result;
                    }
                }

            } else if ("cp".equals(userType)) {
                if (!userService.checkAdminMng(CurrentUserUtil.getUserId())) {
                    PartnerInfo partnerInfo = partnerInfoService.getPartnerInfoByUserId(CurrentUserUtil.getUserId());
                    if (JcywConstants.BASE_CONSTANT_N.equals(partnerInfo.getExportImeiSource())) {
                        result.put("ret", false);
                        result.put("errorMsg", "对不起，你没有导出此imei的权限！");
                        return result;
                    }
                }
            } else {
                if (!userService.checkAdminMng(CurrentUserUtil.getUserId())) {
                    result.put("ret", false);
                    result.put("errorMsg", "对不起，你没有导出此imei的权限！");
                    return result;
                }
            }
            StatImeiRequest statImeiRequest = new StatImeiRequest(ImeiQueryType.Day_Device_Process);
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
            statImeiRequest.setUa(ua);
            statImeiRequest.setDeviceCode(deviceCode);
            statImeiRequest.setModelName(modelName);
            List<StatImeiResult> list = statImeiQueryService.queryImeiListFromLog(statImeiRequest);
            BaseExportModel exportModel = new BaseExportModel();
            Map<String, String> titleMap = new LinkedHashMap<String, String>();
            titleMap.put("processDate", "日期");
            titleMap.put("modelName", "机型名称");
            titleMap.put("imei", "IMEI号");
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