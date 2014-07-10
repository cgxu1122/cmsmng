package com.ifhz.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.api.constants.ResultType;
import com.ifhz.api.utils.ApiJsonHandler;
import com.ifhz.core.po.DeviceInfo;
import com.ifhz.core.po.DeviceSystem;
import com.ifhz.core.service.device.DeviceInfoService;
import com.ifhz.core.service.device.DeviceSystemService;
import com.ifhz.core.utils.HostsHandle;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 17:44
 */
@Controller
@RequestMapping("/nzyw/api")
public class DeviceUpgradeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceUpgradeController.class);

    @Resource(name = "deviceSystemService")
    private DeviceSystemService deviceSystemService;
    @Resource(name = "deviceInfoService")
    private DeviceInfoService deviceInfoService;


    @RequestMapping(value = "/getDeviceVersion.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject getDeviceVersion(@RequestParam(value = "code", required = true) String code,
                                @RequestParam(value = "version", required = true) String version) {
        LOGGER.info("receive msg code={},version={}", code, version);
        JSONObject result = null;
        try {
            if (StringUtils.isBlank(code) || StringUtils.isBlank(version)) {
                return ApiJsonHandler.genJsonRet(ResultType.Fail);
            }
            DeviceInfo info = deviceInfoService.queryByDeviceCode(code.toUpperCase());
            if (info != null) {
                DeviceSystem newestDeviceSytem = deviceSystemService.queryNewestVersion(new Date());
                if (newestDeviceSytem != null) {
                    if (StringUtils.equalsIgnoreCase(version.trim(), newestDeviceSytem.getVersion())) {
                        result = ApiJsonHandler.genJsonRet(ResultType.SuccNonUpgrade);
                    } else {
                        result = ApiJsonHandler.genJsonRet(ResultType.SuccUpgrade);
                        result.put("path", HostsHandle.getHostPrefix() + newestDeviceSytem.getDownloadUrl());
                        result.put("md5value", newestDeviceSytem.getMd5Value());
                    }
                }
                result.put("cid", info.getChannelId().toString());
            }
            if (result == null) {
                result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            }
        } catch (Exception e) {
            result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            LOGGER.error("getDeviceVersion error ", e);
        } finally {
            LOGGER.info("getDeviceVersion:returnObj={}", result);
        }

        return result;
    }
}