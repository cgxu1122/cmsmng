package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.codec.AnalysisApkFile;
import com.ifhz.core.base.commons.codec.DesencryptUtils;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.commons.util.FtpUtils;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.ApkInfo;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.service.pkgmng.ApkInfoService;
import com.ifhz.core.utils.HostsHandle;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/tymng/apkInfo")
public class ApkInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApkInfoController.class);
    @Autowired
    private ApkInfoService apkInfoService;
    @Resource(name = "localDirCacheService")
    private LocalDirCacheService localDirCacheService;

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
        if (CollectionUtils.isNotEmpty(list)) {
            for (ApkInfo apkInfo : list) {
                if (StringUtils.isNotBlank(apkInfo.getDownloadUrl())) {
                    apkInfo.setDownloadUrl(HostsHandle.getHostPrefix() + apkInfo.getDownloadUrl());
                }
            }
        }
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/insert", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject insert(@RequestParam(value = "file", required = true) MultipartFile file,
                             @RequestParam(value = "apkName", required = true) String apkName,
                             @RequestParam(value = "type", required = true) String type,
                             HttpServletRequest request) {
        long start = System.currentTimeMillis();
        String originFileName = file.getOriginalFilename();
        LOGGER.info("rev msg apkName={},type={},originalFilename={}", apkName, type, originFileName);
        JSONObject result = new JSONObject();
        String md5Value = null;
        String dir = GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_DEVICEDIR).replace("{0}", String.valueOf(Calendar.getInstance().getTimeInMillis()));
        if (StringUtils.isBlank(apkName) || apkName.length() > 50 || StringUtils.isBlank(type) || StringUtils.isBlank(originFileName)) {
            result.put("errorMsg", "表单参数填写有误,请重新填写并提交！");
            return result;
        }
        if (!StringUtils.endsWithIgnoreCase(originFileName.trim(), ".apk")) {
            result.put("errorMsg", "请上传后缀名为Apk的文件");
            return result;
        }
        //产品名称唯一性校验
        boolean isRepeat = isRepeat(Type.Insert, null, apkName);
        if (isRepeat) {
            result.put("errorMsg", "产品名称重复，请重新输入！");
            return result;
        }
        String storeLocalFilePath = null;
        try {
            String newFileName = localDirCacheService.getLocalFileName(originFileName);
            storeLocalFilePath = localDirCacheService.storeTempFile(file.getInputStream(), newFileName);
            if (StringUtils.isBlank(storeLocalFilePath)) {
                throw new Exception("上传文件保存出错，请重新操作");
            }
            md5Value = DesencryptUtils.md5File(new File(storeLocalFilePath));
            FtpUtils.ftpUpload(file.getInputStream(), dir, originFileName);
        } catch (Exception e) {
            LOGGER.error("insert DeviceSystem error", e);
            result.put("errorMsg", "上传文件出错，请重新上传或者联系管理员！");
            return result;
        }

        try {
            ApkInfo po = new ApkInfo();
            po.setApkName(apkName.trim());
            String packagePath = AnalysisApkFile.parseApk(storeLocalFilePath);
            if (StringUtils.isNotBlank(packagePath)) {
                po.setPackagePath(packagePath);
            }
            po.setApkName(apkName.trim());
            po.setSoftName(originFileName.trim());
            po.setActive(JcywConstants.ACTIVE_Y);
            po.setFtpPath(dir + originFileName.trim());
            po.setDownloadUrl(dir + originFileName.trim());
            po.setMd5Value(md5Value);
            po.setType(type);
            apkInfoService.insert(po);
            result.put("msg", "添加成功!");
        } catch (Exception e) {
            result.put("msg", "添加失败，请联系管理员");
        } finally {
            long end = System.currentTimeMillis();
            LOGGER.info("parseApkFile totalTime={}", end - start);
        }

        return result;
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject update(@RequestParam(value = "file", required = false) MultipartFile file,
                             @RequestParam(value = "apkName", required = true) String apkName,
                             @RequestParam(value = "type", required = true) String type,
                             @RequestParam(value = "apkId", required = true) Long apkId,
                             HttpServletRequest request) {
        long start = System.currentTimeMillis();
        String originFileName = file.getOriginalFilename();
        LOGGER.info("rev msg apkName={},type={},originalFilename={},apkId={}", apkName, type, originFileName, apkId);
        JSONObject result = new JSONObject();
        String md5Value = null;
        String dir = GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_DEVICEDIR).replace("{0}", String.valueOf(Calendar.getInstance().getTimeInMillis()));
        if (StringUtils.isBlank(apkName) || apkName.length() > 50 || StringUtils.isBlank(type) || apkId == null) {
            result.put("errorMsg", "表单参数填写有误,请重新填写并提交！");
            return result;
        }
        //产品名称唯一性校验
        boolean isRepeat = isRepeat(Type.Update, apkId, apkName);
        if (isRepeat) {
            result.put("errorMsg", "产品名称重复，请重新输入！");
            return result;
        }
        ApkInfo apkInfo = apkInfoService.getById(apkId);
        String originFtpPath = apkInfo.getFtpPath();
        if (apkInfo == null) {
            result.put("errorMsg", "数据已被删除，请刷新!");
            return result;
        }
        String storeLocalFilePath = null;
        if (StringUtils.isNotBlank(originFileName)) {
            if (!StringUtils.endsWithIgnoreCase(originFileName.trim(), ".apk")) {
                result.put("errorMsg", "请上传后缀名为Apk的文件");
                return result;
            }
            try {
                String newFileName = localDirCacheService.getLocalFileName(originFileName);
                storeLocalFilePath = localDirCacheService.storeTempFile(file.getInputStream(), newFileName);
                if (StringUtils.isBlank(storeLocalFilePath)) {
                    throw new Exception("上传文件保存出错，请重新操作");
                }
                md5Value = DesencryptUtils.md5File(new File(storeLocalFilePath));
                FtpUtils.ftpUpload(file.getInputStream(), dir, originFileName);
            } catch (Exception e) {
                LOGGER.error("insert DeviceSystem error", e);
                result.put("errorMsg", "上传文件出错，请重新上传或者联系管理员！");
                return result;
            }
        }

        try {
            if (!StringUtils.equalsIgnoreCase(md5Value, apkInfo.getMd5Value())) {
                apkInfo.setMd5Value(md5Value);
                apkInfo.setUpdateTime(new Date());
            }
            if (StringUtils.isNotBlank(originFileName)) {
                String packagePath = AnalysisApkFile.parseApk(storeLocalFilePath);
                if (StringUtils.isNotBlank(packagePath)) {
                    apkInfo.setPackagePath(packagePath);
                }
                apkInfo.setSoftName(originFileName);
                apkInfo.setFtpPath(dir + originFileName);
                apkInfo.setDownloadUrl(dir + originFileName);

            }
            apkInfo.setApkName(apkName.trim());
            apkInfo.setType(type);
            apkInfoService.update(apkInfo);

            if (StringUtils.isNotBlank(originFtpPath)) {
                try {
                    FtpUtils.ftpDelete(originFtpPath);
                } catch (Exception e) {
                }
            }
            result.put("msg", "修改成功!");
        } catch (Exception e) {
            LOGGER.error("updateApkInfo error", e);
            result.put("msg", "修改失败，请联系管理员");
        } finally {
            LOGGER.info("update ApkInfo  process step 4--------------------------");
            long end = System.currentTimeMillis();
            LOGGER.info("update ApkInfo totalTime={}", end - start);
        }

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
            if (StringUtils.isNotBlank(ai.getFtpPath())) {
                try {
                    FtpUtils.ftpDelete(ai.getFtpPath());
                } catch (Exception e) {
                }
            }
            result.put("msg", "删除成功!");
        }
        return result;
    }

