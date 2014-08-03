package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DeviceInfo;
import com.ifhz.core.service.device.DeviceInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/tymng/deviceInfo")
public class DeviceInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceInfoController.class);
    @Autowired
    private DeviceInfoService deviceInfoService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("deviceInfo/index");
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
        String deviceCodeCondition = request.getParameter("deviceCodeCondition");
        DeviceInfo di = new DeviceInfo();
        di.setActive(JcywConstants.ACTIVE_Y);
        di.setDeviceCodeCondition(deviceCodeCondition);
        List<DeviceInfo> list = deviceInfoService.queryByVo(page, di);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/insert", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject insert(HttpServletRequest request) {
        String groupId = request.getParameter("groupId");
        String channelId = request.getParameter("channelId");
        String deviceCode = request.getParameter("deviceCode");
        String errorMsg = null;

        if (StringUtils.isEmpty(deviceCode) || deviceCode.length() > 50) {
            errorMsg = "请正确输入设备编码！";
        } else if (StringUtils.isEmpty(groupId)) {
            errorMsg = "请选择渠道组织！";
        } else if (StringUtils.isEmpty(channelId)) {
            errorMsg = "请选择设备所属仓库/渠道！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        //设备编码唯一性校验
        DeviceInfo di = new DeviceInfo();
        di.setDeviceCode(deviceCode.trim());
        di.setActive(JcywConstants.ACTIVE_Y);
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(1);
        List<DeviceInfo> list = deviceInfoService.queryByVo(page, di);
        if (list != null && list.size() > 0) {
            errorMsg = "设备编码重复，请重新输入！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        di.setChannelId(Long.parseLong(channelId));
        di.setGroupId(Long.parseLong(groupId));
        deviceInfoService.insert(di);
        result.put("msg", "添加成功!");
        return result;
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject update(HttpServletRequest request) {
        String deviceId = request.getParameter("deviceId");
        String deviceCode = request.getParameter("deviceCode");
        String groupId = request.getParameter("groupId");
        String channelId = request.getParameter("channelId");
        String errorMsg = null;
        if (StringUtils.isEmpty(deviceId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(deviceCode) || deviceCode.length() > 50) {
            errorMsg = "请正确输入设备编码！";
        } else if (StringUtils.isEmpty(groupId)) {
            errorMsg = "请选择渠道组织！";
        } else if (StringUtils.isEmpty(channelId)) {
            errorMsg = "请选择设备所属仓库/渠道！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        DeviceInfo deviceInfo = deviceInfoService.getById(Long.parseLong(deviceId));
        if (deviceInfo == null) {
            result.put("errorMsg", "数据已被删除，请刷新!");
            return result;
        }
        //设备编码唯一性校验
        DeviceInfo di = new DeviceInfo();
        di.setDeviceCode(deviceCode.trim());
        di.setGroupId(deviceInfo.getGroupId());
        di.setActive(JcywConstants.ACTIVE_Y);
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(2);
        List<DeviceInfo> list = deviceInfoService.queryByVo(page, di);
        if (list != null && list.size() > 0) {
            for (DeviceInfo repeatCodeCi : list) {
                if (!repeatCodeCi.getDeviceId().equals(deviceInfo.getDeviceId())) {
                    result.put("errorMsg", "设备编码重复，请重新输入！");
                    return result;
                }
            }
        }
        deviceInfo.setDeviceCode(deviceCode.trim());
        deviceInfo.setGroupId(Long.parseLong(groupId));
        deviceInfo.setChannelId(Long.parseLong(channelId));
        deviceInfoService.update(deviceInfo);
        result.put("msg", "修改成功!");
        return result;
    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject delete(HttpServletRequest request) {
        String deviceId = request.getParameter("deviceId");
        String errorMsg = null;
        if (StringUtils.isEmpty(deviceId)) {
            errorMsg = "系统错误，请联系管理员！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        DeviceInfo di = deviceInfoService.getById(Long.parseLong(deviceId));
        if (di == null) {
            result.put("errorMsg", "数据已被其他人操作，请刷新!");
        } else {
            deviceInfoService.delete(di);
            result.put("msg", "删除成功!");
        }
        return result;
    }

}