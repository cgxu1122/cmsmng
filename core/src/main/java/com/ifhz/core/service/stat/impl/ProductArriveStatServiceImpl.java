package com.ifhz.core.service.stat.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.adapter.BatchProductRefAdapter;
import com.ifhz.core.adapter.stat.ProductArriveStatAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GroupEnums;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.po.ProductInfo;
import com.ifhz.core.po.stat.ProductArriveStat;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import com.ifhz.core.service.cache.ModelInfoCacheService;
import com.ifhz.core.service.cache.ProductInfoCacheService;
import com.ifhz.core.service.stat.ProductArriveStatService;
import com.ifhz.core.service.stat.constants.CounterActive;
import com.ifhz.core.service.stat.handle.ArriveStatConvertHandler;
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
 * Time: 19:52
 */
@Service
public class ProductArriveStatServiceImpl implements ProductArriveStatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductArriveStatServiceImpl.class);

    @Resource
    private BatchProductRefAdapter batchProductRefAdapter;
    @Resource
    private ProductArriveStatAdapter productArriveStatAdapter;
    @Resource
    private ModelInfoCacheService modelInfoCacheService;
    @Resource
    private ProductInfoCacheService productInfoCacheService;
    @Resource(name = "channelInfoCacheService")
    private ChannelInfoCacheService channelInfoCacheService;


    @Override
    @Log
    public List<ProductArriveStat> queryByVo(Pagination pagination, ProductArriveStat record) {
        List<ProductArriveStat> result = productArriveStatAdapter.queryByVo(pagination, record);
        if (CollectionUtils.isNotEmpty(result)) {
            for (ProductArriveStat productArriveStat : result) {
                String ua = productArriveStat.getUa();
                if (StringUtils.isNotEmpty(ua)) {
                    ModelInfo modelInfo = null;
                    try {
                        modelInfo = modelInfoCacheService.getByUaAndGrouId(ua, productArriveStat.getGroupId());
                    } catch (Exception e) {
                        LOGGER.error("getByUaAndGrouId error", e);
                    }
                    if (modelInfo != null) {
                        productArriveStat.setModelName(modelInfo.getModelName() + "(" + ua + ")");
                    } else {
                        productArriveStat.setModelName("未知(" + ua + ")");
                    }
                } else {
                    productArriveStat.setModelName("未知()");
                }
                if (productArriveStat.getGroupId() != null) {
                    productArriveStat.setGroupName(GroupEnums.fromByValue(productArriveStat.getGroupId()).name);
                }

                Long channelId = productArriveStat.getChannelId();
                if (channelId != null) {
                    ChannelInfo channelInfo = null;
                    try {
                        channelInfo = channelInfoCacheService.getByChannelId(channelId);
                    } catch (Exception e) {
                        LOGGER.error("getByChannelId error", e);
                    }
                    if (channelInfo != null) {
                        productArriveStat.setChannelName(channelInfo.getChannelName());
                    } else {
                        productArriveStat.setModelName("未知");
                    }
                }

                if (productArriveStat.getProductId() != null) {
                    ProductInfo productInfo = productInfoCacheService.getById(productArriveStat.getProductId());
                    if (productInfo != null) {
                        productArriveStat.setProductName(productInfo.getProductName());
                    }
                }
            }
        }

        return result;
    }

    @Override
    public List<ProductArriveStat> querySumByVo(Pagination pagination, ProductArriveStat record) {
        List<ProductArriveStat> result = productArriveStatAdapter.querySumByVo(pagination, record);
        if (CollectionUtils.isNotEmpty(result)) {
            for (ProductArriveStat productArriveStat : result) {
                String ua = productArriveStat.getUa();
                productArriveStat.setModelName(ua);
                if (productArriveStat.getProductId() != null) {
                    ProductInfo productInfo = productInfoCacheService.getById(productArriveStat.getProductId());
                    if (productInfo != null) {
                        productArriveStat.setProductName(productInfo.getProductName());
                    }
                }
            }
        }

        return result;
    }

    @Override
    public ProductArriveStat queryCountByVo(ProductArriveStat record) {
        return productArriveStatAdapter.queryCountByVo(record);
    }


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
