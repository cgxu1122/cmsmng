package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.auth.SysUser;
import com.ifhz.core.service.auther.SysUserService;
import com.ifhz.core.service.channel.ChannelGroupService;
import com.ifhz.core.service.channel.ChannelInfoService;
import com.ifhz.core.shiro.utils.CurrentUserUtil;
import com.ifhz.core.utils.PatternUtils;
import org.apache.commons.collections.CollectionUtils;
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
@RequestMapping("/tymng/channelInfo")
public class ChannelInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelInfoController.class);
    @Autowired
    private ChannelGroupService channelGroupService;
    @Autowired
    private ChannelInfoService channelInfoService;
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        String groupId = request.getParameter("groupId");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("groupId", groupId);
        if (JcywConstants.CHANNEL_GROUP_TY_ID_1.toString().equals(groupId)) {
            return new ModelAndView("channelInfo/indexTY", result);
        } else if (JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId)) {
            result.put("userType", "admin");
            //如果是地包渠道的负责人登录，则进行数据过滤
            if (CurrentUserUtil.isManager()) {
                result.put("userType", "manager");
            }
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
        Long parentId = null;
        JSONArray result = new JSONArray();
        if (!StringUtils.isEmpty(parentIdStr)) {
            parentId = Long.parseLong(parentIdStr);
        } else {//返回根节点
            JSONObject jo = new JSONObject();
            jo.put("id", JcywConstants.CHANNEL_ROOT_PARENT_ID);
            if (JcywConstants.CHANNEL_GROUP_TY_ID_1.toString().equals(groupId)) {
                jo.put("text", "天音渠道");
            } else if (JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId)) {
                jo.put("text", "地包渠道");
            } else if (JcywConstants.CHANNEL_GROUP_QT_ID_3.toString().equals(groupId)) {
                jo.put("text", "其他渠道");
            }
            jo.put("state", "closed");
            result.add(jo);
            return result;
        }
        ChannelInfo ci = new ChannelInfo();
        ci.setActive(JcywConstants.ACTIVE_Y);
        ci.setGroupId(Long.parseLong(groupId));
        ci.setParentId(parentId);
        //如果是地包渠道的负责人登录，则进行数据过滤
        if (JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId) && CurrentUserUtil.isManager()) {
            ci.setMngId(CurrentUserUtil.getUserId());
        }
        List<ChannelInfo> list = channelInfoService.queryByVo(null, ci);
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

    @RequestMapping(value = "/listTreeAll", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONArray listTreeAll(HttpServletRequest request) {
        //查询条件
        String groupId = request.getParameter("groupId");
        ChannelInfo ci = new ChannelInfo();
        ci.setActive(JcywConstants.ACTIVE_Y);
        ci.setGroupId(Long.parseLong(groupId));
        ci.setParentId(JcywConstants.CHANNEL_ROOT_PARENT_ID);
        //如果是地包渠道的负责人登录，则进行数据过滤
        if (JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId) && CurrentUserUtil.isManager()) {
            ci.setMngId(CurrentUserUtil.getUserId());
        }
        JSONObject jo = new JSONObject();
        jo.put("id", JcywConstants.CHANNEL_ROOT_PARENT_ID);
        if (JcywConstants.CHANNEL_GROUP_TY_ID_1.toString().equals(groupId)) {
            jo.put("text", "天音渠道");
        } else if (JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId)) {
            jo.put("text", "地包渠道");
        } else if (JcywConstants.CHANNEL_GROUP_QT_ID_3.toString().equals(groupId)) {
            jo.put("text", "其他渠道");
        }
        JSONArray subJa = new JSONArray();
        loadAllTreeChannelInfo(subJa, ci);
        if (CollectionUtils.isNotEmpty(subJa)) {
            jo.put("children", subJa);
        }
        jo.put("state", "open");
        JSONArray result = new JSONArray();
        result.add(jo);
        return result;
    }

    private void loadAllTreeChannelInfo(JSONArray ja, ChannelInfo channelInfoCondition) {
        List<ChannelInfo> list = channelInfoService.queryByVo(null, channelInfoCondition);
        if (list != null && list.size() > 0) {
            for (ChannelInfo channelInfo : list) {
                JSONObject jo = new JSONObject();
                jo.put("id", channelInfo.getChannelId());
                jo.put("text", channelInfo.getChannelName());
                if (JcywConstants.BASE_CONSTANT_N.equals(channelInfo.getLeaf())) {
                    jo.put("state", "open");
                    JSONArray subJa = new JSONArray();
                    jo.put("children", subJa);
                    channelInfoCondition.setParentId(channelInfo.getChannelId());
                    loadAllTreeChannelInfo(subJa, channelInfoCondition);
                } else {
                    jo.put("state", "open");
                }
                ja.add(jo);
            }
        }
    }

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
        }
        if (!JcywConstants.CHANNEL_GROUP_QT_ID_3.toString().equals(groupId)) {//其他渠道不需要验证用户名和密码
            if (StringUtils.isEmpty(username) || username.length() > 50) {
                errorMsg = "请正确输入用户名！";
            } else if (StringUtils.isEmpty(password) || password.length() > 50) {
                errorMsg = "请正确输入用户密码！";
            }
            if (!PatternUtils.verifyLoginName(username)) {
                errorMsg = "用户名包含非法字符，请正确输入用户名";
            }
            SysUser user = sysUserService.getByLoginName(username);
            if (user != null) {
                errorMsg = "用户名重复，请重新输入！";
            }
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
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(1);
        List<ChannelInfo> list = channelInfoService.queryByVo(page, ci);
        if (list != null && list.size() > 0) {
            errorMsg = "名称重复，请重新输入！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        String mngId = request.getParameter("mngId");
        //如果是系统管理员添加地包一级渠道，则mngId为必选项
        if (JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId) && !CurrentUserUtil.isManager() && StringUtils.isNotEmpty(parentId) && JcywConstants.CHANNEL_ROOT_PARENT_ID == Long.parseLong(parentId)) {
            if (StringUtils.isEmpty(mngId)) {
                errorMsg = "请选择负责人！";
                result.put("errorMsg", errorMsg);
                return result;
            }
        }
        if (!StringUtils.isEmpty(mngId) && StringUtils.isNumeric(mngId)) {
            ci.setMngId(Long.parseLong(mngId));
        }
        //如果是负责人添加地包一级渠道，则mngId为当前登录人
        if (JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId) && CurrentUserUtil.isManager() && StringUtils.isNotEmpty(parentId) && JcywConstants.CHANNEL_ROOT_PARENT_ID == Long.parseLong(parentId)) {
            ci.setMngId(CurrentUserUtil.getUserId());
        }
        if (!StringUtils.isEmpty(parentId)) {//如果有父级数据则添加上parentId，并且更改父级机构叶子节点属性
            ci.setParentId(Long.parseLong(parentId));
            ChannelInfo parentChannelInfo = channelInfoService.getById(Long.parseLong(parentId));
            //将新添加的渠道负责人设置为和父渠道同一负责人
            if (parentChannelInfo != null) {
                ci.setMngId(parentChannelInfo.getMngId());
            }
            if (parentChannelInfo != null && JcywConstants.BASE_CONSTANT_Y.equals(parentChannelInfo.getLeaf())) {
                parentChannelInfo.setLeaf(JcywConstants.BASE_CONSTANT_N);
                channelInfoService.update(parentChannelInfo);
            }
        }
        String laowuId = request.getParameter("laowuId");
        if (!StringUtils.isEmpty(laowuId) && StringUtils.isNumeric(laowuId)) {
            ci.setLaowuId(Long.parseLong(laowuId));
        }
        String queryImeiSource = request.getParameter("queryImeiSource");
        if (!StringUtils.isEmpty(queryImeiSource)) {
            ci.setQueryImeiSource(queryImeiSource);
        }
        if (JcywConstants.CHANNEL_GROUP_LW_ID_4.equals(groupId)) {
            ci.setType(JcywConstants.CHANNEL_TYPE_L);
        } else if (JcywConstants.CHANNEL_GROUP_DB_ID_2.equals(groupId)) {
            if (JcywConstants.CHANNEL_ROOT_PARENT_ID == ci.getParentId()) {
                ci.setType(JcywConstants.CHANNEL_TYPE_M);//主渠道
            } else {
                ci.setType(JcywConstants.CHANNEL_TYPE_C);//主渠道
            }
        } else {
            ci.setType(JcywConstants.CHANNEL_TYPE_O);
        }
        ci.setUsername(username);
        ci.setPassword(password);
        String address = request.getParameter("address");
        ci.setAddress(address);
        String contact = request.getParameter("contact");
        ci.setContact(contact);
        String phone = request.getParameter("phone");
        ci.setPhone(phone);
        ci.setLeaf(JcywConstants.BASE_CONSTANT_Y);
        channelInfoService.insert(ci);
        result.put("msg", "添加成功!");
        return result;
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject update(HttpServletRequest request) {
        String channelId = request.getParameter("channelId");
        String channelName = request.getParameter("channelName");
        String errorMsg = null;
        if (StringUtils.isEmpty(channelId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(channelName) || channelName.length() > 50) {
            errorMsg = "请正确输入渠道名称！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        ChannelInfo ci = channelInfoService.getById(Long.parseLong(channelId));
        if (ci == null) {
            result.put("errorMsg", "数据已被删除，请刷新!");
            return result;
        }
        //仓库名称唯一性校验
        ChannelInfo ciCondition = new ChannelInfo();
        ciCondition.setGroupId(ci.getGroupId());
        ciCondition.setChannelName(channelName.trim());
        ciCondition.setActive(JcywConstants.ACTIVE_Y);
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(2);
        List<ChannelInfo> list = channelInfoService.queryByVo(page, ciCondition);
        if (list != null && list.size() > 0) {
            for (ChannelInfo repeatNameCi : list) {
                if (!repeatNameCi.getChannelId().equals(ci.getChannelId())) {
                    result.put("errorMsg", "仓库名称重复，请重新输入！");
                    return result;
                }
            }
        }
        ci.setChannelName(channelName.trim());
        String queryImeiSource = request.getParameter("queryImeiSource");
        if (!StringUtils.isEmpty(queryImeiSource)) {
            ci.setQueryImeiSource(queryImeiSource);
        }
        String laowuId = request.getParameter("laowuId");
        if (!StringUtils.isEmpty(laowuId) && StringUtils.isNumeric(laowuId)) {
            ci.setLaowuId(Long.parseLong(laowuId));
        }
        String mngId = request.getParameter("mngId");
        if (!StringUtils.isEmpty(mngId) && StringUtils.isNumeric(mngId)) {
            ci.setMngId(Long.parseLong(mngId));
        }
        String userId = request.getParameter("userId");
        if (!StringUtils.isEmpty(userId) && StringUtils.isNumeric(userId)) {
            ci.setUserId(Long.parseLong(userId));
        }
        String password = request.getParameter("password");
        ci.setPassword(password);
        String address = request.getParameter("address");
        ci.setAddress(address);
        String contact = request.getParameter("contact");
        ci.setContact(contact);
        String phone = request.getParameter("phone");
        ci.setPhone(phone);
        channelInfoService.update(ci);
        result.put("msg", "修改成功!");
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
            //查看是否需要更新父节点leaf状态
            Long parentId = ci.getParentId();
            if (JcywConstants.CHANNEL_ROOT_PARENT_ID != parentId) {
                ChannelInfo ciCondition = new ChannelInfo();
                ciCondition.setParentId(parentId);
                ciCondition.setActive(JcywConstants.ACTIVE_Y);
                Pagination page = new Pagination();
                page.setCurrentPage(1);
                page.setPageSize(1);
                List<ChannelInfo> list = channelInfoService.queryByVo(page, ciCondition);
                if (list != null || list.size() <= 0) {
                    ChannelInfo parentCi = channelInfoService.getById(parentId);
                    parentCi.setLeaf(JcywConstants.BASE_CONSTANT_Y);
                    channelInfoService.update(parentCi);
                }
            }
            channelInfoService.delete(ci);
            result.put("msg", "删除成功!");
        }
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
        List<SysUser> list = sysUserService.queryMngListByVo(page, searchValue);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }
}