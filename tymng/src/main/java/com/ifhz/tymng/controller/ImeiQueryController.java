package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ifhz.core.base.commons.excel.ExcelHandle;
import com.ifhz.core.base.commons.util.ExportDataUtil;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.service.export.model.BaseExportModel;
import com.ifhz.core.service.imei.ImeiQueryService;
import com.ifhz.core.service.imei.bean.DataLogResult;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 14:47
 */
@Controller
@RequestMapping("/tymng/imeiQuery")
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
        String originFileName = file.getOriginalFilename();
        if (file.isEmpty() || StringUtils.isBlank(originFileName)) {
            result.put("ret", true);
            result.put("rows", Lists.newArrayList());
            return result;
        }
        if (!checkFileType(originFileName)) {
            result.put("ret", false);
            result.put("errorMsg", "非法文件类型，只允许上传Excel文件");
            return result;
        }

        try {
            LOGGER.info("用户上传imei查询文件fileName={} ----------开始处理", originFileName);
            String newFileName = localDirCacheService.getLocalFileName(originFileName);
            String toFilePath = localDirCacheService.storeTempFile(file.getInputStream(), newFileName);
            LOGGER.info("用户上传imei查询文件fileName={},保存到本地成功,路径为{}", toFilePath);
            boolean checkRowNum = ExcelHandle.checkRowNumFromExcel(toFilePath, ExcelHandle.Type.ImeiList);
            if (!checkRowNum) {
                result.put("ret", false);
                result.put("errorMsg", "Excel文件最大支持3000条,请上传正确的文件");
                return result;
            }
            Set<String> imeiSet = ExcelHandle.readImeiListFromExcel(toFilePath);
            LOGGER.info("用户上传imei查询文件fileName={},从Excel解析imeiSet={}", JSON.toJSONString(imeiSet));
            List<DataLogResult> dataLogResultList = imeiQueryService.queryListByImeiList(imeiSet);

            result.put("ret", true);
            result.put("imeiPath", toFilePath);
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

    @RequestMapping(value = "/exportData.do", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject exportData(@RequestParam(value = "imeiPath") String imeiPath,
                          HttpServletRequest request) {
        LOGGER.info("rev msg imeiPath={}", imeiPath);
        JSONObject result = new JSONObject();
        if (StringUtils.isBlank(imeiPath)) {
            result.put("ret", false);
            result.put("errorMsg", "请求错误，请先导入imei文件查询再导出");
            return result;
        }

        try {
            LOGGER.info("用户导出Imei文件 imeiPath={} ----------开始处理", imeiPath);
            Set<String> imeiSet = ExcelHandle.readImeiListFromExcel(imeiPath);
            LOGGER.info("用户上传imei查询文件fileName={},从Excel解析imeiSet={}", JSON.toJSONString(imeiSet));
            List<DataLogResult> dataLogResultList = imeiQueryService.queryListByImeiList(imeiSet);

            BaseExportModel exportModel = new BaseExportModel();
            Map<String, String> titleMap = new LinkedHashMap<String, String>();
            titleMap.put("imei", "imei");
            titleMap.put("modelName", "机型全称");
            titleMap.put("channelName", "仓库名称");
            titleMap.put("processTime", "安装日期");
            exportModel.setTitleMap(titleMap);
            exportModel.setDataList(dataLogResultList);
            String localFilePath = localDirCacheService.getExcelTempPath();
            ExportDataUtil.writeData(exportModel, new File(localFilePath));
            result.put("ret", true);
            result.put("path", localFilePath);
        } catch (Exception e) {
            result.put("ret", false);
            result.put("errorMsg", "文件导出失败，请重试或者联系管理员");
            LOGGER.error("exportData", e);
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
