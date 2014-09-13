package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.stat.ProductArriveStatAdapter;
import com.ifhz.core.adapter.stat.ProductArriveStatTempAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.constants.GroupEnums;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.po.ProductInfo;
import com.ifhz.core.po.stat.ProductArriveStat;
import com.ifhz.core.po.stat.ProductArriveStatTemp;
import com.ifhz.core.po.stat.StatDeduction;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import com.ifhz.core.service.cache.ModelInfoCacheService;
import com.ifhz.core.service.cache.ProductInfoCacheService;
import com.ifhz.core.service.stat.ProductArriveStatTempService;
import com.ifhz.core.service.stat.StatDeductionService;
import com.ifhz.core.service.stat.handle.DeductionHandler;
import com.ifhz.core.service.stat.handle.StatConvertHandler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    @Resource
    private ProductArriveStatTempAdapter productArriveStatTempAdapter;
    @Resource
    private ProductArriveStatAdapter productArriveStatAdapter;
    @Resource
    private ModelInfoCacheService modelInfoCacheService;
    @Resource
    private ProductInfoCacheService productInfoCacheService;
    @Resource(name = "channelInfoCacheService")
    private ChannelInfoCacheService channelInfoCacheService;
    @Resource
    private StatDeductionService statDeductionService;


    @Override
    @Log
    public int insert(ProductArriveStatTemp productArriveStatTemp) {
        return productArriveStatTempAdapter.insert(productArriveStatTemp);
    }

    @Override
    @Log
    public boolean syncProductArriveStat(Date startTime, Date endTime) {
        long totalCount = productArriveStatAdapter.queryTotalCount(startTime, endTime);
        LOGGER.info("数据总量为{}", totalCount);
        if (totalCount > 0) {
            long pageNum = StatConvertHandler.getPageNum(totalCount, GlobalConstants.PAGE_SIZE);
            LOGGER.info("数据总量为{},分页值为{}", totalCount, pageNum);
            for (int i = 0; i < pageNum; i++) {
                Pagination page = new Pagination();
                page.setPageSize(GlobalConstants.PAGE_SIZE);
                page.setCurrentPage(i + 1);
                List<ProductArriveStat> productArriveStatList = productArriveStatAdapter.queryStatList(page, startTime, endTime);
                if (CollectionUtils.isNotEmpty(productArriveStatList)) {
                    for (ProductArriveStat productArriveStat : productArriveStatList) {
                        try {
                            if (GroupEnums.DB.value == productArriveStat.getGroupId() || GroupEnums.QT.value == productArriveStat.getGroupId()) {
                                StatDeduction statDeduction = statDeductionService.getByChannelId(productArriveStat.getChannelId());
                                if (statDeduction != null) {
                                    ProductArriveStatTemp productArriveStatTemp = DeductionHandler.initProductArriveStatTemp(productArriveStat, statDeduction);
                                    insert(productArriveStatTemp);
                                }
                            } else {
                                continue;
                            }
                        } catch (Exception e) {
                            LOGGER.error("insert ProductArriveStatTemp error", e);
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    @Log
    public List<ProductArriveStatTemp> queryByVo(Pagination pagination, ProductArriveStatTemp record) {
        List<ProductArriveStatTemp> result = productArriveStatTempAdapter.queryByVo(pagination, record);
        if (CollectionUtils.isNotEmpty(result)) {
            for (ProductArriveStatTemp productArriveStatTemp : result) {
                String ua = productArriveStatTemp.getUa();
                if (StringUtils.isNotEmpty(ua)) {
                    ModelInfo modelInfo = null;
                    try {
                        modelInfo = modelInfoCacheService.getByUaAndGrouId(ua, productArriveStatTemp.getGroupId());
                    } catch (Exception e) {
                        LOGGER.error("getByUaAndGrouId error", e);
                    }
                    if (modelInfo != null) {
                        productArriveStatTemp.setModelName(modelInfo.getModelName() + "(" + ua + ")");
                    } else {
                        productArriveStatTemp.setModelName("未知(" + ua + ")");
                    }
                } else {
                    productArriveStatTemp.setModelName("未知()");
                }

                Long channelId = productArriveStatTemp.getChannelId();
                if (channelId != null) {
                    ChannelInfo channelInfo = null;
                    try {
                        channelInfo = channelInfoCacheService.getByChannelId(channelId);
                    } catch (Exception e) {
                        LOGGER.error("getByChannelId error", e);
                    }
                    if (channelInfo != null) {
                        productArriveStatTemp.setChannelName(channelInfo.getChannelName());
                    } else {
                        productArriveStatTemp.setModelName("未知");
                    }
                }

                if (productArriveStatTemp.getProductId() != null) {
                    ProductInfo productInfo = productInfoCacheService.getById(productArriveStatTemp.getProductId());
                    if (productInfo != null) {
                        productArriveStatTemp.setProductName(productInfo.getProductName());
                    }
                }
            }
        }

        return result;
    }

    @Override
    @Log
    public List<ProductArriveStatTemp> querySumByVo(Pagination pagination, ProductArriveStatTemp record) {
        List<ProductArriveStatTemp> result = productArriveStatTempAdapter.querySumByVo(pagination, record);
        if (CollectionUtils.isNotEmpty(result)) {
            for (ProductArriveStatTemp productArriveStatTemp : result) {
                String ua = productArriveStatTemp.getUa();
                if (StringUtils.isNotEmpty(ua)) {
                    ModelInfo modelInfo = null;
                    try {
                        modelInfo = modelInfoCacheService.getByUaAndGrouId(ua, productArriveStatTemp.getGroupId());
                    } catch (Exception e) {
                        LOGGER.error("getByUaAndGrouId error", e);
                    }
                    if (modelInfo != null) {
                        productArriveStatTemp.setModelName(modelInfo.getModelName() + "(" + ua + ")");
                    } else {
                        productArriveStatTemp.setModelName("未知(" + ua + ")");
                    }
                } else {
                    productArriveStatTemp.setModelName("未知()");
                }

                Long channelId = productArriveStatTemp.getChannelId();
                if (channelId != null) {
                    ChannelInfo channelInfo = null;
                    try {
                        channelInfo = channelInfoCacheService.getByChannelId(channelId);
                    } catch (Exception e) {
                        LOGGER.error("getByChannelId error", e);
                    }
                    if (channelInfo != null) {
                        productArriveStatTemp.setChannelName(channelInfo.getChannelName());
                    } else {
                        productArriveStatTemp.setModelName("未知");
                    }
                }

                if (productArriveStatTemp.getProductId() != null) {
                    ProductInfo productInfo = productInfoCacheService.getById(productArriveStatTemp.getProductId());
                    if (productInfo != null) {
                        productArriveStatTemp.setProductName(productInfo.getProductName());
                    }
                }
            }
        }

        return result;
    }

    @Override
    @Log
    public ProductArriveStatTemp queryCountByVo(ProductArriveStatTemp record) {
        return productArriveStatTempAdapter.queryCountByVo(record);
    }
}