/*
    private String storeLocalFile(Map<String, FileItem> params, String originFileName) throws Exception {
        FileItem file = params.get("file");
        String fileExt = FileHandle.getFileExt(originFileName);
        String newFileName = UUID.randomUUID() + "." + fileExt.toLowerCase();
        String toFilePath = localDirCacheService.storeTempFile(file.getInputStream(), newFileName);
        if (StringUtils.isBlank(toFilePath)) {
            throw new Exception("文件保存到本地失败！！！");
        }

        return toFilePath;
    }*/

    private enum Type {
        Insert, Update;
    }


    private boolean isRepeat(Type type, Long apkId, String apkName) {
        ApkInfo temp = new ApkInfo();
        temp.setApkName(apkName.trim());
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(2);
        List<ApkInfo> list = apkInfoService.queryByVo(page, temp);
        if (CollectionUtils.isNotEmpty(list)) {
            if (type == Type.Update) {
                for (ApkInfo repeatVersionAi : list) {
                    if (repeatVersionAi.getApkId() != apkId) {
                        return true;
                    }
                }
            } else {
                return true;
            }

        }

        return false;
    }

    private void deleteLocalFile(File localFile) {
        if (localFile != null) {
            try {
                localFile.deleteOnExit();
                localFile.delete();
            } catch (Exception e) {
            }
        }
    }

}