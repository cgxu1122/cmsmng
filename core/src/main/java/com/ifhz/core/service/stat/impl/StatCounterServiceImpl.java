package com.ifhz.core.service.stat.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.adapter.BatchProductRefAdapter;
import com.ifhz.core.adapter.CounterTempLogAdapter;
import com.ifhz.core.adapter.LogStatAdapter;
import com.ifhz.core.adapter.ProductStatAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.constants.TempLogType;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.po.ProductStat;
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
    @Resource(name = "counterTempLogAdapter")
    private CounterTempLogAdapter counterTempLogAdapter;

    @Log
    public void updateStat(DataLog dataLog) {
        LOGGER.info("Counter Stat ---------开始");
        if (dataLog != null) {
            boolean isUpdateTempLog = false;
            String md5Key = StatConvertHandler.getMd5KeyForLogStat(dataLog);
            LOGGER.info("LogStat md5Key={}", md5Key);
            if (StringUtils.isNotBlank(md5Key)) {
                int logStatNum = 0;
                while (true) {
                    logStatNum++;
                    LogStat logStat = logStatAdapter.getByMd5Key(md5Key);
                    LOGGER.info("source logStat={}", JSON.toJSONString(logStat));
                    if (logStat == null) {
                        LOGGER.info("LogStat update failure LogStat not found,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(dataLog));
                        break;
                    }
                    logStat.setPrsActiveTotalNum(logStat.getPrsActiveTotalNum() + 1);
                    if (dataLog.getActive() == CounterActive.Valid.value) {
                        logStat.setPrsActiveValidNum(logStat.getPrsActiveValidNum() + 1);
                    } else if (dataLog.getActive() == CounterActive.Invalid_Replace.value) {
                        logStat.setPrsActiveInvalidNum(logStat.getPrsActiveInvalidNum() + 1);
                        logStat.setPrsInvalidReplaceNum(logStat.getPrsInvalidReplaceNum() + 1);
                    } else if (dataLog.getActive() == CounterActive.Invalid_Uninstall.value) {
                        logStat.setPrsActiveInvalidNum(logStat.getPrsActiveInvalidNum() + 1);
                        logStat.setPrsInvalidUninstallNum(logStat.getPrsInvalidUninstallNum() + 1);
                    } else if (dataLog.getActive() == CounterActive.Invalid_Re_And_Un.value) {
                        logStat.setPrsActiveInvalidNum(logStat.getPrsActiveInvalidNum() + 1);
                        logStat.setPrsInvalidUnAndReNum(logStat.getPrsInvalidUnAndReNum() + 1);
                    }
                    LOGGER.info("target logStat={}", JSON.toJSONString(logStat));
                    int num = logStatAdapter.update(logStat);
                    if (num == 1) {
                        isUpdateTempLog = true;
                        LOGGER.info("LogStat update success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(dataLog));
                        break;
                    } else {
                        LOGGER.info("LogStat update failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(dataLog));
                    }
                    if (logStatNum == 10) {
                        LOGGER.info("LogStat update failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(dataLog));
                        break;
                    }
                }
            }
            if (StringUtils.isNotBlank(dataLog.getBatchCode())) {
                List<Long> productIdList = batchProductRefAdapter.queryProductIdList(dataLog.getBatchCode());
                LOGGER.info("BatchCode={},productIdList={}", dataLog.getBatchCode(), JSON.toJSONString(productIdList));
                if (CollectionUtils.isNotEmpty(productIdList)) {
                    for (Long productId : productIdList) {
                        String productMd5Key = StatConvertHandler.getMd5KeyForProductStat(dataLog, productId);
                        if (StringUtils.isNotBlank(productMd5Key)) {
                            int productStatNum = 1;
                            while (true) {
                                productStatNum++;
                                ProductStat productStat = productStatAdapter.getByMd5Key(productMd5Key);
                                LOGGER.info("source productStat={}", JSON.toJSONString(productStat));
                                if (productStat == null) {
                                    LOGGER.info("ProductStat update failure ProductStat not found,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(dataLog));
                                    break;
                                }
                                productStat.setPrsActiveTotalNum(productStat.getPrsActiveTotalNum() + 1);
                                if (dataLog.getActive() == CounterActive.Valid.value) {
                                    productStat.setPrsActiveValidNum(productStat.getPrsActiveValidNum() + 1);
                                } else if (dataLog.getActive() == CounterActive.Invalid_Replace.value) {
                                    productStat.setPrsActiveInvalidNum(productStat.getPrsActiveInvalidNum() + 1);
                                    productStat.setPrsInvalidReplaceNum(productStat.getPrsInvalidReplaceNum() + 1);
                                } else if (dataLog.getActive() == CounterActive.Invalid_Uninstall.value) {
                                    productStat.setPrsActiveInvalidNum(productStat.getPrsActiveInvalidNum() + 1);
                                    productStat.setPrsInvalidUninstallNum(productStat.getPrsInvalidUninstallNum() + 1);
                                } else if (dataLog.getActive() == CounterActive.Invalid_Re_And_Un.value) {
                                    productStat.setPrsActiveInvalidNum(productStat.getPrsActiveInvalidNum() + 1);
                                    productStat.setPrsInvalidUnAndReNum(productStat.getPrsInvalidUnAndReNum() + 1);
                                }
                                LOGGER.info("target productStat={}", JSON.toJSONString(productStat));
                                int num = productStatAdapter.update(productStat);
                                if (num == 1) {
                                    isUpdateTempLog = true;
                                    LOGGER.info("ProductStat update success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(dataLog));
                                    break;
                                }
                                if (productStatNum == 10) {
                                    LOGGER.info("ProductStat update failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(dataLog));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (isUpdateTempLog) {
                counterTempLogAdapter.update(dataLog.getImei(), TempLogType.Done.value);
            }
        }
        LOGGER.info("Counter Stat ---------结束");
    }
}
