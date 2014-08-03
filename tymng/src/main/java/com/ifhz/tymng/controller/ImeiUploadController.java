package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.progress.ProgressModel;
import com.ifhz.core.service.api.bean.ImeiStatus;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.service.channel.ChannelInfoService;
import com.ifhz.core.service.imei.ImeiUploadService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 14:47
 */
@Controller
@RequestMapping("/tymng/imeiUpload")
public class ImeiUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImeiUploadController.class);

    @Resource(name = "imeiUploadService")
    private ImeiUploadService imeiUploadService;
    @Resource(name = "channelInfoService")
    private ChannelInfoService channelInfoService;
    @Resource(name = "localDirCacheService")
    private LocalDirCacheService localDirCacheService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("imeiUpload/index");
    }

    @RequestMapping(value = "/exportImei.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject exportImei(@RequestParam(value = "excelFile", required = true) MultipartFile file,
                          @RequestParam(value = "channelId", required = true) Long channelId,
                          @RequestParam(value = "processDateStr", required = true) String processDateStr,
                          HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String originFileName = file.getOriginalFilename();
        if (file.isEmpty() || StringUtils.isNotBlank(originFileName)) {
            result.put("ret", false);
            result.put("errorMsg", "非法请求，请选择需要上传Excel文件");
            return result;
        }
        if (!checkFileType(originFileName)) {
            result.put("ret", false);
            result.put("errorMsg", "非法文件类型，只允许上传Excel文件");
            return result;
        }
        try {
            Date processDate = DateFormatUtils.parse(processDateStr, "yyyy-MM-dd");
            LOGGER.info("用户上传Imei安装文件fileName={} ----------开始处理", originFileName);
            String newFileName = localDirCacheService.getLocalFileName(originFileName);
            String toFilePath = localDirCacheService.storeTempFile(file.getInputStream(), newFileName);
            LOGGER.info("用户上传Imei安装文件fileName={},保存到本地成功,路径为{}", toFilePath);
            Map<ImeiStatus, Integer> map = imeiUploadService.processImeiExcelData(toFilePath, channelId, processDate);
            if (MapUtils.isNotEmpty(map)) {
                for (Map.Entry<ImeiStatus, Integer> entry : map.entrySet()) {
                    result.put(entry.getKey().name(), entry.getValue());
                }
            }
            for (ImeiStatus imeiStatus : ImeiStatus.values()) {
                if (!result.containsKey(imeiStatus.name())) {
                    result.put(imeiStatus.name(), 0);
                }
            }
        } catch (Exception e) {
            result.put("ret", false);
            result.put("errorMsg", "处理上传文件失败，请重新操作");
            LOGGER.error("exportImei error ", e);
        } finally {
            LOGGER.info("returnObj={}", result);
        }

        return result;
    }

    public boolean checkFileType(String fileName) {
        if (StringUtils.endsWithIgnoreCase(fileName, ".xls")) {
            return true;
        }
        if (StringUtils.endsWithIgnoreCase(fileName, ".xlsx")) {
            return true;
        }

        return false;
    }

    /**
     * 前端每隔固定毫秒数请求后台得到session当中保存的上传进度
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getProgress", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject getProgress(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = new JSONObject();
        try {
            ProgressModel ps = (ProgressModel) request.getSession().getAttribute("upload_progress");
            Double percent = 0d;
            if (ps.getContentLength() != 0L) {
                percent = (double) ps.getBytesRead() / (double) ps.getContentLength();  //百分比
                if (percent != 0d) {
                    DecimalFormat df = new DecimalFormat("0.0");
                    percent = Double.parseDouble(df.format(percent));
                }
            }
            result.put("ret", true);
            result.put("percent", percent);
        } catch (NumberFormatException e) {
            result.put("ret", false);
            LOGGER.error("getProgress error", e);
        } finally {
            LOGGER.info("returnObj={}", result.toJSONString());
        }

        return result;
    }
}
