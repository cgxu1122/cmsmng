/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.tymng.controller;

import com.ifhz.core.base.BaseController;
import com.ifhz.core.service.auther.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author luyujian
 */
@Controller
@RequestMapping("/tymng/auth/productauth")
public class ProductAuthController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductAuthController.class);

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/index")
    public String index() {
        return "auth/product/index";
    }

}
