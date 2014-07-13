package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.ProductInfo;
import com.ifhz.core.service.product.ProductInfoService;
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
@RequestMapping("/tymng/productInfo")
public class ProductInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInfoController.class);
    @Autowired
    private ProductInfoService productInfoService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("productInfo/index");
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
        String productNameCondition = request.getParameter("productNameCondition");
        ProductInfo pi = new ProductInfo();
        pi.setActive(JcywConstants.ACTIVE_Y);
        pi.setProductNameCondition(productNameCondition);
        List<ProductInfo> list = productInfoService.queryByVo(page, pi);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

    @RequestMapping(value = "/insert", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject insert(HttpServletRequest request) {
        String productName = request.getParameter("productName");
        String partnerId = request.getParameter("partnerId");
        String queryDataSource = request.getParameter("queryDataSource");
        String queryStartTime = request.getParameter("queryStartTime");
        String errorMsg = null;
        if (StringUtils.isEmpty(productName) || productName.length() > 50) {
            errorMsg = "请正确输入产品名称！";
        } else if (StringUtils.isEmpty(partnerId)) {
            errorMsg = "请选择合作方！";
        } else if (StringUtils.isEmpty(queryDataSource)) {
            errorMsg = "请选择查看权限！";
        } else if (StringUtils.isEmpty(queryStartTime)) {
            errorMsg = "请选择合作方查看开始时间！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        //设备编码唯一性校验
        ProductInfo pi = new ProductInfo();
        pi.setProductName(productName.trim());
        pi.setActive(JcywConstants.ACTIVE_Y);
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(1);
        List<ProductInfo> list = productInfoService.queryByVo(page, pi);
        if (list != null && list.size() > 0) {
            errorMsg = "产品名称重复，请重新输入！";
            result.put("errorMsg", errorMsg);
            return result;
        }
        pi.setPartnerId(Long.parseLong(partnerId));
        pi.setQueryDataSource(queryDataSource);
        pi.setQueryStartTime(DateFormatUtils.parse(queryStartTime, GlobalConstants.DATE_FORMAT_DPT));
        //TODO 添加用户名密码
        productInfoService.insert(pi);
        result.put("msg", "添加成功!");
        return result;
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject update(HttpServletRequest request) {
        String productId = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String partnerId = request.getParameter("partnerId");
        String queryDataSource = request.getParameter("queryDataSource");
        String queryStartTime = request.getParameter("queryStartTime");
        String errorMsg = null;
        if (StringUtils.isEmpty(productId)) {
            errorMsg = "系统错误，请联系管理员！";
        } else if (StringUtils.isEmpty(productName) || productName.length() > 50) {
            errorMsg = "请正确输入产品名称！";
        } else if (StringUtils.isEmpty(partnerId)) {
            errorMsg = "请选择合作方！";
        } else if (StringUtils.isEmpty(queryDataSource)) {
            errorMsg = "请选择查看权限！";
        } else if (StringUtils.isEmpty(queryStartTime)) {
            errorMsg = "请选择合作方查看开始时间！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        ProductInfo productInfo = productInfoService.getById(Long.parseLong(productId));
        if (productInfo == null) {
            result.put("errorMsg", "数据已被删除，请刷新!");
            return result;
        }
        //设备编码唯一性校验
        ProductInfo pi = new ProductInfo();
        pi.setProductName(productName.trim());
        pi.setActive(JcywConstants.ACTIVE_Y);
        Pagination page = new Pagination();
        page.setCurrentPage(1);
        page.setPageSize(1);
        List<ProductInfo> list = productInfoService.queryByVo(page, pi);
        if (list != null && list.size() > 0) {
            for (ProductInfo repeatCodePi : list) {
                if (!repeatCodePi.getProductId().equals(productInfo.getProductId())) {
                    result.put("errorMsg", "产品名称重复，请重新输入！");
                    return result;
                }
            }
        }
        productInfo.setProductName(productName.trim());
        productInfo.setPartnerId(Long.decode(partnerId));
        productInfo.setQueryDataSource(queryDataSource);
        productInfo.setQueryStartTime(DateFormatUtils.parse(queryStartTime, GlobalConstants.DATE_FORMAT_DPT));
        productInfoService.update(productInfo);
        result.put("msg", "修改成功!");
        return result;
    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JSONObject delete(HttpServletRequest request) {
        String productId = request.getParameter("productId");
        String errorMsg = null;
        if (StringUtils.isEmpty(productId)) {
            errorMsg = "系统错误，请联系管理员！";
        }
        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(errorMsg)) {
            result.put("errorMsg", errorMsg);
            return result;
        }
        ProductInfo pi = productInfoService.getById(Long.parseLong(productId));
        if (pi == null) {
            result.put("errorMsg", "数据已被其他人操作，请刷新!");
        } else {
            productInfoService.delete(pi);
            result.put("msg", "删除成功!");
        }
        return result;
    }

}