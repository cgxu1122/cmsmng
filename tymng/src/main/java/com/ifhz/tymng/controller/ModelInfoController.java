package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.commons.util.JcywUtils;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/tymng/modelInfo")
public class ModelInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelInfoController.class);
    @Autowired
    private ModelInfoService modelInfoService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        String groupId = request.getParameter("groupId");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("groupId", groupId);
        if (JcywConstants.CHANNEL_GROUP_TY_ID_1.toString().equals(groupId)) {
            return new ModelAndView("modelInfo/indexTY", result);
        } else if (JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId)) {
            return new ModelAndView("modelInfo/indexDB", result);
        } else if (JcywConstants.CHANNEL_GROUP_QT_ID_3.toString().equals(groupId)) {
            return new ModelAndView("modelInfo/indexQT", result);
        }
        return null;
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

   /* @RequestMapping(value = "/export", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response) {
        String pageNum = "1";
        String pageSize = GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.EXPORT_NUM_MAX);
        Pagination page = new Pagination();
        if (!StringUtils.isEmpty(pageNum)) page.setCurrentPage(Integer.valueOf(pageNum));
        if (!StringUtils.isEmpty(pageSize)) page.setPageSize(Integer.valueOf(pageSize));
        //查询条件
        String groupId = request.getParameter("groupId");
        String modelNameCondition = request.getParameter("modelNameCondition");
        ModelInfo mi = new ModelInfo();
        mi.setActive(JcywConstants.ACTIVE_Y);
        mi.setGroupId(Long.parseLong(groupId));
        mi.setModelNameCondition(modelNameCondition);
        List<ModelInfo> list = modelInfoService.queryByVo(page, mi);
        BaseExportModel exportModel = new BaseExportModel();
        Map<String, String> titleMap = new HashMap<String, String>();
        titleMap.put("modelId", "modelId");
        titleMap.put("ua", "ua");
        titleMap.put("modelName", "机型名称");
        titleMap.put("tagNum", "标签数量");
        titleMap.put("tagPrice", "标签单价");
        titleMap.put("groupName", "渠道组名称");
        exportModel.setTitleMap(titleMap);
        exportModel.setDataList(list);
        ExportDataUtil.writeData(exportModel, new File("D:\\\\model.csv"));

        // 下载本地文件
        String fileName = "model.csv".toString(); // 文件的默认保存名
        // 读到流中
        InputStream inStream = null;// 文件的存放路径
        try {
            inStream = new FileInputStream("D:\\\\model.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @RequestMapping(value = "/insert", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject insert(HttpServletRequest request) {
        String groupId = request.getParameter("groupId");
        String modelName = request.getParameter("modelName");
        String ua = request.getParameter("ua");
        String tagNum = request.getParameter("tagNum");
        String tagPrice = request.getParameter("tagPrice");
        String errorMsg = null;
        if (StringUtils.isEmpty(groupId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(modelName) || modelName.length() > 50) {
            errorMsg = "请正确输入名称！";
        } else if (StringUtils.isEmpty(ua) || ua.length() > 50) {
            errorMsg = "请正确输入UA！";
        } else if (StringUtils.isEmpty(tagNum) || !StringUtils.isNumeric(tagNum)) {
            errorMsg = "请正确输入标签数量！";
        } else if (StringUtils.isEmpty(tagPrice) || !JcywUtils.isDouble(tagPrice)) {
            errorMsg = "请正确输入标签单价！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        //机型名称唯一性校验
        modelName = modelName.trim().replaceAll("\\s+", "_");
        ModelInfo mi = new ModelInfo();
        mi.setModelName(modelName);
        mi.setGroupId(Long.parseLong(groupId));
        mi.setActive(JcywConstants.ACTIVE_Y);
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(1);
        List<ModelInfo> list = modelInfoService.queryByVo(page, mi);
        if (list != null && list.size() > 0) {
            errorMsg = "机型全名重复，请重新输入！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        //机型UA唯一性校验
        ua = ua.trim().replaceAll("\\s+", "_");
        mi = new ModelInfo();
        mi.setUa(ua);
        mi.setGroupId(Long.parseLong(groupId));
        mi.setActive(JcywConstants.ACTIVE_Y);
        list = modelInfoService.queryByVo(page, mi);
        if (list != null && list.size() > 0) {
            errorMsg = "UA重复，请重新输入！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        mi.setModelName(modelName);
        mi.setTagNum(Integer.parseInt(tagNum));
        mi.setTagPrice(Double.parseDouble(tagPrice));
        modelInfoService.insert(mi);
        result.put("msg", "添加成功!");
        return result;
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject update(HttpServletRequest request) {
        String modelId = request.getParameter("modelId");
        String modelName = request.getParameter("modelName");
        String ua = request.getParameter("ua");
        String tagNum = request.getParameter("tagNum");
        String tagPrice = request.getParameter("tagPrice");
        String errorMsg = null;
        if (StringUtils.isEmpty(modelId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(modelName) || modelName.length() > 50) {
            errorMsg = "请正确输入名称！";
        } else if (StringUtils.isEmpty(ua) || ua.length() > 50) {
            errorMsg = "请正确输入UA！";
        } else if (StringUtils.isEmpty(tagNum) || !StringUtils.isNumeric(tagNum)) {
            errorMsg = "请正确输入标签数量！";
        } else if (StringUtils.isEmpty(tagPrice) || !JcywUtils.isDouble(tagPrice)) {
            errorMsg = "请正确输入标签单价！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        ModelInfo modelInfo = modelInfoService.getById(Long.parseLong(modelId));
        if (modelInfo == null) {
            result.put("errorMsg", "数据已被删除，请刷新!");
            return result;
        }
        //机型名称唯一性校验
        modelName = modelName.trim().replaceAll("\\s+", "_");
        ModelInfo mi = new ModelInfo();
        mi.setModelName(modelName);
        mi.setGroupId(modelInfo.getGroupId());
        mi.setActive(JcywConstants.ACTIVE_Y);
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(1);
        List<ModelInfo> list = modelInfoService.queryByVo(page, mi);
        if (list != null && list.size() > 0) {
            for (ModelInfo repeatNameCi : list) {
                if (!repeatNameCi.getModelId().equals(modelInfo.getModelId())) {
                    result.put("errorMsg", "机型全名重复，请重新输入！");
                    return result;
                }
            }
        }
        //机型UA唯一性校验
        ua = ua.trim().replaceAll("\\s+", "_");
        mi = new ModelInfo();
        mi.setUa(ua);
        mi.setGroupId(modelInfo.getGroupId());
        mi.setActive(JcywConstants.ACTIVE_Y);
        list = modelInfoService.queryByVo(page, mi);
        if (list != null && list.size() > 0) {
            for (ModelInfo repeatUaCi : list) {
                if (!repeatUaCi.getModelId().equals(modelInfo.getModelId())) {
                    errorMsg = "UA重复，请重新输入！";
                    result.put("errorMsg", errorMsg);
                    return result;
                }
            }
        }
        modelInfo.setModelName(modelName.trim());
        modelInfo.setUa(ua);
        modelInfo.setTagPrice(Double.parseDouble(tagPrice));
        modelInfo.setTagNum(Integer.parseInt(tagNum));
        modelInfoService.update(modelInfo);
        result.put("msg", "修改成功!");
        return result;
    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject delete(HttpServletRequest request) {
        String modelId = request.getParameter("modelId");
        String errorMsg = null;
        if (StringUtils.isEmpty(modelId)) {
            errorMsg = "系统错误，请联系管理员！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        ModelInfo mi = modelInfoService.getById(Long.parseLong(modelId));
        if (mi == null) {
            result.put("errorMsg", "数据已被其他人操作，请刷新!");
        } else {
            modelInfoService.delete(mi);
            result.put("msg", "删除成功!");
        }
        return result;
    }

}