package com.ifhz.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.api.constants.ResultType;
import com.ifhz.api.utils.ApiJsonHandler;
import com.ifhz.core.po.ChannelVersion;
import com.ifhz.core.po.DeviceInfo;
import com.ifhz.core.service.device.DeviceInfoService;
import com.ifhz.core.service.pkgmng.ChannelVersionService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 18:43
 */
@Controller
@RequestMapping("/nzyw/api")
public class DeviceInitApkLibController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceInitApkLibController.class);

    @Resource(name = "channelVersionService")
    private ChannelVersionService channelVersionService;
    @Resource(name = "deviceInfoService")
    private DeviceInfoService deviceInfoService;

    @RequestMapping(value = "/initAPKLibVersion.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject initAPKLibVersion(@RequestParam(value = "code", required = true) String code) {
        LOGGER.info("receive msg code={}", code);
        JSONObject result = null;
        try {
            if (StringUtils.isBlank(code)) {
                return ApiJsonHandler.genJsonRet(ResultType.Fail);
            }
            DeviceInfo info = deviceInfoService.queryByDeviceCode(code.trim());
            if (info != null) {
                ChannelVersion channelVersion = channelVersionService.getByChannelId(info.getChannelId());
                if (channelVersion != null) {
                    result = ApiJsonHandler.genJsonRet(ResultType.Succ);
                    result.put("version", channelVersion.getVersion());
                    result.put("path", channelVersion.getPath());
                    result.put("md5value", channelVersion.getMd5Value());
                }
            }
            if (result == null) {
                result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            }
        } catch (Exception e) {
            result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            LOGGER.error("initAPKLibVersion error ", e);
        } finally {
            LOGGER.info("initAPKLibVersion:code={},returnObj={}", code, result);
        }

        return result;
    }
}
