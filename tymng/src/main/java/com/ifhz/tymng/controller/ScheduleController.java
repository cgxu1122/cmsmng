package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.service.schedule.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/28
 * Time: 14:14
 */
@Controller
@RequestMapping("/tymng/schedule")
public class ScheduleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleController.class);

    @Resource(name = "scheduleService")
    private ScheduleService scheduleService;

    @RequestMapping(value = "/wdj.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject fetchWdjData() {
        LOGGER.info("receive msg --------------------start");
        JSONObject result = new JSONObject();
        try {
            scheduleService.fetchWdjData();
        } catch (Exception e) {
            LOGGER.error("arrivalData error ", e);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
    }


    @RequestMapping(value = "/scanCounterTempLog.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject scanCounterTempLog() {
        LOGGER.info("receive msg -----------------------------start");
        JSONObject result = new JSONObject();
        try {
            scheduleService.scanCounterTempLog();
        } catch (Exception e) {
            LOGGER.error("scanCounterTempLog error ", e);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
    }

    @RequestMapping(value = "/statisticsData.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject statisticsData() {
        LOGGER.info("receive msg -----------------------------start");
        JSONObject result = new JSONObject();
        try {
            scheduleService.statisticsData();
        } catch (Exception e) {
            LOGGER.error("statisticsData error ", e);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
    }
}