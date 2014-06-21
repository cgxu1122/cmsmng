package com.ifhz.core.service.stat.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.DataLogAdapter;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.stat.DataLogQueryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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


    public long queryDeviceUpdDayNum(Date date, Date startTime, Date endTime) {
        long deviceUpdDayNum = 0L;
        String tableName = splitTableService.getCurrentTableName(date);
        if (StringUtils.isNotBlank(tableName)) {
            deviceUpdDayNum = dataLogAdapter.queryTotalCountForDevice(tableName, startTime, endTime);
        }

        return deviceUpdDayNum;
    }


    public long queryCounterUpdDayNum(Date date, Date startTime, Date endTime) {
        long counterUpdDayNum = 0L;
        List<String> tableNameList = splitTableService.getTableNameList(date);
        List<QueryCounterUpdNumTask> taskList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(tableNameList)) {
            for (String tableName : tableNameList) {
                QueryCounterUpdNumTask task = new QueryCounterUpdNumTask(tableName, startTime, endTime);
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


    private class QueryCounterUpdNumTask implements Callable<Long> {
        private String tableName;
        private Date startTime;
        private Date endTime;

        private QueryCounterUpdNumTask(String tableName, Date startTime, Date endTime) {
            this.tableName = tableName;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        @Override
        public Long call() throws Exception {
            try {
                if (StringUtils.isNotBlank(tableName)) {
                    return dataLogAdapter.queryTotalCountForCounter(tableName, startTime, endTime, null);
                }
            } catch (Exception e) {
                LOGGER.error("QueryHasImeiTask[{},{},{}] error", tableName, startTime, endTime);
            }

            return null;
        }
    }
}
