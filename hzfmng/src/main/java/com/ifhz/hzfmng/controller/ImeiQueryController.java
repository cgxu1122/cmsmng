package com.ifhz.hzfmng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ifhz.core.base.commons.excel.ExcelHandle;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.service.imei.ImeiQueryService;
import com.ifhz.core.service.imei.bean.DataLogResult;
import com.ifhz.core.utils.FileHandle;
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
import java.util.List;
import java.util.UUID;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 14:47
 */
@Controller
@RequestMapping("/hzfmng/imeiQuery")
public class ImeiQueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImeiQueryController.class);

    @Resource(name = "imeiQueryService")
    private ImeiQueryService imeiQueryService;
    @Resource(name = "localDirCacheService")
    private LocalDirCacheService localDirCacheService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("imeiQuery/index");
    }


    @RequestMapping(value = "/list.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject list(@RequestParam(value = "excelFile", required = false) MultipartFile file,
                    HttpServletRequest request) {
        JSONObject result = new JSONObject();
        if (file == null || file.isEmpty()) {
            result.put("ret", true);
            result.put("rows", Lists.newArrayList());
            return result;
        }
        String originFileName = file.getOriginalFilename();
        if (!checkFileType(originFileName)) {
            result.put("ret", false);
            result.put("errorMsg", "非法文件类型，只允许上传Excel文件");
            return result;
        }

        try {
            LOGGER.info("用户上传imei查询文件fileName={} ----------开始处理", originFileName);
            String fileExt = FileHandle.getFileExt(originFileName);
            String newFileName = UUID.randomUUID() + "." + fileExt.toLowerCase();
            String toFilePath = localDirCacheService.storeTempFile(file.getInputStream(), newFileName);
            if (StringUtils.isBlank(toFilePath)) {
                throw new Exception("文件保存到本地失败！！！");
            }
            LOGGER.info("用户上传imei查询文件fileName={},保存到本地成功,路径为{}", toFilePath);
            List<String> imeiList = ExcelHandle.readImeiListFromExcel(toFilePath);
            LOGGER.info("用户上传imei查询文件fileName={},从Excel解析ImeiList={}", JSON.toJSONString(imeiList));
            List<DataLogResult> dataLogResultList = imeiQueryService.queryListByImeiList(imeiList);

            result.put("ret", true);
            result.put("rows", dataLogResultList);
        } catch (Exception e) {
            result.put("ret", false);
            result.put("errorMsg", "查询任务失败，请重新操作");
            LOGGER.error("list error ", e);
        } finally {
            LOGGER.info("list:returnObj={}", result);
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

}
