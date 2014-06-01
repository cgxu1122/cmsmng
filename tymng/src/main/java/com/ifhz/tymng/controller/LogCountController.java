package com.ifhz.tymng.controller;


import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DeviceProcessLog;
import com.ifhz.core.service.api.DeviceProcessLogService;
import com.ifhz.core.service.statistics.LogCountService;
import com.ifhz.core.service.statistics.ProductCountService;
import com.ifhz.tymng.utils.toexcel.Export;
import com.ifhz.tymng.utils.toexcel.ExportToExcel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lm on 14-5-17.
 */
@Controller
@RequestMapping("/logCount")
public class LogCountController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogCountController.class);

    @Autowired
    private LogCountService logCountService;
    @Autowired
    private ProductCountService productCountService;
    @Autowired
    private DeviceProcessLogService deviceProcessLogService;

    @RequestMapping("/index")
    public String index() {
        return "count/testCount";
    }

    @RequestMapping("/tianyin")
    public String tianyin() {
        return "partnerQuery/tianyin";
    }

    @RequestMapping("/dibao")
    public String dibao() {
        return "partnerQuery/dibao";
    }

    @RequestMapping("/cp")
    public String cp() {
        return "partnerQuery/cp";
    }

    @RequestMapping("/laowu")
    public String laowu() {
        return "partnerQuery/laowu";
    }

    @RequestMapping(value = "/partnerQueryList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject partnerQueryList(HttpServletRequest request) {
        Pagination page = getPageMess(request);
        Map pars = getPars(request);
        List<Map<String, Object>> list = logCountService.partnerQuery(page, pars);

        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/partnerCPQueryList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject partnerCPQueryList(HttpServletRequest request) {
        Pagination page = getPageMess(request);
        Map pars = getPars(request);
        List<Map<String, Object>> list = logCountService.partnerCPQuery(page, pars);

        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/partnerLaowuQueryList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject partnerLaowuQueryList(HttpServletRequest request) {
        Pagination page = getPageMess(request);
        Map pars = getPars(request);
        pars.put("channelId", request.getParameter("channelId"));
        List<Map<String, Object>> list = logCountService.partnerLaowuQueryList(page, pars);

        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/downImea", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void downImea(HttpServletRequest request, HttpServletResponse response) {
        DeviceProcessLog pars = new DeviceProcessLog();
        Long countTime = Long.parseLong(request.getParameter("countTime"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(new Date(countTime));
        pars.setProcessTime(formattedDate);
        pars.setDeviceCode(request.getParameter("deviceCode"));
        pars.setModelName(request.getParameter("modleName"));
        pars.setGroupId(Long.parseLong(request.getParameter("groupId")));
        pars.setChannelId(request.getParameter("channelId"));

        List<DeviceProcessLog> list = deviceProcessLogService.queryDeviceProcessLog(pars);
        // System.out.println(formattedDate+"\t"+pars.getDeviceCode()+"\t"+pars.getModelName()+"\tgetChannelId="+pars.getChannelId()+"\tsize="+list.size());
        List records = new ArrayList();
        for (DeviceProcessLog dpl : list) {
            List record = new ArrayList();
            record.add(dpl.getProcessTime());
            record.add(dpl.getModelName());
            record.add(dpl.getImei());
            records.add(record);
        }
        String filene = "装机imei详细表";
        String[][] names = {{"日期"}, {"机型"}, {"IMEI号"}};
        Export ee = new ExportToExcel();
        ee.exportExcel(names, records, response, filene);
    }

    private Pagination getPageMess(HttpServletRequest request) {
        /**分页*/
        String pageNum = request.getParameter("page");
        String pageSize = request.getParameter("rows");
        Pagination page = new Pagination();
        if (!StringUtils.isEmpty(pageNum)) page.setCurrentPage(Integer.valueOf(pageNum));
        if (!StringUtils.isEmpty(pageSize)) page.setPageSize(Integer.valueOf(pageSize));
        return page;
    }

    private Map getPars(HttpServletRequest request) {
        //查询条件
        String modleName = request.getParameter("modleName");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String groupId = request.getParameter("groupId");
        Map pars = new HashMap();
        pars.put("modleName", modleName);
        pars.put("startDate", DateFormatUtils.convertYYYYMMDD(startDate));
        pars.put("endDate", DateFormatUtils.convertYYYYMMDD(endDate));
        pars.put("groupId", Long.parseLong(groupId));
        return pars;
    }
}
