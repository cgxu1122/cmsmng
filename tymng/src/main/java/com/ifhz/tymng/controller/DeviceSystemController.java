package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.base.commons.codec.DesencryptUtils;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.DeviceSystem;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.service.device.DeviceSystemService;
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
import java.util.Date;
import java.util.List;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/tymng/deviceSystem")
public class DeviceSystemController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSystemController.class);
    @Autowired
    private DeviceSystemService deviceSystemService;
    @Resource(name = "localDirCacheService")
    private LocalDirCacheService localDirCacheService;

    private static final String Store_APK_Path = MapConfig.getString(GlobalConstants.KEY_STORE_APK_DIR, GlobalConstants.GLOBAL_CONFIG, "/upload");

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("deviceSystem/index");
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
        String versionCondition = request.getParameter("versionCondition");
        DeviceSystem ds = new DeviceSystem();
        ds.setVersionCondition(versionCondition);
        List<DeviceSystem> list = deviceSystemService.queryByVo(page, ds);
        if (CollectionUtils.isNotEmpty(list)) {
            for (DeviceSystem deviceSystem : list) {
                if (StringUtils.isNotBlank(deviceSystem.getDownloadUrl())) {
                    deviceSystem.setDownloadUrl(HostsHandle.getHostPrefix() + deviceSystem.getDownloadUrl());
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
    public JSONObject insert(@RequestParam(value = "version") String version,
                             @RequestParam(value = "effectiveTime") String effectiveTime,
                             @RequestParam(value = "file") MultipartFile file,
                             HttpServletRequest request) {
        String originFileName = file.getOriginalFilename();
        LOGGER.info("rev msg version={},effectiveTime={},originalFilename={}", version, effectiveTime, originFileName);
        JSONObject result = new JSONObject();
        String md5Value = null;
        if (StringUtils.isBlank(version) || version.length() > 50 || StringUtils.isBlank(effectiveTime) || StringUtils.isBlank(originFileName)) {
            result.put("errorMsg", "表单参数填写有误,请重新填写并提交！");
            return result;
        }
        try {
            String storeLocalFilePath = localDirCacheService.storeApkFile(file.getInputStream(), originFileName);
            if (StringUtils.isBlank(storeLocalFilePath)) {
                throw new Exception("上传文件保存出错，请重新操作");
            }
            md5Value = DesencryptUtils.md5File(new File(storeLocalFilePath));

            //版本号唯一性校验
            DeviceSystem ds = new DeviceSystem();
            ds.setVersion(version.trim());
            Pagination page = new Pagination();
            page.setCurrentPage(1);
            page.setPageSize(1);
            List<DeviceSystem> list = deviceSystemService.queryByVo(page, ds);
            if (list != null && list.size() > 0) {
                result.put("errorMsg", "版本号重复，请重新输入！");
                return result;
            }
            String path = StringUtils.replace(storeLocalFilePath, "\\\\+", "\\");
            path = StringUtils.replace(path, "\\", "/");
            path = StringUtils.replace(path, Store_APK_Path, "");
            path = StringUtils.replace(path, "D:/upload", "");

            ds.setVersion(version.trim());
            ds.setEffectiveTime(DateFormatUtils.parse(effectiveTime, GlobalConstants.DATE_FORMAT_DPT));
            ds.setFtpPath(path);
            ds.setDownloadUrl(path);
            ds.setMd5Value(md5Value);
            deviceSystemService.insert(ds);
            result.put("msg", "添加成功!");
        } catch (Exception e) {
            LOGGER.error("insert DeviceSystem error", e);
            result.put("errorMsg", "上传文件出错，请重新上传或者联系管理员！");
            return result;
        }


        return result;
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject update(@RequestParam(value = "version") String version,
                             @RequestParam(value = "effectiveTime") String effectiveTime,
                             @RequestParam(value = "systemId") Long systemId,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             HttpServletRequest request) {
        String originFileName = file.getOriginalFilename();
        LOGGER.info("rev msg version={},effectiveTime={},originalFilename={},systemId={}", version, effectiveTime, originFileName, systemId);
        JSONObject result = new JSONObject();
        String md5Value = null;
        if (StringUtils.isBlank(version) || StringUtils.isBlank(effectiveTime) || systemId == null) {
            result.put("errorMsg", "表单参数填写有误,请重新填写并提交！");
            return result;
        }
        if (version.length() > 50) {
            result.put("errorMsg", "请正确输入版本号！");
            return result;
        }

        try {
            DeviceSystem deviceSystem = deviceSystemService.getById(systemId);
            if (deviceSystem == null) {
                result.put("errorMsg", "数据已被删除，请刷新!");
                return result;
            }
            //版本号唯一性校验
            DeviceSystem ds = new DeviceSystem();
            ds.setVersion(version.trim());
            Pagination page = new Pagination();
            page.setCurrentPage(1);
            page.setPageSize(2);
            List<DeviceSystem> list = deviceSystemService.queryByVo(page, ds);
            if (list != null && list.size() > 0) {
                for (DeviceSystem repeatVersionDs : list) {
                    if (!repeatVersionDs.getSystemId().equals(deviceSystem.getSystemId())) {
                        result.put("errorMsg", "版本号重复，请重新输入！");
                        return result;
                    }
                }
            }

            if (StringUtils.isNotBlank(originFileName)) {
                String storeLocalFilePath = localDirCacheService.storeApkFile(file.getInputStream(), originFileName);
                if (StringUtils.isBlank(storeLocalFilePath)) {
                    throw new Exception("上传文件保存出错，请重新操作");
                }
                md5Value = DesencryptUtils.md5File(new File(storeLocalFilePath));

                String path = StringUtils.replace(storeLocalFilePath, "\\\\+", "\\");
                path = StringUtils.replace(path, "\\", "/");
                path = StringUtils.replace(path, Store_APK_Path, "");
                path = StringUtils.replace(path, "D:/upload", "");

                if (!StringUtils.equalsIgnoreCase(md5Value, deviceSystem.getMd5Value())) {
                    deviceSystem.setMd5Value(md5Value);
                    deviceSystem.setFtpPath(path);
                    deviceSystem.setDownloadUrl(path);
                }
            }

            deviceSystem.setVersion(version.trim());
            deviceSystem.setEffectiveTime(DateFormatUtils.parse(effectiveTime, GlobalConstants.DATE_FORMAT_DPT));
            deviceSystem.setUpdateTime(new Date());

            deviceSystemService.update(deviceSystem);
            result.put("msg", "修改成功!");
        } catch (Exception e) {
            LOGGER.error("insert DeviceSystem error", e);
            result.put("errorMsg", "上传文件出错，请重新上传或者联系管理员！");
        }

        return result;
    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject delete(HttpServletRequest request) {
        String systemId = request.getParameter("systemId");
        String errorMsg = null;
        if (StringUtils.isEmpty(systemId)) {
            errorMsg = "系统错误，请联系管理员！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        DeviceSystem ds = deviceSystemService.getById(Long.parseLong(systemId));
        if (ds == null) {
            result.put("errorMsg", "数据已被其他人操作，请刷新!");
        } else {
            deviceSystemService.delete(ds);
            result.put("msg", "删除成功!");
        }
        return result;
    }
}