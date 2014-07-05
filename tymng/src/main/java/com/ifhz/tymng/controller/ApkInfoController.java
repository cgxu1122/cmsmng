package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.base.commons.codec.AnalysisApkFile;
import com.ifhz.core.base.commons.codec.DesencryptUtils;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.commons.util.FtpUtils;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.ApkInfo;
import com.ifhz.core.service.pkgmng.ApkInfoService;
import org.apache.commons.collections.CollectionUtils;
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
import java.io.File;
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
        long start = System.currentTimeMillis();
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
        File localFile = null;
        String dir = GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_APKDIR).replace("{0}", String.valueOf(Calendar.getInstance().getTimeInMillis()));
        try {
            localFile = storeLocalFile(params, softName);
            md5Value = DesencryptUtils.md5File(localFile);
            apkName = readInputStreamData(params.get("apkName").getInputStream());
            type = readInputStreamData(params.get("type").getInputStream());
            if (StringUtils.isEmpty(apkName) || apkName.length() > 50) {
                errorMsg = "请正确输入产品名称！";
                result.put("errorMsg", errorMsg);
                return result;
            }
            //产品名称唯一性校验
            boolean isRepeat = isRepeat(Type.Insert, null, apkName);
            if (isRepeat) {
                result.put("errorMsg", "产品名称重复，请重新输入！");
                return result;
            }

            FtpUtils.ftpUpload(file.getInputStream(), dir, softName);
        } catch (Exception e) {
            errorMsg = "上传文件出错，请重新上传或者联系管理员！";
            result.put("errorMsg", errorMsg);
            LOGGER.error("insertApkInfo error", e);
            return result;
        }
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }

        try {
            ApkInfo po = new ApkInfo();
            po.setApkName(apkName.trim());
            if (localFile != null) {
                String packagePath = AnalysisApkFile.parseApk(localFile);
                if (StringUtils.isNotBlank(packagePath)) {
                    po.setPackagePath(packagePath);
                }
            }
            po.setApkName(apkName.trim());
            po.setSoftName(softName);
            po.setActive(JcywConstants.ACTIVE_Y);
            po.setFtpPath(dir + softName);
            po.setDownloadUrl(GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_DOWNLOADURL) + dir + softName);
            po.setMd5Value(md5Value);
            po.setType(type);
            apkInfoService.insert(po);
            result.put("msg", "添加成功!");
        } catch (Exception e) {
            result.put("msg", "添加失败，请联系管理员");
        } finally {
            long end = System.currentTimeMillis();
            LOGGER.info("parseApkFile totalTime={}", end - start);
            deleteLocalFile(localFile);
        }


        return result;
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject update(HttpServletRequest request) {
        LOGGER.info("update ApkInfo  --------------------------start");
        long start = System.currentTimeMillis();
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
            LOGGER.info("update ApkInfo  process step 1--------------------------");
        } catch (Exception e) {
            errorMsg = "数据读取错误，请重新操作！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        if (StringUtils.isEmpty(apkId)) {
            errorMsg = "系统错误，请重新操作！";
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
        boolean isRepeat = isRepeat(Type.Update, apkInfo.getApkId(), apkName);
        if (isRepeat) {
            result.put("errorMsg", "产品名称重复，请重新输入！");
            return result;
        }
        LOGGER.info("update ApkInfo  process step 2--------------------------");
        File localFile = null;
        String md5Value = null;
        FileItem file = params.get("file");
        String softName = file.getName();
        String extName = softName.substring(softName.lastIndexOf(".") + 1, softName.length());
        if (!"apk".equals(extName)) {
            errorMsg = "请上传后缀名为Apk的文件！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        String dir = GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_APKDIR).replace("{0}", String.valueOf(Calendar.getInstance().getTimeInMillis()));
        if (StringUtils.isNotEmpty(file.getName())) {
            try {
                FtpUtils.ftpUpload(file.getInputStream(), dir, softName);
                localFile = storeLocalFile(params, softName);
                md5Value = DesencryptUtils.md5File(localFile);
            } catch (Exception e) {
                errorMsg = "上传文件出错，请重新上传或者联系管理员！";
                result.put("errorMsg", errorMsg);
                return result;
            } finally {
                long end = System.currentTimeMillis();
                LOGGER.info("parseApkFile totalTime={}", end - start);
                deleteLocalFile(localFile);
            }
        }
        LOGGER.info("update ApkInfo  process step 3--------------------------");
        try {
            if (!StringUtils.equalsIgnoreCase(md5Value, apkInfo.getMd5Value())) {
                apkInfo.setMd5Value(md5Value);
                apkInfo.setUpdateTime(new Date());
            }
            if (localFile != null) {
                String packagePath = AnalysisApkFile.parseApk(localFile);
                if (StringUtils.isNotBlank(packagePath)) {
                    apkInfo.setPackagePath(packagePath);
                }
            }
            apkInfo.setSoftName(softName);
            apkInfo.setFtpPath(dir + softName);
            apkInfo.setDownloadUrl(GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_DOWNLOADURL) + dir + softName);
            apkInfo.setApkName(apkName.trim());
            apkInfo.setType(type);
            apkInfoService.update(apkInfo);
            result.put("msg", "修改成功!");
        } catch (Exception e) {
            LOGGER.error("updateApkInfo error", e);
            result.put("msg", "修改失败，请联系管理员");
        } finally {
            LOGGER.info("update ApkInfo  process step 4--------------------------");
            long end = System.currentTimeMillis();
            LOGGER.info("parseApkFile totalTime={}", end - start);
            deleteLocalFile(localFile);
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
            result.put("msg", "删除成功!");
        }
        return result;
    }


    private File storeLocalFile(Map<String, FileItem> params, String softName) throws Exception {
        FileItem file = params.get("file");
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
        buffer.append(softName);

        File localFile = new File(buffer.toString());
        ByteStreams.copy(file.getInputStream(), Files.newOutputStreamSupplier(localFile));

        return localFile;
    }

    private enum Type {
        Insert, Update;
    }


    private boolean isRepeat(Type type, Long apkId, String apkName) {
        ApkInfo temp = new ApkInfo();
        temp.setApkName(apkName.trim());
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(1);
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