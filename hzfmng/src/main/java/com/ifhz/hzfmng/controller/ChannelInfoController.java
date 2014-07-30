package com.ifhz.hzfmng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.anthrity.UserConstants;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.auth.SysUser;
import com.ifhz.core.service.auther.SysUserService;
import com.ifhz.core.service.channel.ChannelInfoService;
import com.ifhz.core.shiro.utils.CurrentUserUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/hzfmng/channelInfo")
public class ChannelInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelInfoController.class);
    @Autowired
    private ChannelInfoService channelInfoService;
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/listAll", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject listAll(HttpServletRequest request) {
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
        if (!StringUtils.isEmpty(channelNameCondition)) ci.setChannelNameCondition(channelNameCondition.trim());
        //如果是地包渠道的负责人登录，则进行数据过滤
        if (JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId) && CurrentUserUtil.isManager()) {
            ci.setMngId(CurrentUserUtil.getUserId());
        }
        List<ChannelInfo> list = channelInfoService.queryByVo(page, ci);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/listChannelByLW", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject listChannelByLW(HttpServletRequest request) {
        /**分页*/
        String pageNum = request.getParameter("page");
        String pageSize = request.getParameter("rows");
        Pagination page = new Pagination();
        if (!StringUtils.isEmpty(pageNum)) page.setCurrentPage(Integer.valueOf(pageNum));
        if (!StringUtils.isEmpty(pageSize)) page.setPageSize(Integer.valueOf(pageSize));
        //查询条件
        String channelNameCondition = request.getParameter("channelNameCondition");
        ChannelInfo ci = new ChannelInfo();
        ci.setActive(JcywConstants.ACTIVE_Y);
        ci.setGroupId(JcywConstants.CHANNEL_GROUP_DB_ID_2);
        if (!StringUtils.isEmpty(channelNameCondition)) ci.setChannelNameCondition(channelNameCondition.trim());

        ChannelInfo channelInfo = channelInfoService.getChannelInfoByUserId(CurrentUserUtil.getUserId());
        if (channelInfo != null) {
            ci.setLaowuId(channelInfo.getChannelId());
        }
        List<ChannelInfo> list = channelInfoService.queryByVo(page, ci);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
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
        String parentIdCondition = request.getParameter("parentIdCondition");
        String channelNameCondition = request.getParameter("channelNameCondition");
        ChannelInfo ci = new ChannelInfo();
        ci.setActive(JcywConstants.ACTIVE_Y);
        ci.setGroupId(Long.parseLong(groupId));
        if (StringUtils.isEmpty(parentIdCondition)) {
            ci.setParentId(JcywConstants.CHANNEL_ROOT_PARENT_ID);
        } else {
            ci.setParentId(Long.parseLong(parentIdCondition));
        }
        if (!StringUtils.isEmpty(channelNameCondition)) ci.setChannelNameCondition(channelNameCondition.trim());
        //如果是地包渠道的负责人登录，则进行数据过滤
        if (JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId) && CurrentUserUtil.isManager()) {
            ci.setMngId(CurrentUserUtil.getUserId());
        }
        List<ChannelInfo> list = channelInfoService.queryByVo(page, ci);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/listMng", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject listMng(HttpServletRequest request) {
        /**分页*/
        String pageNum = request.getParameter("page");
        String pageSize = request.getParameter("rows");
        Pagination page = new Pagination();
        if (!StringUtils.isEmpty(pageNum)) page.setCurrentPage(Integer.valueOf(pageNum));
        if (!StringUtils.isEmpty(pageSize)) page.setPageSize(Integer.valueOf(pageSize));
        //查询条件
        String searchValue = request.getParameter("searchValue");
        SysUser user = new SysUser();
        user.setRoleId(UserConstants.USER_TYPE_MANAGER);
        user.setSearchValue(searchValue);
        List<SysUser> list = sysUserService.queryByVo(page, user);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }
}