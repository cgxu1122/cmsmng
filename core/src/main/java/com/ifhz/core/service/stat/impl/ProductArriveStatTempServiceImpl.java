package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.stat.ProductArriveStatTempAdapter;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.stat.ProductArriveStatTempService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:51
 */
public class ProductArriveStatTempServiceImpl implements ProductArriveStatTempService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductArriveStatTempServiceImpl.class);

    private TaskExecutor taskExecutor;
    private ProductArriveStatTempAdapter productArriveStatTempAdapter;

    @Override
    public boolean statTempProductArrive(DataLog record) {
        //TODO
        return false;
    }
}
