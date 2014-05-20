package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONArray;
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
        if (JcywConstants.CHANNEL_GROUP_TY_ID_1.toString().equals(groupId)) {
            return new ModelAndView("channelInfo/indexTY", result);
        } else if (JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId)) {
            return new ModelAndView("channelInfo/indexDB", result);
        } else if (JcywConstants.CHANNEL_GROUP_QT_ID_3.toString().equals(groupId)) {
            return new ModelAndView("channelInfo/indexQT", result);
        } else if (JcywConstants.CHANNEL_GROUP_LW_ID_4.toString().equals(groupId)) {
            return new ModelAndView("channelInfo/indexLW", result);
        }
        return new ModelAndView("channelInfo/indexTY", result);
    }

    @RequestMapping(value = "/listTree", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONArray listTree(HttpServletRequest request) {
        //查询条件
        String groupId = request.getParameter("groupId");
        String parentIdStr = request.getParameter("parentIdCondition");
        Long parentId = JcywConstants.CHANNEL_ROOT_PARENT_ID;
        if (!StringUtils.isEmpty(parentIdStr)) {
            parentId = Long.parseLong(parentIdStr);
        }
        ChannelInfo ci = new ChannelInfo();
        ci.setActive(JcywConstants.ACTIVE_Y);
        ci.setGroupId(Long.parseLong(groupId));
        ci.setParentId(parentId);
        List<ChannelInfo> list = channelInfoService.queryByVo(null, ci);
        JSONArray result = new JSONArray();
        if (list != null && list.size() > 0) {
            for (ChannelInfo channelInfo : list) {
                JSONObject jo = new JSONObject();
                jo.put("id", channelInfo.getChannelId());
                jo.put("text", channelInfo.getChannelName());
                if (JcywConstants.BASE_CONSTANT_N.equals(channelInfo.getLeaf())) {
                    jo.put("state", "closed");
                } else {
                    jo.put("state", "open");
                }
                result.add(jo);
            }
        }
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
        String parentId = request.getParameter("parentId");
        String channelName = request.getParameter("channelName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String errorMsg = null;
        if (StringUtils.isEmpty(groupId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(channelName) || channelName.length() > 50) {
            errorMsg = "请正确输入名称！";
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
        ci.setChannelName(channelName.trim());
        ci.setGroupId(Long.parseLong(groupId));
        ci.setActive(JcywConstants.ACTIVE_Y);
        ci.setParentId(JcywConstants.CHANNEL_ROOT_PARENT_ID);
        List<ChannelInfo> list = channelInfoService.queryByVo(null, ci);
        if (list != null && list.size() > 0) {
            errorMsg = "名称重复，请重新输入！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        if (!StringUtils.isEmpty(parentId)) {//如果有父级数据则添加上parentId，并且更改父级机构叶子节点属性
            ci.setParentId(Long.parseLong(parentId));
            ChannelInfo parentChannelInfo = channelInfoService.getById(Long.parseLong(parentId));
            if (parentChannelInfo != null && JcywConstants.BASE_CONSTANT_Y.equals(parentChannelInfo.getLeaf())) {
                parentChannelInfo.setLeaf(JcywConstants.BASE_CONSTANT_N);
                channelInfoService.update(parentChannelInfo);
            }
        }
        String queryImeiSource = request.getParameter("queryImeiSource");
        if (!StringUtils.isEmpty(queryImeiSource)) {
            ci.setQueryImeiSource(queryImeiSource);
        }
        //TODO 添加用户账号密码
        if (JcywConstants.CHANNEL_GROUP_LW_ID_4.equals(groupId)) {
            ci.setType(JcywConstants.CHANNEL_TYPE_L);
        } else {
            ci.setType(JcywConstants.CHANNEL_TYPE_O);
        }
        channelInfoService.insert(ci);
        //TODO 更新用户id
        result.put("msg", "添加成功!");
        return result;
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject update(HttpServletRequest request) {
        String channelId = request.getParameter("channelId");
        String channelName = request.getParameter("channelName");
        String password = request.getParameter("password");
        String errorMsg = null;
        if (StringUtils.isEmpty(channelId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(channelName) || channelName.length() > 50) {
            errorMsg = "请正确输入仓库名称！";
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
        ci.setChannelName(channelName.trim());
        ci.setActive(JcywConstants.ACTIVE_Y);
        List<ChannelInfo> list = channelInfoService.queryByVo(null, ci);
        ci = channelInfoService.getById(Long.parseLong(channelId));
        if (list != null && list.size() > 0) {
            for (ChannelInfo repeatNameCi : list) {
                if (repeatNameCi.getChannelId() != ci.getChannelId()) {
                    result.put("errorMsg", "仓库名称重复，请重新输入！");
                    return result;
                }
            }
        }
        ci = channelInfoService.getById(Long.parseLong(channelId));
        if (ci == null) {
            result.put("errorMsg", "数据已被删除，请刷新!");
        } else {
            ci.setChannelName(channelName.trim());
            String queryImeiSource = request.getParameter("queryImeiSource");
            if (!StringUtils.isEmpty(queryImeiSource)) {
                ci.setQueryImeiSource(queryImeiSource);
            }
            channelInfoService.update(ci);
            //TODO 修改用户账号密码
            result.put("msg", "修改成功!");
        }
        return result;
    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject delete(HttpServletRequest request) {
        String channelId = request.getParameter("channelId");
        String errorMsg = null;
        if (StringUtils.isEmpty(channelId)) {
            errorMsg = "系统错误，请联系管理员！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        ChannelInfo ci = channelInfoService.getById(Long.parseLong(channelId));
        if (ci == null) {
            result.put("errorMsg", "数据已被其他人操作，请刷新!");
        } else {
            channelInfoService.delete(ci);
            result.put("msg", "删除成功!");
        }
        return result;
    }

}