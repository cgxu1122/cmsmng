package com.ifhz.core.service.stat.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.adapter.BatchProductRefAdapter;
import com.ifhz.core.adapter.ProductStatAdapter;
import com.ifhz.core.adapter.stat.ProductInstallStatAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GroupEnums;
import com.ifhz.core.po.*;
import com.ifhz.core.po.stat.ProductInstallStat;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import com.ifhz.core.service.cache.ModelInfoCacheService;
import com.ifhz.core.service.cache.ProductInfoCacheService;
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
    @Resource
    private ProductInstallStatAdapter productInstallStatAdapter;
    @Resource
    private ProductInfoCacheService productInfoCacheService;
    @Resource
    private ModelInfoCacheService modelInfoCacheService;
    @Resource(name = "channelInfoCacheService")
    private ChannelInfoCacheService channelInfoCacheService;


    @Override
    @Log
    public List<ProductInstallStat> queryByVo(Pagination pagination, ProductInstallStat record) {
        List<ProductInstallStat> result = productInstallStatAdapter.queryByVo(pagination, record);
        if (CollectionUtils.isNotEmpty(result)) {
            for (ProductInstallStat productInstallStat : result) {
                String ua = productInstallStat.getUa();
                if (StringUtils.isNotEmpty(ua)) {
                    ModelInfo modelInfo = null;
                    try {
                        modelInfo = modelInfoCacheService.getByUaAndGrouId(ua, productInstallStat.getGroupId());
                    } catch (Exception e) {
                        LOGGER.error("getByUaAndGrouId error", e);
                    }
                    if (modelInfo != null) {
                        productInstallStat.setModelName(modelInfo.getModelName() + "(" + ua + ")");
                    } else {
                        productInstallStat.setModelName("未知(" + ua + ")");
                    }
                } else {
                    productInstallStat.setModelName("未知()");
                }
                if (productInstallStat.getGroupId() != null) {
                    productInstallStat.setGroupName(GroupEnums.fromByValue(productInstallStat.getGroupId()).name);
                }

                Long channelId = productInstallStat.getChannelId();
                if (channelId != null) {
                    ChannelInfo channelInfo = null;
                    try {
                        channelInfo = channelInfoCacheService.getByChannelId(channelId);
                    } catch (Exception e) {
                        LOGGER.error("getByChannelId error", e);
                    }
                    if (channelInfo != null) {
                        productInstallStat.setChannelName(channelInfo.getChannelName());
                    } else {
                        productInstallStat.setModelName("未知");
                    }
                }

                if (productInstallStat.getProductId() != null) {
                    ProductInfo productInfo = productInfoCacheService.getById(productInstallStat.getProductId());
                    if (productInfo != null) {
                        productInstallStat.setProductName(productInfo.getProductName());
                    }
                }
            }
        }

        return result;
    }

    @Override
    @Log
    public List<ProductInstallStat> querySumByVo(Pagination pagination, ProductInstallStat record) {
        List<ProductInstallStat> result = productInstallStatAdapter.querySumByVo(pagination, record);
        if (CollectionUtils.isNotEmpty(result)) {
            for (ProductInstallStat productInstallStat : result) {
                String ua = productInstallStat.getUa();
                productInstallStat.setModelName(ua);
                if (productInstallStat.getProductId() != null) {
                    ProductInfo productInfo = productInfoCacheService.getById(productInstallStat.getProductId());
                    if (productInfo != null) {
                        productInstallStat.setProductName(productInfo.getProductName());
                    }
                }
            }
        }

        return result;
    }

    @Override
    public ProductInstallStat queryCountByVo(ProductInstallStat record) {
        return productInstallStatAdapter.queryCountByVo(record);
    }


    @Log
    @Override
    public boolean statProductProcess(DataLog record) {
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


    @Log
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
                            ProductInstallStat productInstallStat = productInstallStatAdapter.getByMd5Key(md5Key);
                            LOGGER.info("source productInstallStat={}", JSON.toJSONString(productInstallStat));
                            if (productInstallStat == null) {
                                productInstallStat = StatConvertHandler.initProductInstallStat(record, productId);
                                productInstallStat.setMd5Key(md5Key);
                                productInstallStat.setInstallTotalNum(productInstallStat.getInstallTotalNum() + 1);
                                try {
                                    LOGGER.info("target productInstallStat={}", JSON.toJSONString(productInstallStat));
                                    num = productInstallStatAdapter.insert(productInstallStat);
                                } catch (Exception e) {
                                    LOGGER.error("LogStat insert error", e);
                                    continue;
                                }
                                if (num == 1) {
                                    LOGGER.info("productInstallStat insert success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                                    break;
                                }
                            } else {
                                productInstallStat.setInstallTotalNum(productInstallStat.getInstallTotalNum() + 1);
                                LOGGER.info("target productInstallStat={}", JSON.toJSONString(productInstallStat));
                                num = productInstallStatAdapter.update(productInstallStat);
                            }
                            if (num == 1) {
                                LOGGER.info("productInstallStat update success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                                break;
                            }
                            if (count == 10) {
                                LOGGER.info("productInstallStat update failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
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


    @Log
    public boolean statProductInstallTemp(DataLog record) {
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
                            ProductInstallStat productInstallStat = productInstallStatAdapter.getByMd5Key(md5Key);
                            LOGGER.info("source productInstallStat={}", JSON.toJSONString(productInstallStat));
                            if (productInstallStat == null) {
                                productInstallStat = StatConvertHandler.initProductInstallStat(record, productId);
                                productInstallStat.setMd5Key(md5Key);
                                productInstallStat.setInstallTotalNum(productInstallStat.getInstallTotalNum() + 1);
                                if (record.getCounterUploadTime() != null && record.getActive() != null && record.getActive() != 0) {
                                    productInstallStat.setTotalNum(productInstallStat.getTotalNum() + 1);
                                    if (record.getActive() == CounterActive.Valid.value) {
                                        productInstallStat.setValidNum(productInstallStat.getValidNum() + 1);
                                    } else if (record.getActive() == CounterActive.Invalid_Replace.value) {
                                        productInstallStat.setInvalidNum(productInstallStat.getInvalidNum() + 1);
                                        productInstallStat.setReplaceNum(productInstallStat.getReplaceNum() + 1);
                                    } else if (record.getActive() == CounterActive.Invalid_Uninstall.value) {
                                        productInstallStat.setInvalidNum(productInstallStat.getInvalidNum() + 1);
                                        productInstallStat.setUninstallNum(productInstallStat.getUninstallNum() + 1);
                                    } else if (record.getActive() == CounterActive.Invalid_Re_And_Un.value) {
                                        productInstallStat.setInvalidNum(productInstallStat.getInvalidNum() + 1);
                                        productInstallStat.setUnAndReNum(productInstallStat.getUnAndReNum() + 1);
                                    }
                                }
                                try {
                                    LOGGER.info("target productInstallStat={}", JSON.toJSONString(productInstallStat));
                                    num = productInstallStatAdapter.insert(productInstallStat);
                                } catch (Exception e) {
                                    LOGGER.error("LogStat insert error", e);
                                    continue;
                                }
                                if (num == 1) {
                                    LOGGER.info("productInstallStat insert success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                                    break;
                                }
                            } else {
                                productInstallStat.setInstallTotalNum(productInstallStat.getInstallTotalNum() + 1);
                                if (record.getCounterUploadTime() != null && record.getActive() != null && record.getActive() != 0) {
                                    productInstallStat.setTotalNum(productInstallStat.getTotalNum() + 1);
                                    if (record.getActive() == CounterActive.Valid.value) {
                                        productInstallStat.setValidNum(productInstallStat.getValidNum() + 1);
                                    } else if (record.getActive() == CounterActive.Invalid_Replace.value) {
                                        productInstallStat.setInvalidNum(productInstallStat.getInvalidNum() + 1);
                                        productInstallStat.setReplaceNum(productInstallStat.getReplaceNum() + 1);
                                    } else if (record.getActive() == CounterActive.Invalid_Uninstall.value) {
                                        productInstallStat.setInvalidNum(productInstallStat.getInvalidNum() + 1);
                                        productInstallStat.setUninstallNum(productInstallStat.getUninstallNum() + 1);
                                    } else if (record.getActive() == CounterActive.Invalid_Re_And_Un.value) {
                                        productInstallStat.setInvalidNum(productInstallStat.getInvalidNum() + 1);
                                        productInstallStat.setUnAndReNum(productInstallStat.getUnAndReNum() + 1);
                                    }
                                }
                                LOGGER.info("target productInstallStat={}", JSON.toJSONString(productInstallStat));
                                num = productInstallStatAdapter.update(productInstallStat);
                            }
                            if (num == 1) {
                                LOGGER.info("productInstallStat update success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                                break;
                            }
                            if (count == 10) {
                                LOGGER.info("productInstallStat update failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
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
    public boolean statProductArrive(DataLog record) {
        LOGGER.info("statProductArrive Stat ---------开始");
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
                            ProductInstallStat productInstallStat = productInstallStatAdapter.getByMd5Key(md5Key);
                            LOGGER.info("source productInstallStat={}", JSON.toJSONString(productInstallStat));
                            if (productInstallStat == null) {
                                LOGGER.info("productInstallStat not found,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                                break;
                            }
                            productInstallStat.setTotalNum(productInstallStat.getTotalNum() + 1);
                            if (record.getActive() == CounterActive.Valid.value) {
                                productInstallStat.setValidNum(productInstallStat.getValidNum() + 1);
                            } else if (record.getActive() == CounterActive.Invalid_Replace.value) {
                                productInstallStat.setInvalidNum(productInstallStat.getInvalidNum() + 1);
                                productInstallStat.setReplaceNum(productInstallStat.getReplaceNum() + 1);
                            } else if (record.getActive() == CounterActive.Invalid_Uninstall.value) {
                                productInstallStat.setInvalidNum(productInstallStat.getInvalidNum() + 1);
                                productInstallStat.setUninstallNum(productInstallStat.getUninstallNum() + 1);
                            } else if (record.getActive() == CounterActive.Invalid_Re_And_Un.value) {
                                productInstallStat.setInvalidNum(productInstallStat.getInvalidNum() + 1);
                                productInstallStat.setUnAndReNum(productInstallStat.getUnAndReNum() + 1);
                            }
                            LOGGER.info("target productInstallStat={}", JSON.toJSONString(productInstallStat));
                            int num = productInstallStatAdapter.update(productInstallStat);
                            if (num == 1) {
                                LOGGER.info("productInstallStat update success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                                break;
                            }
                            if (count == 10) {
                                LOGGER.info("productInstallStat update failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                                break;
                            }
                        }
                    }
                }
            }
        }
        LOGGER.info("statProductArrive Stat ---------结束");

        return false;
    }

}
