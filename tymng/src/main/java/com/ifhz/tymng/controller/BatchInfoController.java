package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.BatchInfo;
import com.ifhz.core.po.ProductInfo;
import com.ifhz.core.service.batch.BatchInfoService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/tymng/batchInfo")
public class BatchInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchInfoController.class);
    @Autowired
    private BatchInfoService batchInfoService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        String groupId = request.getParameter("groupId");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("groupId", groupId);
        if (JcywConstants.CHANNEL_GROUP_TY_ID_1.toString().equals(groupId)) {
            return new ModelAndView("batchInfo/indexTY", result);
        } else if (JcywConstants.CHANNEL_GROUP_DB_ID_2.toString().equals(groupId)) {
            return new ModelAndView("batchInfo/indexDB", result);
        } else if (JcywConstants.CHANNEL_GROUP_QT_ID_3.toString().equals(groupId)) {
            return new ModelAndView("batchInfo/indexQT", result);
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
        String batchCodeCondition = request.getParameter("batchCodeCondition");
        String batchProductName = request.getParameter("batchProductName");
        String groupId = request.getParameter("groupId");
        BatchInfo bi = new BatchInfo();
        bi.setActive(JcywConstants.ACTIVE_Y);
        bi.setGroupId(Long.parseLong(groupId));
        bi.setBatchCodeCondition(batchCodeCondition);
        bi.setBatchProductName(batchProductName);
        List<BatchInfo> list = batchInfoService.queryByVo(page, bi);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/insert", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject insert(HttpServletRequest request) {
        String groupId = request.getParameter("groupId");
        String batchCode = request.getParameter("batchCode");
        String startTime = request.getParameter("startTime");
        String[] productIdList = request.getParameterValues("productId");
        String[] productNameList = request.getParameterValues("productName");
        String errorMsg = null;
        if (StringUtils.isEmpty(groupId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(batchCode)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(startTime)) {
            errorMsg = "请选择开始时间！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        BatchInfo bi = new BatchInfo();
        bi.setActive(JcywConstants.ACTIVE_Y);
        bi.setGroupId(Long.parseLong(groupId));
        bi.setBatchCode(batchCode);
        bi.setStartTime(DateFormatUtils.parse(startTime, GlobalConstants.DATE_FORMAT_DPT));
        if (productIdList != null && productIdList.length > 0) {
            List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
            for (int i = 0; i < productIdList.length; i++) {
                if (StringUtils.isNotEmpty(productIdList[i]) && StringUtils.isNumeric(productIdList[i])) {
                    ProductInfo pi = new ProductInfo();
                    pi.setProductId(Long.parseLong(productIdList[i]));
                    pi.setProductName(productNameList[i]);
                    productInfoList.add(pi);
                }
            }
            bi.setProductInfoList(productInfoList);
        }
        batchInfoService.insert(bi);
        result.put("msg", "添加成功!");
        return result;
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject update(HttpServletRequest request) {
        String batchId = request.getParameter("batchId");
        String startTime = request.getParameter("startTime");
        String[] productIdList = request.getParameterValues("productId");
        String[] productNameList = request.getParameterValues("productName");
        String errorMsg = null;
        if (StringUtils.isEmpty(batchId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(startTime)) {
            errorMsg = "请选择开始时间！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        BatchInfo batchInfo = batchInfoService.getById(Long.parseLong(batchId));
        if (batchInfo == null) {
            result.put("errorMsg", "数据已被删除，请刷新!");
            return result;
        }
        batchInfo.setStartTime(DateFormatUtils.parse(startTime, GlobalConstants.DATE_FORMAT_DPT));
        if (productIdList != null && productIdList.length > 0) {
            List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
            for (int i = 0; i < productIdList.length; i++) {
                if (StringUtils.isNotEmpty(productIdList[i]) && StringUtils.isNumeric(productIdList[i])) {
                    ProductInfo pi = new ProductInfo();
                    pi.setProductId(Long.parseLong(productIdList[i]));
                    pi.setProductName(productNameList[i]);
                    productInfoList.add(pi);
                }
            }
            batchInfo.setProductInfoList(productInfoList);
        }
        batchInfoService.update(batchInfo);
        result.put("msg", "修改成功!");
        return result;
    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject delete(HttpServletRequest request) {
        String batchId = request.getParameter("batchId");
        String errorMsg = null;
        if (StringUtils.isEmpty(batchId)) {
            errorMsg = "系统错误，请联系管理员！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        BatchInfo bi = batchInfoService.getById(Long.parseLong(batchId));
        if (bi == null) {
            result.put("errorMsg", "数据已被其他人操作，请刷新!");
        } else {
            batchInfoService.delete(bi);
            result.put("msg", "删除成功!");
        }
        return result;
    }

    @RequestMapping(value = "/getSeqByGroupId", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject getSeqByGroupId(HttpServletRequest request) {
        String groupIdStr = request.getParameter("groupId");
        String errorMsg = null;
        if (StringUtils.isEmpty(groupIdStr)) {
            errorMsg = "系统错误，请联系管理员！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        Long groupId = Long.parseLong(groupIdStr);
        Long seq = batchInfoService.getSeqByGroupId(groupId);
        if (JcywConstants.CHANNEL_GROUP_TY_ID_1 == groupId) {
            result.put("batchCode", "TY" + (seq));
        } else if (JcywConstants.CHANNEL_GROUP_DB_ID_2 == groupId) {
            result.put("batchCode", "DB" + (seq));
        } else if (JcywConstants.CHANNEL_GROUP_QT_ID_3 == groupId) {
            result.put("batchCode", "QT" + (seq));
        }
        return result;
    }

    @RequestMapping(value = "/importTemplateBatch", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject importTemplateBatch(HttpServletRequest request) {
        String batchCode = request.getParameter("batchCode");
        String groupId = request.getParameter("groupId");
        String errorMsg = null;
        if (StringUtils.isEmpty(groupId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(batchCode)) {
            errorMsg = "请输入批次号！";
        }
        BatchInfo biCondition = new BatchInfo();
        biCondition.setBatchCode(batchCode);
        biCondition.setGroupId(Long.parseLong(groupId));
        biCondition.setActive(JcywConstants.ACTIVE_Y);
        List<BatchInfo> batchInfoList = batchInfoService.queryByVo(null, biCondition);
        if (batchInfoList == null || batchInfoList.size() <= 0) {
            errorMsg = "您输入的批次号错误，请重新输入！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        result.put("batchInfo", batchInfoList.get(0));
        return result;
    }

    @RequestMapping(value = "/getBatchInfoByCode", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject getBatchInfoByCode(HttpServletRequest request) {
        String batchCode = request.getParameter("batchCode");
        String errorMsg = null;
        if (StringUtils.isEmpty(batchCode)) {
            errorMsg = "请输入批次号！";
        }
        BatchInfo biCondition = new BatchInfo();
        biCondition.setBatchCode(batchCode);
        biCondition.setActive(JcywConstants.ACTIVE_Y);
        List<BatchInfo> batchInfoList = batchInfoService.queryByVo(null, biCondition);
        if (batchInfoList == null || batchInfoList.size() <= 0) {
            errorMsg = "您输入的批次号错误，请重新输入！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        result.put("batchInfo", batchInfoList.get(0));
        return result;
    }

}