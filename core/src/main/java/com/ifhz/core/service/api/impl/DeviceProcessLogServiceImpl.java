package com.ifhz.core.service.api.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.DeviceProcessLogAdapter;
import com.ifhz.core.po.DeviceProcessLog;
import com.ifhz.core.service.api.DeviceProcessLogService;
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
 * Time: 16:48
 */
@Service("deviceProcessLogService")
public class DeviceProcessLogServiceImpl implements DeviceProcessLogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceProcessLogServiceImpl.class);

    private static final Executor executor = Executors.newFixedThreadPool(256);

    @Resource(name = "deviceProcessLogAdapter")
    private DeviceProcessLogAdapter deviceProcessLogAdapter;
    @Resource(name = "splitTableService")
    private SplitTableService splitTableService;


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void insert(DeviceProcessLog po) {
        Date now = new Date();
        String tableName = splitTableService.getTableNameForCounterByNow(now);
        if (StringUtils.isNotBlank(tableName)) {
            deviceProcessLogAdapter.insert(tableName, po);
        }
    }

    @Override
    public DeviceProcessLog queryHasImei(String imei) {
        if (StringUtils.isBlank(imei)) {
            return null;
        }
        Date now = new Date();
        DeviceProcessLog result = null;
        List<Future<DeviceProcessLog>> futures = Lists.newArrayList();
        CompletionService<DeviceProcessLog> ecs = new ExecutorCompletionService<DeviceProcessLog>(executor);
        try {
            int count = 0;
            List<String> tableList = splitTableService.getTableListForCounterByNow(now);
            for (String tableName : tableList) {
                QueryHasImeiTask task = new QueryHasImeiTask(imei, tableName);
                Future<DeviceProcessLog> future = ecs.submit(task);
                futures.add(future);
                count++;
            }
            for (int i = 0; i < count; i++) {
                try {
                    Future<DeviceProcessLog> future = ecs.poll(10, TimeUnit.SECONDS);
                    if (future != null) {
                        DeviceProcessLog log = future.get();
                        if (log != null && log.getProcessId() != null) {
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


    private class QueryHasImeiTask implements Callable<DeviceProcessLog> {
        private String imei;
        private String tableName;

        private QueryHasImeiTask(String imei, String tableName) {
            this.imei = imei;
            this.tableName = tableName;
        }

        @Override
        public DeviceProcessLog call() throws Exception {
            try {
                if (StringUtils.isNotBlank(tableName) && StringUtils.isNotBlank(imei)) {
                    return deviceProcessLogAdapter.getByImei(tableName, imei);
                }
            } catch (Exception e) {
                LOGGER.warn("QueryHasImeiTask[{},{}] error", tableName, imei);
            }

            return null;
        }
    }
}