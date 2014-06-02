package com.ifhz.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ifhz.api.constants.ResultType;
import com.ifhz.api.utils.ApiJsonHandler;
import com.ifhz.core.po.ApkInfo;
import com.ifhz.core.po.DeviceInfo;
import com.ifhz.core.service.device.DeviceInfoService;
import com.ifhz.core.service.device.DeviceSystemService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 17:44
 */
@Controller
@RequestMapping("/nzyw/api")
public class UpgradePkgController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpgradePkgController.class);

    @Resource(name = "deviceSystemService")
    private DeviceSystemService deviceSystemService;
    @Resource(name = "deviceInfoService")
    private DeviceInfoService deviceInfoService;


    @RequestMapping(value = "/getPkgVersion.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject getPkgVersion(@RequestParam(value = "code", required = true) String code,
                             @RequestParam(value = "version", required = true) String version) {
        LOGGER.info("receive msg code={},version={}", code, version);
        JSONObject result = null;
        try {
            if (StringUtils.isBlank(code)) {
                return ApiJsonHandler.genJsonRet(ResultType.Fail);
            }
            DeviceInfo info = deviceInfoService.queryByDeviceCode(code.trim());
            if (info != null) {
                //TODO 待实现
                List<ApkInfo> list = null;
                JSONArray pkgList = new JSONArray();
                if (CollectionUtils.isNotEmpty(list)) {
                    for (ApkInfo apkInfo : list) {
                        if (apkInfo != null) {

                        }
                    }
                }
                result = ApiJsonHandler.genJsonRet(ResultType.Succ);
                result.put("version", String.valueOf(version));
                result.put("pkgList", pkgList);
            }
            if (result == null) {
                result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            }
        } catch (Exception e) {
            result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            LOGGER.error("getPkgVersion error ", e);
        } finally {
            LOGGER.info("getPkgVersion:code={},returnObj={}", code, result);
        }

        return result;
    }
}