package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.service.schedule.ScheduleBakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/28
 * Time: 14:14
 */
@Controller
@RequestMapping("/tymng/schedule/bak")
public class ScheduleBakController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleBakController.class);

    @Resource(name = "scheduleBakService")
    private ScheduleBakService scheduleBakService;

    @RequestMapping(value = "/wdj.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject fetchWdjData(@RequestParam(value = "date", required = true) String date) {
        LOGGER.info("receive msg date={} --------------------start", date);
        JSONObject result = new JSONObject();
        try {
            Date temp = DateFormatUtils.parse(date, GlobalConstants.DATE_FORMAT_DPT);
            scheduleBakService.fetchWdjData(temp);
            result.put("ret", true);
        } catch (Exception e) {
            LOGGER.error("arrivalData error ", e);
        } finally {
            LOGGER.info("returnObj={}", result);
            result.put("ret", false);
        }

        return result;
    }


    @RequestMapping(value = "/scanCounterTempLogFoUnDo.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject scanCounterTempLogFoUnDo(@RequestParam(value = "startTime", required = true) String startTime,
                                        @RequestParam(value = "endTime", required = true) String endTime) {
        LOGGER.info("receive msg -----------------------------start");
        JSONObject result = new JSONObject();
        try {
            Date startDate = DateFormatUtils.parse(startTime, GlobalConstants.DATE_FORMAT_DPT);
            Date endDate = DateFormatUtils.parse(endTime, GlobalConstants.DATE_FORMAT_DPT);
            scheduleBakService.scanCounterTempLogFoUnDo(startDate, endDate);
            result.put("ret", true);
        } catch (Exception e) {
            result.put("ret", false);
            LOGGER.error("scanCounterTempLog error ", e);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
    }

    @RequestMapping(value = "/scanCounterTempLogFoUnStat.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject scanCounterTempLogFoUnStat(@RequestParam(value = "startTime", required = true) String startTime,
                                          @RequestParam(value = "endTime", required = true) String endTime) {
        LOGGER.info("receive msg -----------------------------start");
        JSONObject result = new JSONObject();
        try {
            Date startDate = DateFormatUtils.parse(startTime, GlobalConstants.DATE_FORMAT_DPT);
            Date endDate = DateFormatUtils.parse(endTime, GlobalConstants.DATE_FORMAT_DPT);
            scheduleBakService.scanCounterTempLogFoUnStat(startDate, endDate);
            result.put("ret", true);
        } catch (Exception e) {
            result.put("ret", false);
            LOGGER.error("scanCounterTempLog error ", e);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
    }

    @RequestMapping(value = "/resetStat.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject resetStat(@RequestParam(value = "startTime", required = true) String startTime,
                         @RequestParam(value = "endTime", required = true) String endTime) {
        LOGGER.info("receive msg -----------------------------start");
        JSONObject result = new JSONObject();
        try {
            Date startDate = DateFormatUtils.parse(startTime, GlobalConstants.DATE_FORMAT_DPT);
            Date endDate = DateFormatUtils.parse(endTime, GlobalConstants.DATE_FORMAT_DPT);
            scheduleBakService.scanCounterTempLogFoUnStat(startDate, endDate);
            result.put("ret", true);
        } catch (Exception e) {
            result.put("ret", false);
            LOGGER.error("scanCounterTempLog error ", e);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
    }

    @RequestMapping(value = "/statisticsData.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject statisticsData(@RequestParam(value = "startTime", required = true) String startTime,
                              @RequestParam(value = "endTime", required = true) String endTime) {
        LOGGER.info("receive msg -----------------------------start");
        JSONObject result = new JSONObject();
        try {
            Date startDate = DateFormatUtils.parse(startTime, GlobalConstants.DATE_FORMAT_DPT);
            Date endDate = DateFormatUtils.parse(endTime, GlobalConstants.DATE_FORMAT_DPT);
            scheduleBakService.statisticsData(startDate, endDate);
            result.put("ret", true);
        } catch (Exception e) {
            result.put("ret", false);
            LOGGER.error("statisticsData error ", e);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
    }

    @RequestMapping(value = "/syncLogActiveTemp.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject syncLogArriveTemp(@RequestParam(value = "startTime", required = true) String startTime,
                                 @RequestParam(value = "endTime", required = true) String endTime) {
        LOGGER.info("receive msg -----------------------------start");
        JSONObject result = new JSONObject();
        try {
            Date startDate = DateFormatUtils.parse(startTime, GlobalConstants.DATE_FORMAT_DPT);
            Date endDate = DateFormatUtils.parse(endTime, GlobalConstants.DATE_FORMAT_DPT);
            scheduleBakService.syncLogActiveTemp(startDate, endDate);
            result.put("ret", true);
        } catch (Exception e) {
            result.put("ret", false);
            LOGGER.error("syncLogArriveTemp error ", e);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
    }

    @RequestMapping(value = "/syncProductActiveTemp.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject syncProductActiveTemp(@RequestParam(value = "startTime", required = true) String startTime,
                                     @RequestParam(value = "endTime", required = true) String endTime) {
        LOGGER.info("receive msg -----------------------------start");
        JSONObject result = new JSONObject();
        try {
            Date startDate = DateFormatUtils.parse(startTime, GlobalConstants.DATE_FORMAT_DPT);
            Date endDate = DateFormatUtils.parse(endTime, GlobalConstants.DATE_FORMAT_DPT);
            scheduleBakService.syncProductActiveTemp(startDate, endDate);
            result.put("ret", true);
        } catch (Exception e) {
            result.put("ret", false);
            LOGGER.error("syncProductActiveTemp error ", e);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
    }
}
