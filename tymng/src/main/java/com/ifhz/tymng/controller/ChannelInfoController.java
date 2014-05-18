package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.service.channel.ChannelGroupService;
import com.ifhz.core.service.channel.ChannelInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/channelInfo")
public class ChannelInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelInfoController.class);
    @Autowired
    private ChannelGroupService channelGroupService;
    @Autowired
    private ChannelInfoService channelInfoService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        String groupId = request.getParameter("groupId");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("groupId", groupId);
        return new ModelAndView("channelInfo/index", result);
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
        String groupId = request.getParameter("groupId");
        String channelNameCondition = request.getParameter("channelNameCondition");
        ChannelInfo ci = new ChannelInfo();
        ci.setActive(JcywConstants.ACTIVE_Y);
        ci.setGroupId(Long.parseLong(groupId));
        ci.setChannelNameCondition(channelNameCondition);
        List<ChannelInfo> list = channelInfoService.queryByVo(page, ci);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/insert", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject insert(HttpServletRequest request) {
        String groupId = request.getParameter("groupId");
        String channelName = request.getParameter("channelName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String errorMsg = null;
        if (StringUtils.isEmpty(groupId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(channelName) || channelName.length() > 50) {
            errorMsg = "请正确输入仓库名称！";
        } else if (StringUtils.isEmpty(username) || username.length() > 50) {
            errorMsg = "请正确输入用户名！";
        } else if (StringUtils.isEmpty(password) || password.length() > 50) {
            errorMsg = "请正确输入用户密码！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        //仓库名称唯一性校验
        ChannelInfo ci = new ChannelInfo();
        ci.setChannelName(channelName);
        ci.setGroupId(Long.parseLong(groupId));
        ci.setActive(JcywConstants.ACTIVE_Y);
        ci.setParentId(JcywConstants.CHANNEL_ROOT_PARENT_ID);
        List<ChannelInfo> list = channelInfoService.queryByVo(null, ci);
        if (list != null && list.size() > 0) {
            errorMsg = "仓库名称重复，请重新输入！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        //TODO 添加用户账号密码
        channelInfoService.insert(ci);
        //TODO 更新用户id
        result.put("msg", "添加成功!");
        return result;
    }

}