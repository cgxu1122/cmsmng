package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.BatchProductRefAdapter;
import com.ifhz.core.adapter.LogStatAdapter;
import com.ifhz.core.adapter.ProductStatAdapter;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.api.bean.StatUpdateBean;
import com.ifhz.core.service.stat.StatCounterService;
import com.ifhz.core.service.stat.constants.CounterActive;
import com.ifhz.core.service.stat.handle.StatConvertHandler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/22
 * Time: 18:14
 */
@Service("statCounterService")
public class StatCounterServiceImpl implements StatCounterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatCounterServiceImpl.class);

    @Resource(name = "batchProductRefAdapter")
    private BatchProductRefAdapter batchProductRefAdapter;
    @Resource(name = "logStatAdapter")
    private LogStatAdapter logStatAdapter;
    @Resource(name = "productStatAdapter")
    private ProductStatAdapter productStatAdapter;


    public void updateStat(DataLog dataLog) {
        LOGGER.info("Counter Stat ---------开始");
        if (dataLog != null) {
            String md5Key = StatConvertHandler.getMd5KeyForLogStat(dataLog);
            LOGGER.info("LogStat md5Key={}", md5Key);
            StatUpdateBean logUpdate = new StatUpdateBean(true, md5Key);
            if (dataLog.getActive() == CounterActive.Valid.value) {
                logUpdate.setUpdateValidNum(true);
            } else if (dataLog.getActive() == CounterActive.Invalid_Replace.value) {
                logUpdate.setUpdateInvalidNum(true);
                logUpdate.setUpdateReplaceNum(true);
            } else if (dataLog.getActive() == CounterActive.Invalid_Uninstall.value) {
                logUpdate.setUpdateInvalidNum(true);
                logUpdate.setUpdateUninstallNum(true);
            }
            int t = logStatAdapter.updateStat(logUpdate);
            LOGGER.info("LogStat md5Key={}, returnObj={}", md5Key, t);

            if (StringUtils.isNotBlank(dataLog.getBatchCode())) {
                List<Long> productIdList = batchProductRefAdapter.queryProductIdList(dataLog.getBatchCode());
                if (CollectionUtils.isNotEmpty(productIdList)) {
                    for (Long productId : productIdList) {
                        String productMd5Key = StatConvertHandler.getMd5KeyForProductStat(dataLog, productId);
                        LOGGER.info("ProductStat md5Key={}", productMd5Key);
                        StatUpdateBean productUpdate = new StatUpdateBean(true, productMd5Key);
                        if (dataLog.getActive() == CounterActive.Valid.value) {
                            productUpdate.setUpdateValidNum(true);
                        } else if (dataLog.getActive() == CounterActive.Invalid_Replace.value) {
                            productUpdate.setUpdateInvalidNum(true);
                            productUpdate.setUpdateReplaceNum(true);
                        } else if (dataLog.getActive() == CounterActive.Invalid_Uninstall.value) {
                            productUpdate.setUpdateInvalidNum(true);
                            productUpdate.setUpdateUninstallNum(true);
                        }

                        int y = productStatAdapter.updateStat(productUpdate);
                        LOGGER.info("ProductStat md5Key={},returnObj={}", productMd5Key, y);
                    }
                }
            }
        }
        LOGGER.info("Counter Stat ---------结束");
    }

}
