package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.stat.ProductArriveStatTempAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.ProductArriveStatTemp;
import com.ifhz.core.service.stat.ProductArriveStatTempService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:51
 */
@Service
public class ProductArriveStatTempServiceImpl implements ProductArriveStatTempService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductArriveStatTempServiceImpl.class);

    private TaskExecutor taskExecutor;
    private ProductArriveStatTempAdapter productArriveStatTempAdapter;

    @Override
    public int insert(ProductArriveStatTemp productArriveStatTemp) {
        return 0;
    }

    @Override
    public boolean syncProductArriveStat() {
        return false;
    }

    @Override
    public List<ProductArriveStatTemp> queryByVo(Pagination pagination, ProductArriveStatTemp record) {
        return null;
    }
}
