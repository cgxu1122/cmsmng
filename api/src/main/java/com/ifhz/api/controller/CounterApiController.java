package com.ifhz.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.ifhz.api.constants.ResultType;
import com.ifhz.api.utils.ApiJsonHandler;
import com.ifhz.core.base.commons.codec.CodecUtils;
import com.ifhz.core.base.commons.log.CounterCommonLog;
import com.ifhz.core.po.DataLog;
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
        JSONObject result = null;
        try {
            if (StringUtils.isNotBlank(data)) {
                String source = CodecUtils.decode(data).trim();
                LOGGER.info("receive decode data={}", source);
                Splitter splitter = Splitter.on("|");
                Iterable<String> iterable = splitter.split(source);

                //手机imei|手机ua|到达状态
                DataLog dataLog = translateDataLog(iterable);
                if (dataLog != null) {
                    apiUploadService.saveCounterDataLog(dataLog);
                    result = ApiJsonHandler.genJsonRet(ResultType.SuccNonUpgrade);
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

    private DataLog translateDataLog(String[] data) {
        //手机imei|手机ua|到达状态
        DataLog result = null;
        if (data != null && data.length == 3) {
            try {
                result = new DataLog();
                result.setCounterUploadTime(new Date());
                result.setImei(StringUtils.trimToEmpty(data[0]));
                result.setUa(StringUtils.trimToEmpty(data[1]));
                String activeStr = StringUtils.trimToEmpty(data[2]);
                if (StringUtils.isNotBlank(activeStr)) {
                    result.setActive(Integer.parseInt(activeStr));
                }
            } catch (Exception e) {
                CounterCommonLog.info("{}", JSON.toJSONString(data));
                LOGGER.error("translateDataLog error ", e);
                return null;
            }
        } else {
            LOGGER.info("数据校验不通过：{}", JSON.toJSONString(data));
        }

        return result;
    }

    private DataLog translateDataLog(Iterable<String> iterable) {
        //手机imei|手机ua|到达状态
        DataLog result = null;
        if (iterable != null) {
            try {
                result = new DataLog();
                result.setCounterUploadTime(new Date());
                int i = 0;
                for (String value : iterable) {
                    if (i == 0) {
                        result.setImei(StringUtils.trimToEmpty(value));
                    } else if (i == 1) {
                        result.setUa(StringUtils.trimToEmpty(value));
                    } else if (i == 2) {
                        String activeStr = StringUtils.trimToEmpty(value);
                        if (StringUtils.isNotBlank(activeStr)) {
                            result.setActive(Integer.parseInt(activeStr));
                        }
                    }
                    i++;
                }
            } catch (Exception e) {
                CounterCommonLog.info("{}", JSON.toJSONString(iterable));
                LOGGER.error("translateDataLog error ", e);
                return null;
            }
        } else {
            LOGGER.info("数据校验不通过：{}", JSON.toJSONString(iterable));
        }

        return result;
    }

}
