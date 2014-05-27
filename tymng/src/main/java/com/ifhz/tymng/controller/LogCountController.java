package com.ifhz.tymng.controller;


import com.ifhz.core.base.BaseController;
import com.ifhz.core.po.LogCount;
import com.ifhz.core.po.ProductCount;
import com.ifhz.core.service.statistics.LogCountService;
import com.ifhz.core.service.statistics.ProductCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * Created by lm on 14-5-17.
 */
@Controller
@RequestMapping("/logCount")
public class LogCountController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogCountController.class);

    @Autowired
    private LogCountService logCountService;
    @Autowired
    private ProductCountService productCountService;

    @RequestMapping("/index")
    public String index() {
        System.out.println("name=cmsmng/LogCount");
        return "count/testCount";
    }

    @RequestMapping("/insertData")
    public String insertData(LogCount logCount) {
        System.out.println(logCount.getId() + "\t insertData=" + logCount.getGroupId());
        logCount.setCountTime(new Date());
        logCountService.insert(logCount);
        return "count/testCount";
    }

    @RequestMapping("/insertProductData")
    public String insertProductData(ProductCount productCount) {
        System.out.println("insertProductCountData=" + productCount.getGroupId());
        productCount.setCountTime(new Date());
        productCountService.insert(productCount);
        return "count/testCount";
    }

    @RequestMapping("/autoCountLog")
    public String autoCountLog() {
        System.out.println(" autoCountLog=");
        try {
            //logCountService.countLogByDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "count/testCount";
    }

}
