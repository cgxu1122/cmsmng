package com.ifhz.core.service.stat.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.adapter.stat.ProductArriveStatTempAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.stat.ProductArriveStat;
import com.ifhz.core.po.stat.ProductArriveStatTemp;
import com.ifhz.core.service.stat.ProductArriveStatTempService;
import com.ifhz.core.service.stat.handle.ArriveStatConvertHandler;
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

    @Log
    @Override
    public boolean asyncData(ProductArriveStat record, boolean isInsert) {
        if (record != null) {
            SyncDataTask task = new SyncDataTask(record, isInsert);
            taskExecutor.execute(task);
        }
        return true;
    }

    private class SyncDataTask implements Runnable {

        private final ProductArriveStat productArriveStat;
        private final boolean isInsert;

        private SyncDataTask(ProductArriveStat productArriveStat, boolean isInsert) {
            this.productArriveStat = productArriveStat;
            this.isInsert = isInsert;
        }

        @Override
        public void run() {
            LOGGER.info("isInsert={},ProductArriveStat = {}", isInsert, JSON.toJSONString(productArriveStat));
            ProductArriveStatTemp productArriveStatTemp = ArriveStatConvertHandler.convertProductArriveStatTemp(productArriveStat);
            int count = 0;
            while (true) {
                count++;
                int num = 0;
                if (isInsert) {
                    try {
                        num = productArriveStatTempAdapter.insert(productArriveStatTemp);
                    } catch (Exception e) {
                        LOGGER.error("ProductArriveStatTemp insert error", e);
                        continue;
                    }
                    if (num == 1) {
                        LOGGER.info("ProductArriveStatTemp insert success, ProductArriveStatTemp={}", JSON.toJSONString(productArriveStatTemp));
                        break;
                    }
                } else {
                    ProductArriveStatTemp temp = productArriveStatTempAdapter.getById(productArriveStat.getId());
                    LOGGER.info("数据库中数据为：ProductArriveStatTemp={}", JSON.toJSONString(temp));
                    if (temp != null && temp.getVersion() < productArriveStatTemp.getVersion()) {
                        num = productArriveStatTempAdapter.update(productArriveStatTemp);
                        if (num == 1) {
                            LOGGER.info("ProductArriveStatTemp update success, ProductArriveStatTemp={}", JSON.toJSONString(productArriveStatTemp));
                            break;
                        }
                    } else {
                        LOGGER.info("ProductArriveStatTemp not found or version lessthan DB data version, ProductArriveStatTemp={}", JSON.toJSONString(productArriveStatTemp));
                        break;
                    }
                }
                if (count == 10) {
                    LOGGER.info("ProductArriveStatTemp save failure, ProductArriveStatTemp={}", JSON.toJSONString(productArriveStatTemp));
                    break;
                }
            }
        }
    }
}
