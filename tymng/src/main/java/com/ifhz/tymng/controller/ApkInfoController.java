package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.base.commons.codec.AnalysisApkFile;
import com.ifhz.core.base.commons.codec.DesencryptUtils;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.ApkInfo;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.service.pkgmng.ApkInfoService;
import com.ifhz.core.utils.HostsHandle;
import com.ifhz.core.utils.PatternUtils;
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
import java.io.FileInputStream;
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

    private static final String CountPkgPath = "com.chris.apkmonitor/com.chris.apkmonitor.MainActivity";
    private static final String Store_APK_Path = MapConfig.getString(GlobalConstants.KEY_STORE_APK_DIR, GlobalConstants.GLOBAL_CONFIG, "/upload");


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

    @RequestMapping(value = "/chooseList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject chooseList(HttpServletRequest request) {
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
        List<ApkInfo> list = apkInfoService.queryChooseListByVo(page, bi);
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
        String originFileName = file.getOriginalFilename();
        LOGGER.info("rev msg apkName={},type={},originalFilename={}", apkName, type, originFileName);
        JSONObject result = new JSONObject();
        String md5Value = null;
        if (StringUtils.isBlank(apkName) || apkName.length() > 50 || StringUtils.isBlank(type) || StringUtils.isBlank(originFileName)) {
            result.put("errorMsg", "表单参数填写有误,请重新填写并提交！");
            return result;
        }
        if (!StringUtils.endsWithIgnoreCase(originFileName.trim(), ".apk")) {
            result.put("errorMsg", "请上传后缀名为Apk的文件");
            return result;
        }
        if (!PatternUtils.verifyApkSoftName(originFileName)) {
            result.put("errorMsg", "Apk的文件名称不合法");
            return result;
        }
        //产品名称唯一性校验
        boolean isRepeat = isRepeat(Type.Insert, null, apkName);
        if (isRepeat) {
            result.put("errorMsg", "产品名称重复，请重新输入！");
            return result;
        }
        if (StringUtils.startsWith(type, "3")) {
            ApkInfo temp = new ApkInfo();
            temp.setType("3");
            Pagination page = new Pagination();
            page.setCurrentPage(1);
            page.setPageSize(2);
            List<ApkInfo> list = apkInfoService.queryByVo(page, temp);
            if (CollectionUtils.isNotEmpty(list)) {
                result.put("errorMsg", "安装进度Apk已经存在，系统内只允许存在一个。");
                return result;
            }
        }

        try {
            String storeLocalFilePath = localDirCacheService.storeApkFile(file.getInputStream(), originFileName);
            if (StringUtils.isBlank(storeLocalFilePath)) {
                throw new Exception("上传文件保存出错，请重新操作");
            }
            long newSize = getFileSizes(new File(storeLocalFilePath));
            LOGGER.info("origin File Size = {}", file.getSize());
            LOGGER.info("new    File Size = {}", newSize);
            if (file.getSize() == newSize) {
                md5Value = DesencryptUtils.md5File(new File(storeLocalFilePath));
                ApkInfo po = new ApkInfo();
                po.setApkName(apkName.trim());
                String packagePath = null;
                if (StringUtils.containsIgnoreCase(type, "2")) {
                    packagePath = CountPkgPath;
                } else {
                    packagePath = AnalysisApkFile.parseApk(storeLocalFilePath);
                }
                LOGGER.info("packagePath={}", packagePath);
                if (StringUtils.isNotBlank(packagePath)) {
                    po.setPackagePath(packagePath);
                }

                String path = StringUtils.replace(storeLocalFilePath, "\\\\+", "\\");
                path = StringUtils.replace(path, "\\", "/");
                path = StringUtils.replace(path, Store_APK_Path, "");
                path = StringUtils.replace(path, "D:/upload", "");
                po.setApkName(apkName.trim());
                po.setSoftName(originFileName.trim());
                po.setActive(JcywConstants.ACTIVE_Y);
                po.setFtpPath(path.trim());
                po.setDownloadUrl(path.trim());
                po.setMd5Value(md5Value);
                po.setType(type);
                po.setFileUpdateTime(new Date());
                apkInfoService.insert(po);
                result.put("msg", "添加成功!");
            } else {
                result.put("errorMsg", "保存失败，请重新上传！");
            }
        } catch (Exception e) {
            LOGGER.error("insert DeviceSystem error", e);
            result.put("errorMsg", "保存失败，请重新上传或者联系管理员！");
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
        if (apkInfo == null) {
            result.put("errorMsg", "数据已被删除，请刷新!");
            return result;
        }

        if (StringUtils.startsWith(type, "3")) {
            if (!StringUtils.equalsIgnoreCase(apkInfo.getType(), type)) {
                result.put("errorMsg", "不允许将其他类型Apk修改为安装进度Apk，如有必要请新增[安装进度Apk]");
                return result;
            }
        }

        try {
            if (StringUtils.isNotBlank(originFileName)) {
                if (!StringUtils.endsWithIgnoreCase(originFileName.trim(), ".apk")) {
                    result.put("errorMsg", "请上传后缀名为Apk的文件");
                    return result;
                }
                if (!PatternUtils.verifyApkSoftName(originFileName)) {
                    result.put("errorMsg", "Apk的文件名称不合法");
                    return result;
                }
                String storeLocalFilePath = localDirCacheService.storeApkFile(file.getInputStream(), originFileName);
                if (StringUtils.isBlank(storeLocalFilePath)) {
                    throw new Exception("上传文件保存出错，请重新操作");
                }
                long newSize = getFileSizes(new File(storeLocalFilePath));
                LOGGER.info("origin File Size = {}", file.getSize());
                LOGGER.info("new    File Size = {}", newSize);
                if (file.getSize() == newSize) {
                    md5Value = DesencryptUtils.md5File(new File(storeLocalFilePath));
                    String packagePath;
                    if (StringUtils.containsIgnoreCase(type, "2")) {
                        packagePath = CountPkgPath;
                    } else {
                        packagePath = AnalysisApkFile.parseApk(storeLocalFilePath);
                    }
                    LOGGER.info("packagePath={}", packagePath);
                    if (StringUtils.isNotBlank(packagePath)) {
                        apkInfo.setPackagePath(packagePath);
                    }

                    String path = StringUtils.replace(storeLocalFilePath, "\\\\+", "\\");
                    path = StringUtils.replace(path, "\\", "/");
                    path = StringUtils.replace(path, Store_APK_Path, "");
                    path = StringUtils.replace(path, "D:/upload", "");

                    apkInfo.setSoftName(originFileName);
                    apkInfo.setFtpPath(path);
                    apkInfo.setDownloadUrl(path);
                    apkInfo.setMd5Value(md5Value);
                    apkInfo.setFileUpdateTime(new Date());
                } else {
                    result.put("errorMsg", "保存失败，请重新上传！");
                    return result;
                }
            }

            apkInfo.setApkName(apkName.trim());
            apkInfo.setType(type);
            apkInfoService.update(apkInfo);

            result.put("msg", "修改成功!");
        } catch (Exception e) {
            LOGGER.error("updateApkInfo error", e);
            result.put("errorMsg", "保存失败，请重新上传或者联系管理员！");
        }

        return result;
    }

    public long getFileSizes(File file) throws Exception {//取得文件大小
        return new FileInputStream(file).available();
    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject delete(@RequestParam(value = "apkId", required = true) Long apkId,
                      HttpServletRequest request) {

        JSONObject result = null;
        try {
            LOGGER.info("apkId={}", apkId);
            String errorMsg = null;
            if (apkId == null) {
                errorMsg = "系统错误，请联系管理员！";
            }
            result = new JSONObject();
            if (!StringUtils.isEmpty(errorMsg)) {
                result.put("errorMsg", errorMsg);
                return result;
            }
            ApkInfo ai = apkInfoService.getById(apkId);
            if (ai == null) {
                result.put("errorMsg", "数据已被其他人操作，请刷新!");
            } else {
                if (StringUtils.startsWith(ai.getType(), "3")) {
                    result.put("errorMsg", "不允许删除安装进度Apk");
                    return result;
                }
                apkInfoService.delete(ai);
                result.put("msg", "删除成功!");
            }
        } catch (Exception e) {
            LOGGER.error("deleteApkIno error", e);
        }
        return result;
    }

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
                    if (!repeatVersionAi.getApkId().equals(apkId)) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }

        return false;
    }
}