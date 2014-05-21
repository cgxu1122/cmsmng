package com.ifhz.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.api.constants.ResultType;
import com.ifhz.api.utils.ApiJsonHandler;
import com.ifhz.core.service.pkgmng.IncrementalUpdateService;
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
 * Time: 21:27
 */
@Controller
@RequestMapping("/nzyw/api")
public class DeviceIncrementalController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceUpgradeController.class);

    @Resource(name = "incrementalUpdateService")
    private IncrementalUpdateService incrementalUpdateService;


    @RequestMapping(value = "/getAPKLibVersion.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject getAPKLibVersion(@RequestParam(value = "code", required = true) String code,
                                @RequestParam(value = "version", required = true) String version) {
        LOGGER.info("receive msg code={},version={}", code, version);
        JSONObject result = null;
        try {
            if (StringUtils.isBlank(code) || StringUtils.isBlank(version)) {
                return ApiJsonHandler.genJsonRet(ResultType.Fail);
            }
            //TODO
        } catch (Exception e) {
            result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            LOGGER.error("getAPKLibVersion error ", e);
        } finally {
            LOGGER.info("getAPKLibVersion:code={},version={},returnObj={}", code, version, result);
        }

        return result;
    }
}