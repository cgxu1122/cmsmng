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
import com.ifhz.core.po.stat.LogArriveStatTemp;
import com.ifhz.core.po.stat.ProductArriveStat;
import com.ifhz.core.po.stat.ProductInstallStat;
import com.ifhz.core.service.auther.SysUserService;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.service.channel.ChannelInfoService;
import com.ifhz.core.service.export.model.BaseExportModel;
import com.ifhz.core.service.imei.StatImeiService;
import com.ifhz.core.service.imei.bean.ImeiQueryType;
import com.ifhz.core.service.imei.bean.QueryActive;
import com.ifhz.core.service.imei.bean.StatImeiRequest;
import com.ifhz.core.service.imei.bean.StatImeiResult;
import com.ifhz.core.service.partner.PartnerInfoService;
import com.ifhz.core.service.product.ProductInfoService;
import com.ifhz.core.service.stat.LogArriveStatTempService;
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
    private LogArriveStatTempService logArriveStatTempService;
    @Autowired
    private ProductArriveStatService productArriveStatService;
    @Autowired
    private ProductInstallStatService productInstallStatService;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private StatImeiService statImeiService;
    @Autowired
    private LocalDirCacheService localDirCacheService;
    @Autowired
    private SysUserService sysUserService;


    @RequestMapping("/indexTY")
    public ModelAndView indexTY(HttpServletRequest request) {
        return new ModelAndView("partnerQuery/indexTY");
    }

    @RequestMapping("/indexDB")
    public ModelAndView indexDB(HttpServletRequest request) {
        return new ModelAndView("partnerQuery/indexDB");
    }

    @RequestMapping("/indexDBArrive")
    public ModelAndView indexDBArrive(HttpServletRequest request) {
        return new ModelAndView("partnerQuery/indexDBArrive");
    }

    @RequestMapping("/indexQTArrive")
    public ModelAndView indexQTArrive(HttpServletRequest request) {
        return new ModelAndView("partnerQuery/indexQTArrive");
    }

    @RequestMapping("/indexCP")
    public ModelAndView indexCP(HttpServletRequest request) {
        return new ModelAndView("partnerQuery/indexCP");
    }

    @RequestMapping("/indexCPArrive")
    public ModelAndView indexCPArrive(HttpServletRequest request) {
        return new ModelAndView("partnerQuery/indexCPArrive");
    }

    @RequestMapping("/indexLW")
    public ModelAndView indexLW(HttpServletRequest request) {
        return new ModelAndView("partnerQuery/indexLW");
    }

    @RequestMapping("/indexQT")
    public ModelAndView indexQT(HttpServletRequest request) {
        return new ModelAndView("partnerQuery/indexQT");
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
            logStat.setChannelId(null);
            String channelId = request.getParameter("channelId");
            if (StringUtils.isNotEmpty(channelId)) {
                logStat.setChannelId(Long.parseLong(channelId));
            }
            if (channelInfo != null) {
                logStat.setLaowuId(channelInfo.getChannelId());
            }
            logStat.setGroupId(null);
        }
        List<LogStat> list = logStatQueryService.queryHzfListByVo(page, logStat);
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
            List<LogStat> list = logStatQueryService.queryHzfListByVo(null, logStat);
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
            if (JcywConstants.CHANNEL_GROUP_QT_ID_3 == logStat.getGroupId()) {
                titleMap.remove("deviceCode");
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
        String productId = request.getParameter("productId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        ProductInstallStat productStat = new ProductInstallStat();
        if (StringUtils.isNotEmpty(productId)) {
            productStat.setProductId(Long.parseLong(productId));
        }
        if (StringUtils.isNotEmpty(startDate)) {
            productStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            productStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        PartnerInfo partnerInfo = partnerInfoService.getPartnerInfoByUserId(CurrentUserUtil.getUserId());
        if (partnerInfo != null) {
            productStat.setPartnerId(partnerInfo.getPartnerId());
            Date sdate = DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT);
            Date maxQueryDate = productInfoService.getMaxQueryDateByPartnerId(partnerInfo.getPartnerId());
            if (maxQueryDate != null) {
                if (sdate == null) {
                    productStat.setStartDate(maxQueryDate);
                } else if (sdate != null) {
                    if (maxQueryDate.after(sdate)) {
                        productStat.setStartDate(maxQueryDate);
                    } else {
                        productStat.setStartDate(maxQueryDate);
                    }
                }
            }
        }
        List<ProductInstallStat> list = productInstallStatService.querySumByVo(page, productStat);
        if (CollectionUtils.isNotEmpty(list)) {
            ProductInstallStat countProductStat = productInstallStatService.queryCountByVo(productStat);
            list.add(countProductStat);
            if (partnerInfo != null) {
                for (ProductInstallStat productStat1 : list) {
                    if (!sysUserService.checkAdminMng(CurrentUserUtil.getUserId())) {
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
            ProductInstallStat productStat = new ProductInstallStat();
            if (StringUtils.isNotEmpty(productId)) {
                productStat.setProductId(Long.parseLong(productId));
            }
            if (StringUtils.isNotEmpty(startDate)) {
                productStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
            }
            if (StringUtils.isNotEmpty(endDate)) {
                productStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
            }
            PartnerInfo partnerInfo = partnerInfoService.getPartnerInfoByUserId(CurrentUserUtil.getUserId());
            if (partnerInfo != null) {
                productStat.setPartnerId(partnerInfo.getPartnerId());
                Date sdate = DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT);
                Date maxQueryDate = productInfoService.getMaxQueryDateByPartnerId(partnerInfo.getPartnerId());
                if (maxQueryDate != null) {
                    if (sdate == null) {
                        productStat.setStartDate(maxQueryDate);
                    } else if (sdate != null && maxQueryDate.after(sdate)) {
                        productStat.setStartDate(maxQueryDate);
                    }
                }
            }
            List<ProductInstallStat> list = productInstallStatService.querySumByVo(null, productStat);
            if (CollectionUtils.isNotEmpty(list)) {
                ProductInstallStat countProductStat = productInstallStatService.queryCountByVo(productStat);
                list.add(countProductStat);
            }
            BaseExportModel exportModel = new BaseExportModel();
            Map<String, String> titleMap = new LinkedHashMap<String, String>();
            titleMap.put("statDate", "日期");
            titleMap.put("modelName", "机型名称");
            titleMap.put("productName", "产品名称");
            titleMap.put("installTotalNum", "装机数量");

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
        String queryType = request.getParameter("queryType");
        String count = request.getParameter("count");
        StatImeiRequest statImeiRequest = this.getStatImeiRequestByQueryType(queryType, false);
        if (StringUtils.isNotEmpty(queryType)) {
            statImeiRequest = getStatImeiRequestByQueryType(queryType, false);
        }
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
        if (StringUtils.isNotEmpty(count)) {
            statImeiRequest.setTotalCount(Long.parseLong(count));
        }
        statImeiRequest.setDeviceCode(deviceCode);
        statImeiRequest.setChannelName(channelName);
        statImeiRequest.setModelName(modelName);
        statImeiRequest.setUa(ua);
        List<StatImeiResult> list = statImeiService.queryImeiList(statImeiRequest);
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
            String count = request.getParameter("count");
            if ("lw".equals(userType)) {
                if (!sysUserService.checkAdminMng(CurrentUserUtil.getUserId())) {
                    ChannelInfo channelInfo = channelInfoService.getChannelInfoByUserId(CurrentUserUtil.getUserId());
                    if (JcywConstants.BASE_CONSTANT_N.equals(channelInfo.getQueryImeiSource())) {
                        result.put("ret", false);
                        result.put("errorMsg", "对不起，你没有导出此imei的权限！");
                        return result;
                    }
                }
            } else if ("cp".equals(userType)) {
                if (!sysUserService.checkAdminMng(CurrentUserUtil.getUserId())) {
                    PartnerInfo partnerInfo = partnerInfoService.getPartnerInfoByUserId(CurrentUserUtil.getUserId());
                    if (JcywConstants.BASE_CONSTANT_N.equals(partnerInfo.getExportImeiSource())) {
                        result.put("ret", false);
                        result.put("errorMsg", "对不起，你没有导出此imei的权限！");
                        return result;
                    }
                }
            } else {
               /* if (!sysUserService.checkAdminMng(CurrentUserUtil.getUserId())) {
                    result.put("ret", false);
                    result.put("errorMsg", "对不起，你没有导出此imei的权限！");
                    return result;
                }*/
            }
            String queryType = request.getParameter("queryType");
            StatImeiRequest statImeiRequest = this.getStatImeiRequestByQueryType(queryType, true);
            if (StringUtils.isNotEmpty(queryType)) {
                statImeiRequest = getStatImeiRequestByQueryType(queryType, true);
            }
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
            if (StringUtils.isNotEmpty(count)) {
                statImeiRequest.setTotalCount(Long.parseLong(count));
            }
            statImeiRequest.setUa(ua);
            statImeiRequest.setDeviceCode(deviceCode);
            statImeiRequest.setModelName(modelName);
            List<StatImeiResult> list = statImeiService.queryImeiList(statImeiRequest);
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

    private StatImeiRequest getStatImeiRequestByQueryType(String queryType, Boolean expertType) {
        StatImeiRequest statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Install, expertType);
        if ("1".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Install, expertType);
        } else if ("3".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Arrive_Temp, expertType);
            statImeiRequest.setActive(QueryActive.Total);
        } else if ("4".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Arrive_Temp, expertType);
            statImeiRequest.setActive(QueryActive.Valid);
        } else if ("5".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Log_Arrive_Temp, expertType);
            statImeiRequest.setActive(QueryActive.Invalid);
        } else if ("10".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Arrive_Temp, expertType);
            statImeiRequest.setActive(QueryActive.Total);
        } else if ("11".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Arrive_Temp, expertType);
            statImeiRequest.setActive(QueryActive.Valid);
        } else if ("12".equals(queryType)) {
            statImeiRequest = new StatImeiRequest(ImeiQueryType.Product_Arrive_Temp, expertType);
            statImeiRequest.setActive(QueryActive.Invalid);
        }
        return statImeiRequest;
    }


    @RequestMapping(value = "/listLogArriveStatTemp", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject listLogArriveStatTemp(HttpServletRequest request) {
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
        LogArriveStatTemp logArriveStatTemp = new LogArriveStatTemp();
        if (StringUtils.isNotEmpty(groupId)) {
            logArriveStatTemp.setGroupId(Long.parseLong(groupId));
        }
        if (StringUtils.isNotEmpty(ua)) {
            logArriveStatTemp.setUa(ua.trim());
        }
        if (StringUtils.isNotEmpty(startDate)) {
            logArriveStatTemp.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            logArriveStatTemp.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        ChannelInfo channelInfo = channelInfoService.getChannelInfoByUserId(CurrentUserUtil.getUserId());
        if (channelInfo != null) {
            logArriveStatTemp.setChannelId(channelInfo.getChannelId());
        }
        List<LogArriveStatTemp> list = logArriveStatTempService.queryByVo(page, logArriveStatTemp);
        if (CollectionUtils.isNotEmpty(list)) {
            LogArriveStatTemp countLogArriveStatTemp = logArriveStatTempService.queryCountByVo(logArriveStatTemp);
            list.add(countLogArriveStatTemp);
        }
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }


    @RequestMapping(value = "/exportArriveTempData", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject exportArriveTempData(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        String ua = request.getParameter("ua");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String groupId = request.getParameter("groupId");
        try {
            LogArriveStatTemp logArriveStatTemp = new LogArriveStatTemp();
            if (StringUtils.isNotEmpty(groupId)) {
                logArriveStatTemp.setGroupId(Long.parseLong(groupId));
            }
            if (StringUtils.isNotEmpty(ua)) {
                logArriveStatTemp.setUa(ua.trim());
            }
            if (StringUtils.isNotEmpty(startDate)) {
                logArriveStatTemp.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
            }
            if (StringUtils.isNotEmpty(endDate)) {
                logArriveStatTemp.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
            }
            ChannelInfo channelInfo = channelInfoService.getChannelInfoByUserId(CurrentUserUtil.getUserId());
            if (channelInfo != null) {
                logArriveStatTemp.setChannelId(channelInfo.getChannelId());
            }
            List<LogArriveStatTemp> list = logArriveStatTempService.queryByVo(null, logArriveStatTemp);
            if (CollectionUtils.isNotEmpty(list)) {
                LogArriveStatTemp countLogArriveStatTemp = logArriveStatTempService.queryCountByVo(logArriveStatTemp);
                list.add(countLogArriveStatTemp);
            }
            BaseExportModel exportModel = new BaseExportModel();
            Map<String, String> titleMap = new LinkedHashMap<String, String>();
            titleMap.put("statDate", "日期");
            titleMap.put("channelName", "仓库名称");
            titleMap.put("modelName", "机型名称");
            titleMap.put("validNum", "有效到达数量");
            titleMap.put("invalidNum", "无效到达数量");
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
        String productId = request.getParameter("productId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        ProductArriveStat productArriveStat = new ProductArriveStat();
        if (StringUtils.isNotEmpty(productId)) {
            productArriveStat.setProductId(Long.parseLong(productId));
        }
        if (StringUtils.isNotEmpty(startDate)) {
            productArriveStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            productArriveStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
        }
        PartnerInfo partnerInfo = partnerInfoService.getPartnerInfoByUserId(CurrentUserUtil.getUserId());
        if (partnerInfo != null) {
            productArriveStat.setPartnerId(partnerInfo.getPartnerId());
            Date sdate = DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT);
            Date maxQueryDate = productInfoService.getMaxQueryDateByPartnerId(partnerInfo.getPartnerId());
            if (maxQueryDate != null) {
                if (sdate == null) {
                    productArriveStat.setStartDate(maxQueryDate);
                } else if (sdate != null && maxQueryDate.after(sdate)) {
                    productArriveStat.setStartDate(maxQueryDate);
                }
            }
        }
        List<ProductArriveStat> list = productArriveStatService.querySumByVo(page, productArriveStat);
        if (CollectionUtils.isNotEmpty(list)) {
            ProductArriveStat countProductArriveStat = productArriveStatService.queryCountByVo(productArriveStat);
            list.add(countProductArriveStat);
            if (partnerInfo != null) {
                for (ProductArriveStat productStat1 : list) {
                    if (!sysUserService.checkAdminMng(CurrentUserUtil.getUserId())) {
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

    @RequestMapping(value = "/exportProductArriveData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject exportProductArriveData(HttpServletRequest request, HttpServletResponse response) {
        String productId = request.getParameter("productId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        JSONObject result = new JSONObject();
        try {
            ProductArriveStat productArriveStat = new ProductArriveStat();
            if (StringUtils.isNotEmpty(productId)) {
                productArriveStat.setProductId(Long.parseLong(productId));
            }
            if (StringUtils.isNotEmpty(startDate)) {
                productArriveStat.setStartDate(DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT));
            }
            if (StringUtils.isNotEmpty(endDate)) {
                productArriveStat.setEndDate(DateFormatUtils.parse(endDate, GlobalConstants.DATE_FORMAT_DPT));
            }
            PartnerInfo partnerInfo = partnerInfoService.getPartnerInfoByUserId(CurrentUserUtil.getUserId());
            if (partnerInfo != null) {
                productArriveStat.setPartnerId(partnerInfo.getPartnerId());
                Date sdate = DateFormatUtils.parse(startDate, GlobalConstants.DATE_FORMAT_DPT);
                Date maxQueryDate = productInfoService.getMaxQueryDateByPartnerId(partnerInfo.getPartnerId());
                if (maxQueryDate != null) {
                    if (sdate == null) {
                        productArriveStat.setStartDate(maxQueryDate);
                    } else if (sdate != null && maxQueryDate.after(sdate)) {
                        productArriveStat.setStartDate(maxQueryDate);
                    }
                }
            }
            List<ProductArriveStat> list = productArriveStatService.querySumByVo(null, productArriveStat);
            if (CollectionUtils.isNotEmpty(list)) {
                ProductArriveStat countProductArriveStat = productArriveStatService.queryCountByVo(productArriveStat);
                list.add(countProductArriveStat);
            }
            BaseExportModel exportModel = new BaseExportModel();
            Map<String, String> titleMap = new LinkedHashMap<String, String>();
            titleMap.put("statDate", "日期");
            titleMap.put("modelName", "机型名称");
            titleMap.put("productName", "产品名称");
            titleMap.put("totalNum", "累计到达数量");
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