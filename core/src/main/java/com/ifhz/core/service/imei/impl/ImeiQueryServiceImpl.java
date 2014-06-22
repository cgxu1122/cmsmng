package com.ifhz.core.service.imei.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.ifhz.core.adapter.ImeiQueryAdapter;
import com.ifhz.core.adapter.ModelInfoAdapter;
import com.ifhz.core.po.ModelInfo;
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
    @Resource(name = "modelInfoAdapter")
    private ModelInfoAdapter modelInfoAdapter;

    private static final ExecutorService THREADPOOL = Executors.newFixedThreadPool(128);

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<DataLogResult> queryListByImeiList(List<String> imeiList) {
        Date now = new Date();
        imeiQueryAdapter.insertBatch(imeiList);
        List<DataLogResult> dataLogResultList = queryDeviceResultList(now);
        if (CollectionUtils.isNotEmpty(dataLogResultList)) {
            for (DataLogResult dataLogResult : dataLogResultList) {
                if (StringUtils.isNotBlank(dataLogResult.getUa()) && dataLogResult.getGroupId() != null) {
                    ModelInfo modelInfo = modelInfoAdapter.getByGroupIdAndUa(dataLogResult.getGroupId(), dataLogResult.getUa());
                    if (modelInfo != null && StringUtils.isNotBlank(modelInfo.getModelName())) {
                        dataLogResult.setModelName(modelInfo.getModelName());
                    } else {
                        dataLogResult.setModelName("未知");
                    }
                } else {
                    dataLogResult.setModelName("未知");
                }
            }
            //重新排序
            dataLogResultList = Ordering.from(new DataLogResultByProcessTime()).sortedCopy(dataLogResultList);
        }

        return dataLogResultList == null ? Lists.<DataLogResult>newArrayList() : dataLogResultList;
    }


    private List<DataLogResult> queryDeviceResultList(Date date) {
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
        return taskList;
    }


    private class ImeiQueryTask implements Callable<List<DataLogResult>> {

        private String tableName;

        private ImeiQueryTask(String tableName) {
            this.tableName = tableName;
        }

        @Override
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
        public int compare(DataLogResult o1, DataLogResult o2) {
            if (o1 == null)
                return -1;
            if (o2 == null)
                return 1;

            return o1.getProcessTime().compareTo(o2.getProcessTime());
        }
    }

}
