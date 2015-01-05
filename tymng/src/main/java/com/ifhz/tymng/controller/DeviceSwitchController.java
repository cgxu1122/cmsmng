package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.DeviceSwitch;
import com.ifhz.core.po.auth.SysUser;
import com.ifhz.core.service.api.DataLogApiService;
import com.ifhz.core.service.auther.SysUserService;
import com.ifhz.core.service.stat.DeviceSwitchService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/28
 * Time: 14:14
 */
@Controller
@RequestMapping("/tymng/schedule/bak")
public class DeviceSwitchController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSwitchController.class);

    @Resource
    private DeviceSwitchService deviceSwitchService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private DataLogApiService dataLogApiService;


    @RequestMapping(value = "/queryDataLog.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject queryDataLog(@RequestParam(value = "imei", required = true) String imei) {
        LOGGER.info("receive msg -----------------------------start");
        JSONObject result = new JSONObject();
        try {
            if (StringUtils.isBlank(imei)) {
                result.put("ret", false);
                result.put("msg", "参数错误");
                return result;
            }
            DataLog dataLog = dataLogApiService.getByImei(imei);
            result.put("ret", true);
            result.put("data", dataLog);
        } catch (Exception e) {
            result.put("ret", false);
            LOGGER.error("queryDataLog error ", e);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
    }


    @RequestMapping(value = "/update.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject fetchWdjData(@RequestParam(value = "deviceCode", required = true) String deviceCode) {
        LOGGER.info("receive msg deviceCode={} --------------------start", deviceCode);
        JSONObject result = new JSONObject();
        try {
            if (StringUtils.isBlank(deviceCode)) {
                result.put("ret", false);
                return result;
            }
            DeviceSwitch deviceSwitch = deviceSwitchService.get(deviceCode.trim());
            if (deviceSwitch != null) {
                deviceSwitch.setStatus(1);
                deviceSwitchService.update(deviceSwitch);
                result.put("ret", true);
            } else {
                result.put("ret", false);
            }
        } catch (Exception e) {
            LOGGER.error("arrivalData error ", e);
            result.put("ret", false);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
    }


    @RequestMapping(value = "/queryByVo.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject scanCounterTempLogFoUnDo() {
        LOGGER.info("receive msg -----------------------------start");
        JSONObject result = new JSONObject();
        try {
            List<DeviceSwitch> list = deviceSwitchService.queryByVo();
            result.put("ret", true);
            result.put("data", list);
        } catch (Exception e) {
            result.put("ret", false);
            LOGGER.error("queryByVo error ", e);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
    }


    @RequestMapping(value = "/updateSys.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject updateSys(HttpServletRequest request) {
        LOGGER.info("receive msg ");
        JSONObject result = new JSONObject();
        try {
            Pagination pagination = new Pagination();
            pagination.setPageSize(10000);
            pagination.setCurrentPage(1);
            List<SysUser> list = sysUserService.queryByVo(pagination, new SysUser());
            if (CollectionUtils.isNotEmpty(list)) {
                for (SysUser sysUser : list) {
                    sysUser.setLoginName(StringUtils.trim(sysUser.getLoginName()));
                    sysUserService.update(sysUser);
                }
            }
            list = sysUserService.queryByVo(pagination, new SysUser());
            LOGGER.info("list={}", JSON.toJSONString(list));
        } catch (Exception e) {
            LOGGER.error("arrivalData error ", e);
            result.put("ret", false);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
    }
}
