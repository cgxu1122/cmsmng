package com.ifhz.hzfmng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.service.imei.ImeiQueryService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 14:47
 */
@Controller
@RequestMapping("/hzfmng/imei")
public class ImeiQueryController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImeiQueryController.class);

    @Resource(name = "imeiQueryService")
    private ImeiQueryService imeiQueryService;


    @RequestMapping(value = "/list.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject arrivalData(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String errorMsg = null;
        try {
            Map<String, FileItem> params = paserMultiData(request);
            String imeiList = readInputStreamData(params.get("imeiList").getInputStream());
            if (StringUtils.isNotBlank(imeiList)) {
//                Splitter.on("\\,").trimResults()
            }
            FileItem file = params.get("file");


        } catch (Exception e) {
            LOGGER.error("arrivalData error ", e);
        } finally {
            LOGGER.info("arrivalData:returnObj={}", result);
        }

        return result;
    }


    public String readInputStreamData(InputStream in) {
        String result = null;
        try {
            byte[] tt = new byte[in.available()];
            int b;
            while ((b = in.read(tt)) != -1) {
            }
            result = new String(tt, "utf-8");
        } catch (IOException e) {
        } finally {
            IOUtils.closeQuietly(in);
        }
        return result;
    }

}
