package com.ifhz.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ifhz.api.constants.ResultType;
import com.ifhz.api.utils.ApiJsonHandler;
import com.ifhz.core.po.ApkInfo;
import com.ifhz.core.po.DeviceInfo;
import com.ifhz.core.service.device.DeviceInfoService;
import com.ifhz.core.service.pkgmng.ApkInfoService;
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
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 21:27
 */
@Controller
@RequestMapping("/nzyw/api")
public class UpgradeApkLibController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpgradeApkLibController.class);

    @Resource(name = "apkInfoService")
    private ApkInfoService apkInfoService;
    @Resource(name = "deviceInfoService")
    private DeviceInfoService deviceInfoService;


    @RequestMapping(value = "/getApkLibVersion.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
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
            DeviceInfo info = deviceInfoService.queryByDeviceCode(code.trim());
            if (info != null) {
                long newVersion = new Date().getTime();
                List<ApkInfo> list;
                if (StringUtils.equalsIgnoreCase(version.trim(), "0")) {
                    list = apkInfoService.queryAllList();
                } else {
                    Date date = new Date(Long.parseLong(version.trim()));
                    list = apkInfoService.queryUpgradeList(date);
                }
                JSONArray apkList = new JSONArray();
                if (CollectionUtils.isNotEmpty(list)) {
                    for (ApkInfo apkInfo : list) {
                        if (apkInfo != null) {
                            JSONObject apk = new JSONObject();
                            apk.put("apkId", apkInfo.getApkId());
                            apk.put("md5", apkInfo.getMd5Value());
                            apk.put("path", apkInfo.getDownloadUrl());
                            apkList.add(apk);
                        }
                    }
                }
                result = ApiJsonHandler.genJsonRet(ResultType.Succ);
                result.put("version", String.valueOf(newVersion));
                result.put("apkList", apkList);
            }
            if (result == null) {
                result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            }
        } catch (Exception e) {
            result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            LOGGER.error("getApkLibVersion error ", e);
        } finally {
            LOGGER.info("getApkLibVersion:code={},version={},returnObj={}", code, version, result);
        }

        return result;
    }
}