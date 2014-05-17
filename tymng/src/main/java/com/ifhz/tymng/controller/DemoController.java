package com.ifhz.tymng.controller;

import com.ifhz.core.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author radishlee
 */
@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping("/index")
    public String index() {
        return "demo/index";
    }
}