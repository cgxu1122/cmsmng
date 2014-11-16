package com.ifhz.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.api.constants.ResultType;
import com.ifhz.api.utils.ApiJsonHandler;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.po.DeviceInfo;
import com.ifhz.core.service.cache.DictInfoCacheService;
import com.ifhz.core.service.device.DeviceInfoService;
import com.ifhz.core.service.pkgmng.PackageUpgradeService;
import com.ifhz.core.utils.HostsHandle;
import com.ifhz.core.vo.ApkVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Resource(name = "packageUpgradeService")
    private PackageUpgradeService packageUpgradeService;
    @Resource(name = "deviceInfoService")
    private DeviceInfoService deviceInfoService;
    @Resource(name = "dictInfoCacheService")
    private DictInfoCacheService dictInfoCacheService;

    //method = RequestMethod.POST,
    @RequestMapping(value = "/getApkLibVersion.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject getAPKLibVersion(@RequestParam(value = "code", required = true) String code,
                                @RequestParam(value = "apkVersion", required = true) String apkVersion) {
        LOGGER.info("receive msg code={},version={}", code, apkVersion);
        JSONObject result = null;
        try {
            if (StringUtils.isBlank(code) || StringUtils.isBlank(apkVersion)) {
                result = ApiJsonHandler.genJsonRet(ResultType.Fail);
                return result;
            }
            DeviceInfo info = deviceInfoService.queryByDeviceCode(code.trim());
            if (info != null) {
                Date endTime = DateFormatUtils.addMinute(new Date(), -10);
                Date startTime;
                if (StringUtils.equalsIgnoreCase(apkVersion.trim(), "0")) {
                    startTime = dictInfoCacheService.getSystemInitDate();
                } else {
                    startTime = new Date(Long.parseLong(apkVersion.trim()) - 1000);
                }
                List<ApkVo> apkVoList = packageUpgradeService.queryApkList(info.getGroupId(), info.getChannelId(), startTime, endTime);
                if (CollectionUtils.isEmpty(apkVoList)) {
                    result = ApiJsonHandler.genJsonRet(ResultType.SuccNonUpgrade);
                    result.put("version", String.valueOf(endTime.getTime()));
                } else {
                    result = ApiJsonHandler.genJsonRet(ResultType.SuccUpgrade);
                    for (ApkVo apkVo : apkVoList) {
                        if (StringUtils.isNotBlank(apkVo.getPath())) {
                            apkVo.setPath(HostsHandle.getHostPrefix() + apkVo.getPath());
                        }
                    }
                    result.put("apkList", apkVoList);
                    result.put("version", String.valueOf(endTime.getTime()));
                }
            }
            if (result == null) {
                result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            }
        } catch (Exception e) {
            result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            LOGGER.error("getApkLibVersion error ", e);
        } finally {
            LOGGER.info("getApkLibVersion:code={},version={},returnObj={}", code, apkVersion, result);
        }

        return result;
    }
}