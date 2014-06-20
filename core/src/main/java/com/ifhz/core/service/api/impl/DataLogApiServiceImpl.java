package com.ifhz.core.service.api.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.DataLogAdapter;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.api.DataLogApiService;
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
 * Date: 2014/6/20
 * Time: 20:55
 */
@Service("dataLogApiService")
public class DataLogApiServiceImpl implements DataLogApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceProcessLogServiceImpl.class);

    private static final Executor executor = Executors.newFixedThreadPool(256);

    @Resource(name = "dataLogAdapter")
    private DataLogAdapter dataLogAdapter;
    @Resource(name = "splitTableService")
    private SplitTableService splitTableService;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insertDeviceData(DataLog record) {
        Date now = new Date();
        String tableName = splitTableService.getTableNameForCounterByNow(now);
        if (StringUtils.isNotBlank(tableName)) {
            record.setTableName(tableName);
            return dataLogAdapter.insertDeviceData(record);
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateCounterData(DataLog record) {
        Date now = new Date();
        String tableName = splitTableService.getTableNameForCounterByNow(now);
        if (StringUtils.isNotBlank(tableName)) {
            record.setTableName(tableName);
            return dataLogAdapter.updateCounterData(record);
        }

        return 0;
    }

    @Override
    public DataLog getByImei(String imei) {
        if (StringUtils.isBlank(imei)) {
            return null;
        }
        Date now = new Date();
        DataLog result = null;
        List<Future<DataLog>> futures = Lists.newArrayList();
        CompletionService<DataLog> ecs = new ExecutorCompletionService<DataLog>(executor);
        try {
            int count = 0;
            List<String> tableList = splitTableService.getTableListForCounterByNow(now);
            for (String tableName : tableList) {
                QueryHasImeiTask task = new QueryHasImeiTask(imei, tableName);
                Future<DataLog> future = ecs.submit(task);
                futures.add(future);
                count++;
            }
            for (int i = 0; i < count; i++) {
                try {
                    Future<DataLog> future = ecs.poll(10, TimeUnit.SECONDS);
                    if (future != null) {
                        DataLog log = future.get();
                        if (log != null && log.getId() != null) {
                            result = log;
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


    private class QueryHasImeiTask implements Callable<DataLog> {
        private String imei;
        private String tableName;

        private QueryHasImeiTask(String imei, String tableName) {
            this.imei = imei;
            this.tableName = tableName;
        }

        @Override
        public DataLog call() throws Exception {
            try {
                if (StringUtils.isNotBlank(tableName) && StringUtils.isNotBlank(imei)) {
                    return dataLogAdapter.getByImei(tableName, imei);
                }
            } catch (Exception e) {
                LOGGER.error("QueryHasImeiTask[{},{}] error", tableName, imei);
            }

            return null;
        }
    }
}
