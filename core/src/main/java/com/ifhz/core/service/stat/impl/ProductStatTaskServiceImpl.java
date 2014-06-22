package com.ifhz.core.service.stat.impl;

import com.google.common.collect.Maps;
import com.ifhz.core.adapter.BatchProductRefAdapter;
import com.ifhz.core.adapter.DataLogAdapter;
import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.ProductStat;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.stat.DataLogQueryService;
import com.ifhz.core.service.stat.ProductStatTaskService;
import com.ifhz.core.service.stat.ProductStatUpdateService;
import com.ifhz.core.service.stat.bean.DataLogRequest;
import com.ifhz.core.service.stat.constants.CounterActive;
import com.ifhz.core.service.stat.handle.DateHandler;
import com.ifhz.core.service.stat.handle.StatConvertHandler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 23:45
 */
@Service("productStatTaskService")
public class ProductStatTaskServiceImpl implements ProductStatTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductStatTaskServiceImpl.class);

    @Resource(name = "productStatUpdateService")
    private ProductStatUpdateService productStatUpdateService;
    @Resource(name = "splitTableService")
    private SplitTableService splitTableService;
    @Resource(name = "dataLogAdapter")
    private DataLogAdapter dataLogAdapter;
    @Resource(name = "batchProductRefAdapter")
    private BatchProductRefAdapter batchProductRefAdapter;
    @Resource(name = "dataLogQueryService")
    private DataLogQueryService dataLogQueryService;
    //统计时分页处理条目，默认10000条
    public final int pageSize = MapConfig.getInt("stat.pageSize", GlobalConstants.GLOBAL_CONFIG, 10000);


    @Override
    public void scanDataLog(Date startTime, Date endTime) {
        LOGGER.info("统计程序开始执行{},{}", startTime, endTime);
        Map<String, ProductStat> container = Maps.newHashMap();
        String tableName = splitTableService.getCurrentTableName(startTime);
        DataLogRequest dataLogRequest = new DataLogRequest();
        dataLogRequest.setDate(startTime);
        dataLogRequest.setStartTime(startTime);
        dataLogRequest.setEndTime(endTime);
        dataLogRequest.setTableName(tableName);

        long totalCount = dataLogAdapter.queryTotalCountForDevice(dataLogRequest);
        if (totalCount > 0) {
            long pageNum = StatConvertHandler.getPageNum(totalCount, pageSize);
            for (int i = 0; i < pageNum; i++) {
                Pagination page = new Pagination();
                page.setPageSize(pageSize);
                page.setCurrentPage(i + 1);
                List<DataLog> dataLogList = dataLogAdapter.queryPageForDevice(page, dataLogRequest);
                if (CollectionUtils.isNotEmpty(dataLogList)) {
                    for (DataLog dataLog : dataLogList) {
                        String batchCode = dataLog.getBatchCode();
                        if (StringUtils.isNotBlank(batchCode)) {
                            List<Long> productIdList = batchProductRefAdapter.queryProductIdList(batchCode);
                            if (CollectionUtils.isNotEmpty(productIdList)) {
                                for (Long productId : productIdList) {
                                    String md5Key = StatConvertHandler.getMd5KeyForProductStat(dataLog, productId);
                                    String pmd5Key = StatConvertHandler.getMd5KeyByLogDataForProductStat(dataLog);
                                    ProductStat productStat = getProductStatFromMap(container, md5Key, dataLog, productId);
                                    productStat.setMd5Key(md5Key);
                                    productStat.setDataLogPmd5Key(pmd5Key);
                                    productStat.setProductPrsDayNum(productStat.getProductPrsDayNum() + 1);
                                    //判断此加工数据 同时计数器数据也已经上传
                                    if (dataLog.getActive() != null && dataLog.getCounterUploadTime() != null) {
                                        productStat.setPrsActiveTotalNum(productStat.getPrsActiveTotalNum() + 1);
                                        boolean isInvalid = false;
                                        if (dataLog.getActive() == CounterActive.Valid.value) {
                                            productStat.setPrsActiveValidNum(productStat.getPrsActiveValidNum() + 1);
                                        } else if (dataLog.getActive() == CounterActive.Invalid_Replace.value) {
                                            isInvalid = true;
                                            productStat.setPrsActiveInvalidNum(productStat.getPrsActiveInvalidNum() + 1);
                                        } else if (dataLog.getActive() == CounterActive.Invalid_Uninstall.value) {
                                            isInvalid = true;
                                            productStat.setPrsInvalidUninstallNum(productStat.getPrsInvalidUninstallNum() + 1);
                                        }
                                        if (isInvalid) {
                                            productStat.setPrsActiveInvalidNum(productStat.getPrsActiveInvalidNum() + 1);
                                        }
                                    }
                                    //重新放入容器中
                                    container.put(md5Key, productStat);
                                }
                            }
                        }
                    }
                    //统计完成 保存入库
                    saveStatMap(container);
                }
            }
        }
    }

    private ProductStat getProductStatFromMap(Map<String, ProductStat> container, String md5Key, DataLog dataLog, Long productId) {
        if (container.containsKey(md5Key)) {
            return container.get(md5Key);
        } else {
            return StatConvertHandler.initProductStat(dataLog, productId);
        }
    }

    public void saveStatMap(Map<String, ProductStat> param) {
        if (MapUtils.isNotEmpty(param)) {
            for (Map.Entry<String, ProductStat> entry : param.entrySet()) {
                try {
                    //保存前先查询库里是否有
                    String md5Key = entry.getKey();
                    ProductStat value = entry.getValue();
                    ProductStat entity = productStatUpdateService.getByMd5Key(md5Key);
                    Date startTime = DateHandler.getStartTime(entity.getProcessDate());
                    Date endTime = DateHandler.getEndTime(entity.getProcessDate());

                    if (entity != null) {//数据库有数据则更新
                        entity.setProductPrsDayNum(entity.getProductPrsDayNum() + value.getProductPrsDayNum());
                        entity.setPrsActiveTotalNum(entity.getPrsActiveTotalNum() + value.getPrsActiveTotalNum());
                        entity.setPrsActiveValidNum(entity.getPrsActiveValidNum() + value.getPrsActiveValidNum());
                        entity.setPrsInvalidReplaceNum(entity.getPrsInvalidReplaceNum() + value.getPrsInvalidReplaceNum());
                        entity.setPrsInvalidUninstallNum(entity.getPrsInvalidUninstallNum() + value.getPrsInvalidUninstallNum());

                        DataLogRequest request = new DataLogRequest();
                        request.setStartTime(entity.getProcessDate());
                        request.setStartTime(startTime);
                        request.setEndTime(endTime);
                        request.setMd5Key(value.getDataLogPmd5Key());
                        request.setProduct(true);

                        long productUpdDayNum = dataLogQueryService.queryProductUpdDayNum(request);
                        long counterProductDayNum = dataLogQueryService.queryCounterProductDayNum(request);
                        entity.setProductPrsDayNum(productUpdDayNum);
                        entity.setCounterProductDayNum(counterProductDayNum);

                        productStatUpdateService.update(entity);
                    } else {//数据库无数据则插入
                        DataLogRequest request = new DataLogRequest();
                        request.setStartTime(entity.getProcessDate());
                        request.setStartTime(startTime);
                        request.setEndTime(endTime);
                        request.setMd5Key(value.getDataLogPmd5Key());
                        request.setProduct(true);

                        long productUpdDayNum = dataLogQueryService.queryProductUpdDayNum(request);
                        long counterProductDayNum = dataLogQueryService.queryCounterProductDayNum(request);
                        entity.setProductPrsDayNum(productUpdDayNum);
                        entity.setCounterProductDayNum(counterProductDayNum);

                        productStatUpdateService.insert(value);
                    }
                } catch (Exception e) {
                    LOGGER.error("ProductStat update error", e);
                }
            }
        }
    }
}