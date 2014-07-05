package com.ifhz.core.service.stat.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ifhz.core.adapter.DataLogAdapter;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.stat.DataLogQueryService;
import com.ifhz.core.service.stat.bean.DataLogRequest;
import com.ifhz.core.service.stat.bean.ImeiRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.*;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 23:00
 */
@Service("dataLogQueryService")
public class DataLogQueryServiceImpl implements DataLogQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLogQueryServiceImpl.class);

    private static final ExecutorService THREADPOOL = Executors.newFixedThreadPool(256);
    @Resource(name = "dataLogAdapter")
    private DataLogAdapter dataLogAdapter;
    @Resource(name = "splitTableService")
    private SplitTableService splitTableService;

    @Override
    public long queryDeviceUpdDayNum(DataLogRequest dataLogRequest) {
        long deviceUpdDayNum = 0L;
        String tableName = splitTableService.getCurrentTableName(dataLogRequest.getDate());
        if (StringUtils.isNotBlank(tableName)) {
            dataLogRequest.setTableName(tableName);
            deviceUpdDayNum = dataLogAdapter.queryTotalCountForDevice(dataLogRequest);
        }

        return deviceUpdDayNum;
    }

    @Override
    public long queryCounterUpdDayNum(DataLogRequest dataLogRequest) {
        long counterUpdDayNum = 0L;
        List<String> tableNameList = splitTableService.getTableNameList(dataLogRequest.getDate());
        List<QueryCounterUpdNumTask> taskList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(tableNameList)) {
            for (String tableName : tableNameList) {
                DataLogRequest temp = new DataLogRequest();
                temp.setDate(dataLogRequest.getDate());
                temp.setStartTime(dataLogRequest.getStartTime());
                temp.setEndTime(dataLogRequest.getEndTime());
                temp.setMd5Key(dataLogRequest.getMd5Key());
                temp.setTableName(tableName);
                QueryCounterUpdNumTask task = new QueryCounterUpdNumTask(temp);
                taskList.add(task);
            }

            List<Future<Long>> futureResult = Lists.newArrayList();
            try {
                futureResult = THREADPOOL.invokeAll(taskList);
                if (CollectionUtils.isNotEmpty(futureResult)) {
                    for (Future<Long> future : futureResult) {
                        Long result = null;
                        try {
                            result = future.get(10, TimeUnit.SECONDS);
                        } catch (Exception e) {
                            LOGGER.error("task future result error", e);
                        }
                        if (result != null) {
                            counterUpdDayNum = counterUpdDayNum + result;
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Execute QueryCounterUpdNumTask error", e);
            }
        }

        return counterUpdDayNum;
    }

    @Override
    public long queryProductUpdDayNum(DataLogRequest dataLogRequest) {
        long productUpdDayNum = 0L;
        dataLogRequest.setProductSwitch(true);
        String tableName = splitTableService.getCurrentTableName(dataLogRequest.getDate());
        if (StringUtils.isNotBlank(tableName)) {
            dataLogRequest.setTableName(tableName);
            productUpdDayNum = dataLogAdapter.queryTotalCountForDevice(dataLogRequest);
        }

        return productUpdDayNum;
    }

    @Override
    public long queryCounterProductDayNum(DataLogRequest dataLogRequest) {
        long counterProductDayNum = 0L;
        List<String> tableNameList = splitTableService.getTableNameList(dataLogRequest.getDate());
        List<QueryCounterUpdNumTask> taskList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(tableNameList)) {
            for (String tableName : tableNameList) {
                DataLogRequest temp = new DataLogRequest();
                temp.setDate(dataLogRequest.getDate());
                temp.setStartTime(dataLogRequest.getStartTime());
                temp.setEndTime(dataLogRequest.getEndTime());
                temp.setMd5Key(dataLogRequest.getMd5Key());
                temp.setTableName(tableName);
                temp.setProductSwitch(true);
                QueryCounterUpdNumTask task = new QueryCounterUpdNumTask(temp);
                taskList.add(task);
            }

            List<Future<Long>> futureResult = Lists.newArrayList();
            try {
                futureResult = THREADPOOL.invokeAll(taskList);
                if (CollectionUtils.isNotEmpty(futureResult)) {
                    for (Future<Long> future : futureResult) {
                        Long result = null;
                        try {
                            result = future.get(10, TimeUnit.SECONDS);
                        } catch (Exception e) {
                            LOGGER.error("task future result error", e);
                        }
                        if (result != null) {
                            counterProductDayNum = counterProductDayNum + result;
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Execute QueryCounterUpdNumTask error", e);
            }
        }

        return counterProductDayNum;
    }

    @Override
    public List<String> queryImeiList(ImeiRequest request) {


        return null;
    }


    private class QueryCounterUpdNumTask implements Callable<Long> {
        private final DataLogRequest dataLogRequest;

        private QueryCounterUpdNumTask(DataLogRequest dataLogRequest) {
            this.dataLogRequest = dataLogRequest;
        }

        @Override
        public Long call() throws Exception {
            try {
                if (StringUtils.isNotBlank(dataLogRequest.getTableName())) {
                    return dataLogAdapter.queryTotalCountForCounter(dataLogRequest);
                }
            } catch (Exception e) {
                LOGGER.error("QueryCounterUpdNumTask error,{}", JSON.toJSONString(dataLogAdapter));
                LOGGER.error("QueryCounterUpdNumTask error", e);
            }

            return null;
        }
    }
}
