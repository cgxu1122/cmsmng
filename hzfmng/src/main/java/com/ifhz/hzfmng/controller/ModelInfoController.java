package com.ifhz.hzfmng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.service.model.ModelInfoService;
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
@RequestMapping("/hzfmng/modelInfo")
public class ModelInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelInfoController.class);
    @Autowired
    private ModelInfoService modelInfoService;

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
        String modelNameCondition = request.getParameter("modelNameCondition");
        ModelInfo mi = new ModelInfo();
        mi.setActive(JcywConstants.ACTIVE_Y);
        if (StringUtils.isNotEmpty(groupId)) {
            mi.setGroupId(Long.parseLong(groupId));
        }
        mi.setModelNameCondition(modelNameCondition);
        List<ModelInfo> list = modelInfoService.queryByVo(page, mi);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

}