package com.ifhz.hzfmng.controller;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PartnerInfo;
import com.ifhz.core.po.ProductInfo;
import com.ifhz.core.service.partner.PartnerInfoService;
import com.ifhz.core.service.product.ProductInfoService;
import com.ifhz.core.shiro.utils.CurrentUserUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/hzfmng/productInfo")
public class ProductInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInfoController.class);
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private PartnerInfoService partnerInfoService;

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
        PartnerInfo partnerInfo = partnerInfoService.getPartnerInfoByUserId(CurrentUserUtil.getUserId());
        if (partnerInfo != null) {
            pi.setPartnerId(partnerInfo.getPartnerId());
            pi.setQueryDataSource(JcywConstants.ACTIVE_Y);
            pi.setQueryStartTime(new Date());
        }
        pi.setActive(JcywConstants.ACTIVE_Y);
        pi.setProductNameCondition(productNameCondition);
        List<ProductInfo> list = productInfoService.queryByVo(page, pi);
        JSONObject result = new JSONObject();
        result.put("total", page.getTotalCount());
        result.put("rows", list);
        return result;
    }

}