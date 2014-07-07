package com.ifhz.hzfmng.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.service.channel.ChannelInfoService;
import com.ifhz.core.service.imei.ImeiUploadService;
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
import java.io.File;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 14:47
 */
@Controller
@RequestMapping("/hzfmng/zipUpload")
public class ZipUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZipUploadController.class);

    @Resource(name = "imeiUploadService")
    private ImeiUploadService imeiUploadService;
    @Resource(name = "channelInfoService")
    private ChannelInfoService channelInfoService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("imeiUpload/index");
    }

    @RequestMapping(value = "/uploadZip.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject uploadZip(@RequestParam(value = "zipFile", required = true) MultipartFile file,
                         @RequestParam(value = "channelId", required = true) Long channelId,
                         HttpServletRequest request) {
        JSONObject result = new JSONObject();
        if (file == null || file.isEmpty()) {
            result.put("ret", false);
            result.put("errorMsg", "非法请求，请选择需要上传的文件");
            return result;
        }
        String originFileName = file.getOriginalFilename();
        if (!checkFileType(originFileName)) {
            result.put("ret", false);
            result.put("errorMsg", "非法文件类型，只允许上传Excel文件");
            return result;
        }
        try {
            LOGGER.info("用户上传ZIP文件fileName={} ----------开始处理", originFileName);
            StringBuffer buffer = new StringBuffer();
            buffer.append(MapConfig.getString(GlobalConstants.KEY_LOCAL_STORE_DIR, GlobalConstants.GLOBAL_CONFIG, "/data/app"));
            buffer.append(File.separator);
            buffer.append(new Date().getTime());
            String localDirPath = buffer.toString();
            File localDir = new File(localDirPath);
            if (!localDir.exists()) {
                localDir.mkdirs();
            }
            buffer.append(File.separator);
            buffer.append(originFileName);
            File localFile = new File(buffer.toString());
            ByteStreams.copy(file.getInputStream(), Files.newOutputStreamSupplier(localFile));
            LOGGER.info("用户上传ZIP文件fileName={},保存到本地成功,路径为{}", localFile.getAbsolutePath());
            boolean ret = imeiUploadService.processCsvData(localFile);
            result.put("ret", ret);
            if (!ret) {
                result.put("errorMsg", "处理上传文件失败，请检查文件格式是否正确或者重新操作");
            }
        } catch (Exception e) {
            result.put("ret", false);
            result.put("errorMsg", "处理上传文件失败，请重新操作");
            LOGGER.error("list error ", e);
        } finally {
            LOGGER.info("list:returnObj={}", result);
        }

        return result;
    }


    public boolean checkFileType(String fileName) {
        if (StringUtils.endsWithIgnoreCase(fileName, ".zip")) {
            return true;
        }

        return false;
    }

}
