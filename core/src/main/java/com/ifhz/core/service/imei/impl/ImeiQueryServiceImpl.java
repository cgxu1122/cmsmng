package com.ifhz.core.service.imei.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.ifhz.core.adapter.ImeiQueryAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import com.ifhz.core.service.cache.ModelInfoCacheService;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.imei.ImeiQueryService;
import com.ifhz.core.service.imei.bean.DataLogResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/16
 * Time: 22:30
 */
@Service("imeiQueryService")
public class ImeiQueryServiceImpl implements ImeiQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImeiQueryServiceImpl.class);

    @Resource(name = "imeiQueryAdapter")
    private ImeiQueryAdapter imeiQueryAdapter;
    @Resource(name = "splitTableService")
    private SplitTableService splitTableService;
    @Resource(name = "modelInfoCacheService")
    private ModelInfoCacheService modelInfoCacheService;
    @Resource(name = "channelInfoCacheService")
    private ChannelInfoCacheService channelInfoCacheService;

    private static final ExecutorService THREADPOOL = Executors.newFixedThreadPool(128);

    @Log
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<DataLogResult> queryListByImeiList(List<String> imeiList) {
        Date now = new Date();
        List<String> tableNameList = splitTableService.getTableNameList(now);
        imeiQueryAdapter.insertBatch(imeiList);
        LOGGER.info("queryImeiList = {}", JSON.toJSONString(imeiQueryAdapter.queryImeiList()));
        List<DataLogResult> result = getDataLogResultList(tableNameList);
        LOGGER.info("DataLogResult={}", result);
        if (CollectionUtils.isNotEmpty(result)) {
            for (DataLogResult dataLogResult : result) {
                if (StringUtils.isNotBlank(dataLogResult.getUa()) && dataLogResult.getGroupId() != null) {
                    ModelInfo modelInfo = null;
                    try {
                        modelInfo = modelInfoCacheService.getByUaAndGrouId(dataLogResult.getUa(), dataLogResult.getGroupId());
                    } catch (Exception e) {
                        LOGGER.error("getByUaAndGrouId error", e);
                    }
                    if (modelInfo != null && StringUtils.isNotBlank(modelInfo.getModelName())) {
                        dataLogResult.setModelName(modelInfo.getModelName());
                    } else {
                        dataLogResult.setModelName("未知");
                    }
                } else {
                    dataLogResult.setModelName("未知");
                }
                Long channelId = dataLogResult.getChannelId();
                if (channelId != null) {
                    ChannelInfo channelInfo = null;
                    try {
                        channelInfo = channelInfoCacheService.getByChannelId(channelId);
                    } catch (Exception e) {
                        LOGGER.error("getByChannelId error", e);
                    }
                    if (channelInfo != null) {
                        dataLogResult.setChannelName(channelInfo.getChannelName());
                    } else {
                        dataLogResult.setModelName("未知");
                    }
                } else {
                    dataLogResult.setModelName("未知");
                }
            }
            //重新排序
            result = Ordering.from(new DataLogResultByProcessTime()).sortedCopy(result);
        }
        LOGGER.info("DataLogResult={}", result);
        return result == null ? Lists.<DataLogResult>newArrayList() : result;
    }

    @Log
    private List<DataLogResult> getDataLogResultList(List<String> tableNameList) {
        List<DataLogResult> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(tableNameList)) {
            LOGGER.info("tableNameList = {}", JSON.toJSONString(tableNameList));
            for (String tableName : tableNameList) {
                List<DataLogResult> dataLogResultList = imeiQueryAdapter.queryListByImeiList(tableName);
                if (CollectionUtils.isNotEmpty(dataLogResultList)) {
                    LOGGER.info("dataLogResultList = {}", JSON.toJSONString(dataLogResultList));
                    result.addAll(dataLogResultList);
                }
            }
        }

        return result;
    }

    @Log
    private List<DataLogResult> asyncDataLogResultList(Date date) {
        List<DataLogResult> result = Lists.newArrayList();
        List<ImeiQueryTask> taskList = genTaskList(date);
        if (CollectionUtils.isNotEmpty(taskList)) {
            List<Future<List<DataLogResult>>> futureResult = Lists.newArrayList();
            try {
                futureResult = THREADPOOL.invokeAll(taskList);
                if (CollectionUtils.isNotEmpty(futureResult)) {
                    for (Future<List<DataLogResult>> future : futureResult) {
                        try {
                            List<DataLogResult> dataLogResultList = future.get(10, TimeUnit.SECONDS);
                            LOGGER.info("{}", JSON.toJSONString(dataLogResultList));
                            if (CollectionUtils.isNotEmpty(dataLogResultList)) {
                                result.addAll(dataLogResultList);
                            }
                        } catch (Exception e) {
                            LOGGER.error("ImeiQueryTask future result error", e);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Execute ImeiQueryTask error", e);
            }
        }

        return result;
    }

    @Log
    private List<ImeiQueryTask> genTaskList(Date date) {
        List<String> tableNameList = splitTableService.getTableNameList(date);
        List<ImeiQueryTask> taskList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(tableNameList)) {
            for (String tableName : tableNameList) {
                if (StringUtils.isNotBlank(tableName)) {
                    ImeiQueryTask task = new ImeiQueryTask(tableName);
                    taskList.add(task);
                }
            }
        }
        LOGGER.info("task.size = {}", taskList.size());
        return taskList;
    }


    private class ImeiQueryTask implements Callable<List<DataLogResult>> {

        private String tableName;

        private ImeiQueryTask(String tableName) {
            this.tableName = tableName;
        }

        @Override
        @Log
        public List<DataLogResult> call() throws Exception {
            List<DataLogResult> result = imeiQueryAdapter.queryListByImeiList(tableName);
            return result == null ? Lists.<DataLogResult>newArrayList() : result;
        }
    }


    /**
     * 排序
     */
    private static class DataLogResultByProcessTime implements Comparator<DataLogResult> {
        @Override
        @Log
        public int compare(DataLogResult o1, DataLogResult o2) {
            if (o1 == null)
                return -1;
            if (o2 == null)
                return 1;

            return o1.getProcessTime().compareTo(o2.getProcessTime());
        }
    }

}
