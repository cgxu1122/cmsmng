package com.ifhz.tymng.controller;


import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.service.api.CounterUploadLogService;
import com.ifhz.core.service.api.DeviceProcessLogService;
import com.ifhz.core.service.statistics.LogCountService;
import com.ifhz.core.service.statistics.ProductCountService;
import com.ifhz.tymng.utils.toexcel.Export;
import com.ifhz.tymng.utils.toexcel.ExportToExcel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/reportCount")
public class ReportCountController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportCountController.class);

    private LogCountService logCountService;
    private ProductCountService productCountService;
    private DeviceProcessLogService deviceProcessLogService;
    private CounterUploadLogService counterUploadLogService;


    @RequestMapping("/warehouseQuery")
    public String warehouseQuery() {
        return "reportCount/warehouseQuery";
    }

    @RequestMapping(value = "/warehouseQueryList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject warehouseQueryList(HttpServletRequest request) {
        Pagination page = getPageMess(request);
        Map pars = getPars(request);
        pars.put("channelId", request.getParameter("channelId"));
        String active = request.getParameter("active");
        String noActiveReason = request.getParameter("noActiveReason");

        List<Map<String, Object>> list = logCountService.warehouseQueryList(page, pars);

        if (active == null || active.trim().equals("")) {//全部
            for (Map<String, Object> record : list) {
                record.put("arriveCount", record.get("allCount"));
                record.put("active", "-1");
            }
        } else if ("1".equals(active)) {//有效到达
            for (Map<String, Object> record : list) {
                record.put("arriveCount", record.get("activeCount"));
                record.put("active", "1");
            }
        } else if ("0".equals(active)) {//无效到达
            if (noActiveReason == null || noActiveReason.trim().equals("")) {
                for (Map<String, Object> record : list) {
                    record.put("arriveCount", record.get("nonActiveCount"));
                    record.put("active", "0");
                }
            } else if (noActiveReason.equals("2")) {//无效替换
                for (Map<String, Object> record : list) {
                    record.put("arriveCount", record.get("nonActiveReplaceCount"));
                    record.put("active", "2");
                }
            } else if (noActiveReason.equals("3")) {//无效卸载
                for (Map<String, Object> record : list) {
                    record.put("arriveCount", record.get("nonActiveUninstallCount"));
                    record.put("active", "3");
                }
            }
        }
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    /*
    下载累计到达数量，累计到达数量为静态数据，指当天上传的数量
     */
    @RequestMapping(value = "/downUploadImei", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void downUploadImei(HttpServletRequest request, HttpServletResponse response) {
        Long countTime = Long.parseLong(request.getParameter("countTime"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String createTime = dateFormat.format(new Date(countTime));
        String groupId = request.getParameter("groupId");
        Map pars = new HashMap();
        System.out.println("createTime=" + createTime);
        pars.put("modleName", request.getParameter("modleName"));
        pars.put("createTime", createTime);
        pars.put("groupId", Long.parseLong(groupId));
        pars.put("channelId", request.getParameter("channelId"));

        List<Map<String, Object>> list = counterUploadLogService.queryUploadImei(pars);

        List records = new ArrayList();
        for (Map m : list) {
            List record = new ArrayList();
            record.add(m.get("imei"));
            record.add(m.get("createTime"));
            records.add(record);
        }
        String filene = "累计到达数量imei详细表";
        String[][] names = {{"IMEI号"}, {"日期"}};
        Export ee = new ExportToExcel();
        ee.exportExcel(names, records, response, filene);
    }

    /*
   下载装机到达数量,动态数据。根据到达状态查询
   由于后台数据库没有表示无效状态，前端用active=0表示无效状态，active=0时，查询条件是active in ('2','3')
    */
    @RequestMapping(value = "/downArriveImei", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void downArriveImei(HttpServletRequest request, HttpServletResponse response) {
        Long countTime = Long.parseLong(request.getParameter("countTime"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String processTime = dateFormat.format(new Date(countTime));
        String groupId = request.getParameter("groupId");
        Map pars = new HashMap();
        pars.put("modleName", request.getParameter("modleName"));
        pars.put("processTime", processTime);
        pars.put("groupId", Long.parseLong(groupId));
        pars.put("channelId", request.getParameter("channelId"));
        String active = request.getParameter("active");
        String noActiveReason = request.getParameter("noActiveReason");
        // 由于后台数据库表没有表示无效状态列，前端用active=0表示无效状态，active=0时，查询条件是active in ('2','3')
        if (active != null && !active.equals("")) {
            if (active != null && active.equals("1")) {
                int[] actives = {1};
                pars.put("active", actives);
            } else if (active != null && active.equals("2")) {
                int[] actives = {2};
                pars.put("active", actives);
            } else if (active != null && active.equals("3")) {
                int[] actives = {3};
                pars.put("active", actives);
            } else if (active != null && active.equals("0")) {
                int[] actives = {2, 3};
                pars.put("active", actives);
            }
        }
        List<Map<String, Object>> list = counterUploadLogService.queryArriveImei(pars);
        // System.out.println(formattedDate+"\t"+pars.getDeviceCode()+"\t"+pars.getModelName()+"\tgetChannelId="+pars.getChannelId()+"\tsize="+list.size());
        List records = new ArrayList();
        for (Map m : list) {
            List record = new ArrayList();
            record.add(m.get("imei"));
            record.add(m.get("createTime"));
            records.add(record);
        }
        String filene = "装机到达数量imei详细表";
        String[][] names = {{"IMEI号"}, {"日期"}};
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
