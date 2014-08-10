package com.ifhz.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.api.constants.ResultType;
import com.ifhz.api.utils.ApiJsonHandler;
import com.ifhz.core.po.DeviceInfo;
import com.ifhz.core.service.cache.DictInfoCacheService;
import com.ifhz.core.service.device.DeviceInfoService;
import com.ifhz.core.service.pkgmng.PackageUpgradeService;
import com.ifhz.core.vo.PackageVo;
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
 * Time: 17:44
 */
@Controller
@RequestMapping("/nzyw/api")
public class UpgradePkgController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpgradePkgController.class);

    @Resource(name = "deviceInfoService")
    private DeviceInfoService deviceInfoService;
    @Resource(name = "dictInfoCacheService")
    private DictInfoCacheService dictInfoCacheService;
    @Resource(name = "packageUpgradeService")
    private PackageUpgradeService packageUpgradeService;

    //method = RequestMethod.POST,
    @RequestMapping(value = "/getPkgLibVersion.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject getPkgVersion(@RequestParam(value = "code", required = true) String code,
                             @RequestParam(value = "apkVersion", required = true) String apkVersion,
                             @RequestParam(value = "pkgVersion", required = true) String pkgVersion
    ) {
        LOGGER.info("receive msg code={},apkVersion={},pkgVersion={}", code, apkVersion, pkgVersion);
        JSONObject result = null;
        try {
            if (StringUtils.isBlank(code) || StringUtils.isBlank(apkVersion) || StringUtils.isBlank(pkgVersion)) {
                return ApiJsonHandler.genJsonRet(ResultType.Fail);
            }
            DeviceInfo info = deviceInfoService.queryByDeviceCode(code.trim());
            LOGGER.info("DeviceInfo={}", info);
            Date date = new Date();
            if (info != null) {
                //初始化版本库查询
                if (StringUtils.equalsIgnoreCase("0", pkgVersion.trim())) {
                    Date startTime = dictInfoCacheService.getSystemInitDate();
                    long apkTimestamp = Long.parseLong(apkVersion.trim());
                    Date endTime = new Date(apkTimestamp);
                    PackageVo commonPkg = packageUpgradeService.queryCommonPkgList(info.getGroupId(), info.getChannelId(), startTime, endTime);
                    List<PackageVo> normalPkgList = packageUpgradeService.queryNormalPkgList(info.getGroupId(), info.getChannelId(), true, startTime, endTime);
                    //成功，无更新
                    if (commonPkg == null && CollectionUtils.isEmpty(normalPkgList)) {
                        result = ApiJsonHandler.genJsonRet(ResultType.SuccNonUpgrade);
                        result.put("version", String.valueOf(endTime.getTime()));
                    } else {//成功，有更新
                        result = ApiJsonHandler.genJsonRet(ResultType.SuccUpgrade);
                        result.put("version", String.valueOf(endTime.getTime()));
                        result.put("pkgList", normalPkgList);
                        result.put("commonPkg", commonPkg);
                    }
                } else {
                    long pkgTimestamp = Long.parseLong(pkgVersion.trim());
                    Date startTime = new Date(pkgTimestamp - 1000);
                    long apkTimestamp = Long.parseLong(apkVersion.trim());
                    Date endTime = new Date(apkTimestamp);
                    PackageVo commonPkg = packageUpgradeService.queryCommonPkgList(info.getGroupId(), info.getChannelId(), startTime, endTime);
                    List<PackageVo> normalPkgList = packageUpgradeService.queryNormalPkgList(info.getGroupId(), info.getChannelId(), false, startTime, endTime);
                    //成功，无更新
                    if (commonPkg == null && CollectionUtils.isEmpty(normalPkgList)) {
                        result = ApiJsonHandler.genJsonRet(ResultType.SuccNonUpgrade);
                        result.put("version", String.valueOf(endTime.getTime()));
                    } else {//成功，有更新
                        result = ApiJsonHandler.genJsonRet(ResultType.SuccUpgrade);
                        result.put("version", String.valueOf(endTime.getTime()));
                        result.put("pkgList", normalPkgList);
                        result.put("commonPkg", commonPkg);
                    }
                }
            }

            if (result == null) {
                result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            }
        } catch (Exception e) {
            result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            LOGGER.error("getPkgVersion error ", e);
        } finally {
            LOGGER.info("return msg code={},apkVersion={},pkgVersion={},returnObj={}", code, apkVersion, pkgVersion, result);
        }

        return result;
    }
}