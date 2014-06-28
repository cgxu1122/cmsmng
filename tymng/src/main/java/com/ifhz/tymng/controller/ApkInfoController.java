package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.codec.AnalysisApk;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.commons.util.FtpUtils;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.ApkInfo;
import com.ifhz.core.service.pkgmng.ApkInfoService;
import com.ifhz.core.util.MD5keyUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/tymng/apkInfo")
public class ApkInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApkInfoController.class);
    @Autowired
    private ApkInfoService apkInfoService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("apkInfo/index");
    }

    @RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject list(HttpServletRequest request) {
        /**分页*/
        String pageNum = request.getParameter("page");
        String pageSize = request.getParameter("rows");
        Pagination page = new Pagination();
        if (!StringUtils.isEmpty(pageNum)) page.setCurrentPage(Integer.valueOf(pageNum));
        if (!StringUtils.isEmpty(pageSize)) page.setPageSize(Integer.valueOf(pageSize));
        //查询条件
        String apkNameCondition = request.getParameter("apkNameCondition");
        ApkInfo bi = new ApkInfo();
        bi.setActive(JcywConstants.ACTIVE_Y);
        bi.setApkNameCondition(apkNameCondition);
        List<ApkInfo> list = apkInfoService.queryByVo(page, bi);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/insert", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject insert(HttpServletRequest request) {
        Map<String, FileItem> params = paserMultiData(request);
        String apkName = null;
        String type = null;
        FileItem file = params.get("file");
        String errorMsg = null;
        JSONObject result = new JSONObject();
        if (StringUtils.isEmpty(file.getName())) {
            errorMsg = "请上传Apk文件！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        String softName = file.getName();
        String extName = softName.substring(softName.lastIndexOf(".") + 1, softName.length());
        if (!"apk".equals(extName)) {
            errorMsg = "请上传后缀名为Apk的文件！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        String md5Value = null;
        String dir = GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_APKDIR).replace("{0}", String.valueOf(Calendar.getInstance().getTimeInMillis()));
        try {
            apkName = readInputStreamData(params.get("apkName").getInputStream());
            type = readInputStreamData(params.get("type").getInputStream());
            FtpUtils.ftpUpload(file.getInputStream(),
                    dir,
                    softName
            );
            md5Value = MD5keyUtil.getMD5(file.getInputStream());
        } catch (Exception e) {
            errorMsg = "上传文件出错，请重新上传或者联系管理员！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        if (StringUtils.isEmpty(apkName) || apkName.length() > 50) {
            errorMsg = "请正确输入产品名称！";
        }
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        //产品名称唯一性校验
        ApkInfo ai = new ApkInfo();
        ai.setApkName(apkName.trim());
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(1);
        List<ApkInfo> list = apkInfoService.queryByVo(page, ai);
        if (list != null && list.size() > 0) {
            errorMsg = "产品名称重复，请重新输入！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        try {
            String packagePath = AnalysisApk.genApkPackageName(file.getInputStream());
            ai.setPackagePath(packagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ai.setApkName(apkName.trim());
        ai.setSoftName(softName);
        ai.setActive(JcywConstants.ACTIVE_Y);
        ai.setFtpPath(GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_DOWNLOADURL) + dir + softName);
        ai.setMd5Value(md5Value);
        ai.setType(type);
        apkInfoService.insert(ai);
        result.put("msg", "添加成功!");
        return result;
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject update(HttpServletRequest request) {
        Map<String, FileItem> params = paserMultiData(request);
        String apkId = null;
        String apkName = null;
        String type = null;
        String errorMsg = null;
        JSONObject result = new JSONObject();
        try {
            apkId = readInputStreamData(params.get("apkId").getInputStream());
            apkName = readInputStreamData(params.get("apkName").getInputStream());
            type = readInputStreamData(params.get("type").getInputStream());
        } catch (IOException e) {
            errorMsg = "数据读取错误，请联系管理员！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        if (StringUtils.isEmpty(apkId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(apkName) || apkName.length() > 50) {
            errorMsg = "请正确输入产品名称！";
        }
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        ApkInfo apkInfo = apkInfoService.getById(Long.parseLong(apkId));
        if (apkInfo == null) {
            result.put("errorMsg", "数据已被删除，请刷新!");
            return result;
        }
        //产品名称唯一性校验
        ApkInfo ai = new ApkInfo();
        ai.setApkName(apkName.trim());
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(1);
        List<ApkInfo> list = apkInfoService.queryByVo(page, ai);
        if (list != null && list.size() > 0) {
            for (ApkInfo repeatVersionAi : list) {
                if (repeatVersionAi.getApkId() != apkInfo.getApkId()) {
                    result.put("errorMsg", "产品名称重复，请重新输入！");
                    return result;
                }
            }
        }
        FileItem file = params.get("file");
        if (StringUtils.isNotEmpty(file.getName())) {
            try {
                String softName = file.getName();
                String extName = softName.substring(softName.lastIndexOf(".") + 1, softName.length());
                if (!"apk".equals(extName)) {
                    errorMsg = "请上传后缀名为Apk的文件！";
                    result.put("errorMsg", errorMsg);
                    return result;
                }
                String dir = GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_APKDIR).replace("{0}", String.valueOf(Calendar.getInstance().getTimeInMillis()));
                FtpUtils.ftpUpload(file.getInputStream(),
                        dir,
                        softName
                );
                apkInfo.setSoftName(softName);
                apkInfo.setFtpPath(GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_DOWNLOADURL) + dir + softName);
                if (!MD5keyUtil.getMD5(file.getInputStream()).equals(apkInfo.getMd5Value())) {
                    apkInfo.setMd5Value(MD5keyUtil.getMD5(file.getInputStream()));
                    apkInfo.setUpdateTime(new Date());
                }
                String packagePath = AnalysisApk.genApkPackageName(file.getInputStream());
                apkInfo.setPackagePath(packagePath);
            } catch (Exception e) {
                errorMsg = "上传文件出错，请重新上传或者联系管理员！";
                result.put("errorMsg", errorMsg);
                return result;
            }
        }
        apkInfo.setApkName(apkName.trim());
        apkInfo.setType(type);
        apkInfoService.update(apkInfo);
        result.put("msg", "修改成功!");
        return result;
    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject delete(HttpServletRequest request) {
        String apkId = request.getParameter("apkId");
        String errorMsg = null;
        if (StringUtils.isEmpty(apkId)) {
            errorMsg = "系统错误，请联系管理员！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        ApkInfo ai = apkInfoService.getById(Long.parseLong(apkId));
        if (ai == null) {
            result.put("errorMsg", "数据已被其他人操作，请刷新!");
        } else {
            apkInfoService.delete(ai);
            result.put("msg", "删除成功!");
        }
        return result;
    }

}