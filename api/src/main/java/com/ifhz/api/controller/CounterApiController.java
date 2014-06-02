package com.ifhz.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.api.constants.ResultType;
import com.ifhz.api.utils.ApiJsonHandler;
import com.ifhz.core.base.commons.codec.CodecUtils;
import com.ifhz.core.base.commons.log.CounterCommonLog;
import com.ifhz.core.po.CounterUploadLog;
import com.ifhz.core.service.api.ApiUploadService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/19
 * Time: 23:22
 */
@Controller
@RequestMapping("/nzyw/api")
public class CounterApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CounterApiController.class);

    @Resource(name = "apiUploadService")
    private ApiUploadService apiUploadService;


    @RequestMapping(value = "/arrivalData.do", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject arrivalData(@RequestParam(value = "data", required = true) String data,
                           HttpServletRequest request) {
        LOGGER.info("receive encode data={}", data);
        CounterCommonLog.info("receive encode data={}", data);
        JSONObject result = null;
        try {
            if (StringUtils.isNotBlank(data)) {
                String source = CodecUtils.decode(data).trim();
                LOGGER.info("receive decode data={}", source);
                CounterCommonLog.info("receive decode data={}", source);
                String[] array = StringUtils.split(source, "|");
                //^手机imei|手机ua|渠道id|加工设备编码|批次号|手机加工时间戳
                if (array.length == 7 && valid(array)) {
                    if (StringUtils.isBlank(array[7])) {
                        LOGGER.error("data={} ,active is null", source);
                    }
                    CounterUploadLog log = new CounterUploadLog();
                    log.setImei(StringUtils.trimToEmpty(array[0]));
                    log.setUa(StringUtils.trimToEmpty(array[1]));
                    log.setChannelId(StringUtils.trimToEmpty(array[2]));
                    log.setDeviceCode(StringUtils.trimToEmpty(array[3]));
                    log.setBatchCode(StringUtils.trimToEmpty(array[4]));
                    log.setProcessTime(StringUtils.trimToEmpty(array[5]));
                    log.setActive(StringUtils.trimToEmpty(array[6]));
                    log.setCreateTime(new Date());

                    apiUploadService.save(log);
                    result = ApiJsonHandler.genJsonRet(ResultType.Succ);
                } else {
                    LOGGER.warn("data is non-valid,data={}", data);
                }
            }

            if (result == null) {
                result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            }
        } catch (Exception e) {
            result = ApiJsonHandler.genJsonRet(ResultType.Fail);
            LOGGER.error("arrivalData error ", e);
        } finally {
            LOGGER.info("arrivalData:returnObj={}", result);
        }

        return result;
    }

    private boolean valid(String[] list) {
        for (int i = 0; i < list.length; i++) {
            if (StringUtils.trimToNull(list[0]) != null) {
                return true;
            }
        }

        return false;
    }
}
