package com.ifhz.core.service.api.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.CounterUploadLogAdapter;
import com.ifhz.core.po.CounterUploadLog;
import com.ifhz.core.service.api.CounterUploadLogService;
import com.ifhz.core.service.common.SplitTableService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 16:47
 */
@Service("counterUploadLogService")
public class CounterUploadLogServiceImpl implements CounterUploadLogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CounterUploadLogServiceImpl.class);

    private static final Executor executor = Executors.newFixedThreadPool(256);

    @Resource(name = "counterUploadLogAdapter")
    private CounterUploadLogAdapter counterUploadLogAdapter;
    @Resource(name = "splitTableService")
    private SplitTableService splitTableService;


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void insert(CounterUploadLog po) {
        Date now = new Date();
        String tableName = splitTableService.getTableNameForCounterByNow(now);
        if (StringUtils.isNotBlank(tableName)) {
            counterUploadLogAdapter.insert(tableName, po);
        }
    }

    @Override
    public CounterUploadLog queryHasImei(String imei) {
        if (StringUtils.isBlank(imei)) {
            return null;
        }
        Date now = new Date();
        CounterUploadLog result = null;
        List<Future<CounterUploadLog>> futures = Lists.newArrayList();
        CompletionService<CounterUploadLog> ecs = new ExecutorCompletionService<CounterUploadLog>(executor);
        try {
            int count = 0;
            List<String> tableList = splitTableService.getTableListForCounterByNow(now);
            for (String tableName : tableList) {
                QueryHasImeiTask task = new QueryHasImeiTask(imei, tableName);
                Future<CounterUploadLog> future = ecs.submit(task);
                futures.add(future);
                count++;
            }
            for (int i = 0; i < count; i++) {
                try {
                    Future<CounterUploadLog> future = ecs.poll(10, TimeUnit.SECONDS);
                    if (future != null) {
                        CounterUploadLog ret = future.get();
                        if (ret != null) {
                            result = ret;
                            break;
                        }
                    }
                } catch (ExecutionException ignore) {
                    LOGGER.warn("queryHasImei Future error", ignore);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("queryHasImei error", e);
        }
        return result;
    }


    private class QueryHasImeiTask implements Callable<CounterUploadLog> {
        private String imei;
        private String tableName;

        private QueryHasImeiTask(String imei, String tableName) {
            this.imei = imei;
            this.tableName = tableName;
        }

        @Override
        public CounterUploadLog call() throws Exception {
            try {
                if (StringUtils.isNotBlank(tableName) && StringUtils.isNotBlank(imei)) {
                    return counterUploadLogAdapter.getByImei(tableName, imei);
                }
            } catch (Exception e) {
                LOGGER.warn("QueryHasImeiTask[{},{}] error", tableName, imei);
            }

            return null;
        }
    }
}
