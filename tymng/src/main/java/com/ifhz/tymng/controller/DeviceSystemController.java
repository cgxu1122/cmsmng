package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.commons.util.FtpUtils;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.DeviceSystem;
import com.ifhz.core.service.device.DeviceSystemService;
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
import java.util.List;
import java.util.Map;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/deviceSystem")
public class DeviceSystemController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSystemController.class);
    @Autowired
    private DeviceSystemService deviceSystemService;

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
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/insert", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject insert(HttpServletRequest request) throws IOException {
        Map<String, FileItem> params = paserMultiData(request);
        String version = readInputStreamData(params.get("version").getInputStream());
        String effectiveTime = readInputStreamData(params.get("effectiveTime").getInputStream());
        FileItem file = params.get("file");
        String errorMsg = null;
        JSONObject result = new JSONObject();
        if (StringUtils.isEmpty(file.getName())) {
            errorMsg = "请上传设备升级文件！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        String fileName = FtpUtils.generateFileName(file.getName());
        try {
            FtpUtils.ftpUpload(file.getInputStream(),
                    GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_DEVICEDIR),
                    fileName
            );
        } catch (Exception e) {
            errorMsg = "上传文件出错，请重新上传或者联系管理员！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        if (StringUtils.isEmpty(version) || version.length() > 50) {
            errorMsg = "请正确输入版本号！";
        } else if (StringUtils.isEmpty(effectiveTime)) {
            errorMsg = "请选择生效时间！";
        }
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        //版本号唯一性校验
        DeviceSystem ds = new DeviceSystem();
        ds.setVersion(version.trim());
        List<DeviceSystem> list = deviceSystemService.queryByVo(null, ds);
        if (list != null && list.size() > 0) {
            errorMsg = "版本号重复，请重新输入！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        ds.setVersion(version.trim());
        ds.setEffectiveTime(DateFormatUtils.parse(effectiveTime, GlobalConstants.DATE_FORMAT_DPT));
        ds.setFtpPath(GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_DEVICEDIR) + fileName);
        deviceSystemService.insert(ds);
        result.put("msg", "添加成功!");
        return result;
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject update(HttpServletRequest request) throws IOException {
        Map<String, FileItem> params = paserMultiData(request);
        String systemId = readInputStreamData(params.get("systemId").getInputStream());
        String version = readInputStreamData(params.get("version").getInputStream());
        String effectiveTime = readInputStreamData(params.get("effectiveTime").getInputStream());
        String errorMsg = null;
        if (StringUtils.isEmpty(systemId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(version) || version.length() > 50) {
            errorMsg = "请正确输入版本号！";
        } else if (StringUtils.isEmpty(effectiveTime)) {
            errorMsg = "请选择生效时间！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        DeviceSystem deviceSystem = deviceSystemService.getById(Long.parseLong(systemId));
        if (deviceSystem == null) {
            result.put("errorMsg", "数据已被删除，请刷新!");
            return result;
        }
        //版本号唯一性校验
        DeviceSystem ds = new DeviceSystem();
        ds.setVersion(version.trim());
        List<DeviceSystem> list = deviceSystemService.queryByVo(null, ds);
        if (list != null && list.size() > 0) {
            for (DeviceSystem repeatVersionDs : list) {
                if (repeatVersionDs.getSystemId() != deviceSystem.getSystemId()) {
                    result.put("errorMsg", "版本号重复，请重新输入！");
                    return result;
                }
            }
        }
        FileItem file = params.get("file");
        if (StringUtils.isNotEmpty(file.getName())) {
            try {
                String fileName = FtpUtils.generateFileName(file.getName());
                FtpUtils.ftpUpload(file.getInputStream(),
                        GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_DEVICEDIR),
                        fileName
                );
                deviceSystem.setFtpPath(GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_DEVICEDIR) + fileName);
            } catch (Exception e) {
                errorMsg = "上传文件出错，请重新上传或者联系管理员！";
                result.put("errorMsg", errorMsg);
                return result;
            }
        }
        deviceSystem.setVersion(version.trim());
        deviceSystem.setEffectiveTime(DateFormatUtils.parse(effectiveTime, GlobalConstants.DATE_FORMAT_DPT));

        deviceSystemService.update(deviceSystem);
        result.put("msg", "修改成功!");
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