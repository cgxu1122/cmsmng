/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.tymng.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ifhz.core.base.BaseController;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.Active;
import com.ifhz.core.po.ProductInfo;
import com.ifhz.core.po.auth.SysUser;
import com.ifhz.core.po.auth.SysUserProductRef;
import com.ifhz.core.service.auther.ProductAuthService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author luyujian
 */
@Controller
@RequestMapping("/tymng/auth/productauth")
public class ProductAuthController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductAuthController.class);

    @Autowired
    private ProductAuthService productAuthService;

    @RequestMapping("/index")
    public String index() {
        return "auth/product/index";
    }

    @RequestMapping("/blank")
    public String blank() {
        return "auth/product/blank";
    }

    @RequestMapping("/userIndex")
    public String userList() {
        return "auth/product/userList";
    }

    @RequestMapping("/productIndex")
    public ModelAndView productIndex(@RequestParam("userId") long userId) {
        ModelAndView mav = new ModelAndView("auth/product/productList");
        mav.addObject("userId", userId);
        return mav;
    }

    @RequestMapping(value = "/userList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject userList(@RequestParam(value = "page", required = true) Integer pageNum,
                        @RequestParam(value = "rows", required = true) Integer pageSize,
                        @RequestParam(value = "searchValue", required = false) String searchValue,
                        HttpServletRequest request) {
        /**分页*/
        Pagination pagination = new Pagination();
        if (pageNum != null) {
            pagination.setCurrentPage(pageNum);
        }
        if (pageSize != null) {
            pagination.setPageSize(pageSize);
        }
        List<SysUser> list = productAuthService.queryUserList(pagination, searchValue);
        if (CollectionUtils.isEmpty(list)) {
            list = Lists.newArrayList();
        }
        JSONObject result = new JSONObject();
        result.put("total", pagination.getTotalCount());
        result.put("rows", list);
        return result;
    }


    @RequestMapping(value = "/productList", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject productList(@RequestParam(value = "userId", required = true) Long userId,
                           HttpServletRequest request) {
        JSONObject result = new JSONObject();
        Map<String, List<ProductInfo>> map = productAuthService.queryProductList(userId);
        if (MapUtils.isNotEmpty(map)) {
            result.putAll(map);
        }

        return result;
    }

    @RequestMapping(value = "/productAuth", produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    JSONObject productAuth(@RequestParam(value = "userId", required = true) Long userId,
                           @RequestParam(value = "productId", required = true) Long productId,
                           @RequestParam(value = "type", required = true, defaultValue = "Y") String type,
                           HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String msg = "授权成功";
        if (userId == null || productId == null) {
            msg = "请选择勾选产品操作";
        } else {
            Active active = Active.getByDbValue(type.trim());
            if (active != null) {
                SysUserProductRef ref = new SysUserProductRef();
                ref.setUserId(userId);
                ref.setProductId(productId);
                ref.setCreateTime(new Date());
                boolean ret = productAuthService.authProduct(active, ref);
                LOGGER.info("ret={}", ret);
            } else {
                msg = "授权失败";
            }
        }
        result.put("msg", msg);

        return result;
    }


}
