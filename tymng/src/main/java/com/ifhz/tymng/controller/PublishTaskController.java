package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.PubChlModRef;
import com.ifhz.core.po.PublishTask;
import com.ifhz.core.service.pkgmng.PublishTaskService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/publishTask")
public class PublishTaskController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublishTaskController.class);
    @Autowired
    private PublishTaskService publishTaskService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("publishTask/index");
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
        String packageNameCondition = request.getParameter("packageNameCondition");
        PublishTask pt = new PublishTask();
        pt.setActive(JcywConstants.ACTIVE_Y);
        pt.setPackageNameCondition(packageNameCondition);
        List<PublishTask> list = publishTaskService.queryByVo(page, pt);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/insert", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject insert(HttpServletRequest request) {
        String packageId = request.getParameter("packageId");
        String packageName = request.getParameter("packageName");
        String pkgType = request.getParameter("pkgType");
        String effectTime = request.getParameter("effectTime");
        String groupId = request.getParameter("groupId");
        String[] channelIds = request.getParameterValues("channelId");
        String[] modelIds = request.getParameterValues("modelId");
        String errorMsg = null;
        if (StringUtils.isEmpty(packageId)) {
            errorMsg = "请选择安装包！";
        } else if (StringUtils.isEmpty(effectTime)) {
            errorMsg = "请选择生效时间！";
        } else if (StringUtils.isEmpty(groupId) && !StringUtils.isNumeric(groupId)) {
            errorMsg = "请选择渠道组！";
        } else if (modelIds == null || modelIds.length <= 0) {
            errorMsg = "请选择机型！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        PublishTask pt = new PublishTask();
        pt.setActive(JcywConstants.ACTIVE_Y);
        pt.setPackageId(Long.parseLong(packageId));
        pt.setPackageName(packageName);
        pt.setPkgType(pkgType);
        pt.setEffectTime(DateFormatUtils.parse(effectTime, GlobalConstants.DATE_FORMAT_DPT));
        List<PubChlModRef> pubChlList = new ArrayList<PubChlModRef>();
        if (channelIds != null && channelIds.length > 0) {
            for (int i = 0; i < channelIds.length; i++) {
                if (StringUtils.isNotEmpty(channelIds[i]) && StringUtils.isNumeric(channelIds[i])) {
                    PubChlModRef pubChl = new PubChlModRef();
                    pubChl.setGroupId(Long.parseLong(groupId));
                    pubChl.setChannelId(Long.parseLong(channelIds[i]));
                    pubChlList.add(pubChl);
                }
            }
        } else {
            PubChlModRef pubChl = new PubChlModRef();
            pubChl.setGroupId(Long.parseLong(groupId));
            pubChlList.add(pubChl);
        }
        pt.setPubChlList(pubChlList);
        List<PubChlModRef> pubModList = new ArrayList<PubChlModRef>();
        for (int i = 0; i < modelIds.length; i++) {
            if (StringUtils.isNotEmpty(modelIds[i]) && StringUtils.isNumeric(modelIds[i])) {
                PubChlModRef pubMod = new PubChlModRef();
                pubMod.setModelId(Long.parseLong(modelIds[i]));
                pubModList.add(pubMod);
            }
        }
        pt.setPubModList(pubModList);
        publishTaskService.insert(pt);
        result.put("msg", "添加成功!");
        return result;
    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject delete(HttpServletRequest request) {
        String publishId = request.getParameter("publishId");
        String errorMsg = null;
        if (StringUtils.isEmpty(publishId)) {
            errorMsg = "系统错误，请联系管理员！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        PublishTask pt = publishTaskService.getById(Long.parseLong(publishId));
        if (pt == null) {
            result.put("errorMsg", "数据已被其他人操作，请刷新!");
        } else {
            publishTaskService.delete(pt);
            result.put("msg", "删除成功!");
        }
        return result;
    }

}