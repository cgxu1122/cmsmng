package com.ifhz.core.service.stat.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.adapter.BatchProductRefAdapter;
import com.ifhz.core.adapter.ProductStatAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.ProductStat;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import com.ifhz.core.service.stat.ProductInstallStatService;
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
 * Date: 2014/8/24
 * Time: 23:52
 */
@Service("productInstallStatService")
public class ProductInstallStatServiceImpl implements ProductInstallStatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInstallStatServiceImpl.class);

    @Resource(name = "batchProductRefAdapter")
    private BatchProductRefAdapter batchProductRefAdapter;
    @Resource(name = "productStatAdapter")
    private ProductStatAdapter productStatAdapter;
    @Resource(name = "channelInfoCacheService")
    private ChannelInfoCacheService channelInfoCacheService;

    @Log
    @Override
    public boolean statProductInstall(DataLog record) {
        LOGGER.info("statProductInstall Stat ---------开始");
        if (StringUtils.isNotBlank(record.getBatchCode())) {
            List<Long> productIdList = batchProductRefAdapter.queryProductIdList(record.getBatchCode());
            LOGGER.info("BatchCode={},productIdList={}", record.getBatchCode(), JSON.toJSONString(productIdList));
            if (CollectionUtils.isNotEmpty(productIdList)) {
                for (Long productId : productIdList) {
                    String md5Key = StatConvertHandler.getMd5KeyForProductStat(record, productId);
                    if (StringUtils.isNotBlank(md5Key)) {
                        int count = 1;
                        while (true) {
                            count++;
                            int num = 0;
                            ProductStat productStat = productStatAdapter.getByMd5Key(md5Key);
                            LOGGER.info("source productStat={}", JSON.toJSONString(productStat));
                            if (productStat == null) {
                                productStat = StatConvertHandler.initProductStat(record, productId);
                                productStat.setMd5Key(md5Key);
                                productStat.setProductPrsDayNum(productStat.getProductPrsDayNum() + 1);

                                try {
                                    LOGGER.info("target productStat={}", JSON.toJSONString(productStat));
                                    num = productStatAdapter.insert(productStat);
                                } catch (Exception e) {
                                    LOGGER.error("LogStat insert error", e);
                                    continue;
                                }
                                if (num == 1) {
                                    LOGGER.info("ProductStat insert success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                                    break;
                                }
                            } else {
                                productStat.setProductPrsDayNum(productStat.getProductPrsDayNum() + 1);
                                LOGGER.info("target productStat={}", JSON.toJSONString(productStat));
                                num = productStatAdapter.update(productStat);
                            }
                            if (num == 1) {
                                LOGGER.info("ProductStat update success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                                break;
                            }
                            if (count == 10) {
                                LOGGER.info("ProductStat update failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                                break;
                            }
                        }
                    }
                }
            }
        }
        LOGGER.info("statProductInstall Stat ---------结束");
        return true;
    }

    @Override
    @Log
    public boolean statProductInstallForArrive(DataLog record) {
        LOGGER.info("statProductInstallForArrive Stat ---------开始");
        if (StringUtils.isNotBlank(record.getBatchCode())) {
            List<Long> productIdList = batchProductRefAdapter.queryProductIdList(record.getBatchCode());
            LOGGER.info("BatchCode={},productIdList={}", record.getBatchCode(), JSON.toJSONString(productIdList));
            if (CollectionUtils.isNotEmpty(productIdList)) {
                for (Long productId : productIdList) {
                    String md5Key = StatConvertHandler.getMd5KeyForProductStat(record, productId);
                    if (StringUtils.isNotBlank(md5Key)) {
                        int count = 0;
                        while (true) {
                            count++;
                            ProductStat productStat = productStatAdapter.getByMd5Key(md5Key);
                            LOGGER.info("source productStat={}", JSON.toJSONString(productStat));
                            if (productStat == null) {
                                LOGGER.info("ProductStat update failure ProductStat not found,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                                break;
                            }
                            productStat.setPrsActiveTotalNum(productStat.getPrsActiveTotalNum() + 1);
                            if (record.getActive() == CounterActive.Valid.value) {
                                productStat.setPrsActiveValidNum(productStat.getPrsActiveValidNum() + 1);
                            } else if (record.getActive() == CounterActive.Invalid_Replace.value) {
                                productStat.setPrsActiveInvalidNum(productStat.getPrsActiveInvalidNum() + 1);
                                productStat.setPrsInvalidReplaceNum(productStat.getPrsInvalidReplaceNum() + 1);
                            } else if (record.getActive() == CounterActive.Invalid_Uninstall.value) {
                                productStat.setPrsActiveInvalidNum(productStat.getPrsActiveInvalidNum() + 1);
                                productStat.setPrsInvalidUninstallNum(productStat.getPrsInvalidUninstallNum() + 1);
                            } else if (record.getActive() == CounterActive.Invalid_Re_And_Un.value) {
                                productStat.setPrsActiveInvalidNum(productStat.getPrsActiveInvalidNum() + 1);
                                productStat.setPrsInvalidUnAndReNum(productStat.getPrsInvalidUnAndReNum() + 1);
                            }
                            LOGGER.info("target productStat={}", JSON.toJSONString(productStat));
                            int num = productStatAdapter.update(productStat);
                            if (num == 1) {
                                LOGGER.info("ProductStat update success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                                break;
                            }
                            if (count == 10) {
                                LOGGER.info("ProductStat update failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                                break;
                            }
                        }
                    }
                }
            }
        }
        LOGGER.info("statProductInstallForArrive Stat ---------结束");

        return false;
    }
}
