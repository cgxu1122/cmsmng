package com.ifhz.core.service.stat.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.adapter.BatchProductRefAdapter;
import com.ifhz.core.adapter.stat.ProductArriveStatAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.stat.ProductArriveStat;
import com.ifhz.core.service.stat.ProductArriveStatService;
import com.ifhz.core.service.stat.ProductArriveStatTempService;
import com.ifhz.core.service.stat.constants.CounterActive;
import com.ifhz.core.service.stat.handle.ArriveStatConvertHandler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public class ProductArriveStatServiceImpl implements ProductArriveStatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductArriveStatServiceImpl.class);

    private BatchProductRefAdapter batchProductRefAdapter;
    private ProductArriveStatAdapter productArriveStatAdapter;
    private ProductArriveStatTempService productArriveStatTempService;

    @Log
    @Override
    public boolean statProductArrive(DataLog record) {
        LOGGER.info("statProductArrive Stat ---------开始");
        if (StringUtils.isNotBlank(record.getBatchCode())) {
            List<Long> productIdList = batchProductRefAdapter.queryProductIdList(record.getBatchCode());
            LOGGER.info("BatchCode={},productIdList={}", record.getBatchCode(), JSON.toJSONString(productIdList));
            if (CollectionUtils.isNotEmpty(productIdList)) {
                for (Long productId : productIdList) {
                    String md5Key = ArriveStatConvertHandler.getMd5KeyForProductArriveStat(record, productId);
                    int count = 0;
                    while (true) {
                        count++;
                        int num = 0;
                        ProductArriveStat productArriveStat = productArriveStatAdapter.getByMd5Key(md5Key);
                        LOGGER.info("source productArriveStat={}", JSON.toJSONString(productArriveStat));
                        if (productArriveStat == null) {
                            productArriveStat = ArriveStatConvertHandler.initProductArriveStat(record, productId);
                            productArriveStat.setMd5Key(md5Key);
                            LOGGER.info("LogArriveStat not found,  md5Key={}, productArriveStat={}", md5Key, JSON.toJSONString(productArriveStat));
                            productArriveStat.setTotalNum(productArriveStat.getTotalNum() + 1);
                            if (record.getActive() == CounterActive.Valid.value) {
                                productArriveStat.setValidNum(productArriveStat.getValidNum() + 1);
                            } else if (record.getActive() == CounterActive.Invalid_Replace.value) {
                                productArriveStat.setInvalidNum(productArriveStat.getInvalidNum() + 1);
                                productArriveStat.setReplaceNum(productArriveStat.getReplaceNum() + 1);
                            } else if (record.getActive() == CounterActive.Invalid_Uninstall.value) {
                                productArriveStat.setInvalidNum(productArriveStat.getInvalidNum() + 1);
                                productArriveStat.setUninstallNum(productArriveStat.getUninstallNum() + 1);
                            } else if (record.getActive() == CounterActive.Invalid_Re_And_Un.value) {
                                productArriveStat.setInvalidNum(productArriveStat.getInvalidNum() + 1);
                                productArriveStat.setUnAndReNum(productArriveStat.getUnAndReNum() + 1);
                            }
                            try {
                                LOGGER.info("target productArriveStat={}", JSON.toJSONString(productArriveStat));
                                num = productArriveStatAdapter.insert(productArriveStat);
                            } catch (Exception e) {
                                LOGGER.error("LogArriveStat insert error", e);
                                continue;
                            }
                        } else {
                            productArriveStat.setTotalNum(productArriveStat.getTotalNum() + 1);
                            if (record.getActive() == CounterActive.Valid.value) {
                                productArriveStat.setValidNum(productArriveStat.getValidNum() + 1);
                            } else if (record.getActive() == CounterActive.Invalid_Replace.value) {
                                productArriveStat.setInvalidNum(productArriveStat.getInvalidNum() + 1);
                                productArriveStat.setReplaceNum(productArriveStat.getReplaceNum() + 1);
                            } else if (record.getActive() == CounterActive.Invalid_Uninstall.value) {
                                productArriveStat.setInvalidNum(productArriveStat.getInvalidNum() + 1);
                                productArriveStat.setUninstallNum(productArriveStat.getUninstallNum() + 1);
                            } else if (record.getActive() == CounterActive.Invalid_Re_And_Un.value) {
                                productArriveStat.setInvalidNum(productArriveStat.getInvalidNum() + 1);
                                productArriveStat.setUnAndReNum(productArriveStat.getUnAndReNum() + 1);
                            }
                            LOGGER.info("target productArriveStat={}", JSON.toJSONString(productArriveStat));
                            num = productArriveStatAdapter.update(productArriveStat);
                        }

                        if (num == 1) {
                            LOGGER.info("ProductArriveStat update success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                            break;
                        }
                        if (count == 10) {
                            LOGGER.info("ProductArriveStat update failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                            break;
                        }
                    }
                }
            }
        }
        LOGGER.info("statProductArrive Stat ---------结束");

        return true;
    }
}
