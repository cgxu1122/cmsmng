package com.ifhz.core.service.imei.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ifhz.core.adapter.DataLogAdapter;
import com.ifhz.core.service.imei.DeviceImeiQueryService;
import com.ifhz.core.service.imei.bean.StatImeiRequest;
import com.ifhz.core.service.stat.handle.DateHandler;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 17:23
 */
@Service("deviceImeiQueryService")
public class DeviceImeiQueryServiceImpl implements DeviceImeiQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceImeiQueryServiceImpl.class);

    private static final ExecutorService executor = Executors.newFixedThreadPool(256);

    @Resource(name = "dataLogAdapter")
    private DataLogAdapter dataLogAdapter;

    public List<String> getLogImeiList(List<String> tableNameList, StatImeiRequest request) {
        List<String> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(tableNameList)) {
            List<LogImeiTask> taskList = Lists.newArrayList();
            for (String tableName : tableNameList) {
                taskList.add(new LogImeiTask(getLogParams(tableName, request)));
            }
            List<Future<List<String>>> futureResult = Lists.newArrayList();
            try {
                futureResult = executor.invokeAll(taskList);
                if (CollectionUtils.isNotEmpty(futureResult)) {
                    for (Future<List<String>> future : futureResult) {
                        List<String> ret = null;
                        try {
                            ret = future.get(10, TimeUnit.SECONDS);
                        } catch (Exception e) {
                            LOGGER.error("DeviceProcessTask future result error", e);
                        }
                        if (CollectionUtils.isNotEmpty(ret)) {
                            result.addAll(ret);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Execute DeviceProcessTask error", e);
            }
        }

        return result == null ? Lists.<String>newArrayList() : result;
    }

    public List<String> getProductImeiList(List<String> tableNameList, StatImeiRequest request) {
        List<String> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(tableNameList)) {
            List<ProductImeiTask> taskList = Lists.newArrayList();
            for (String tableName : tableNameList) {
                taskList.add(new ProductImeiTask(getProductParams(tableName, request)));
            }
            List<Future<List<String>>> futureResult = Lists.newArrayList();
            try {
                futureResult = executor.invokeAll(taskList);
                if (CollectionUtils.isNotEmpty(futureResult)) {
                    for (Future<List<String>> future : futureResult) {
                        List<String> ret = null;
                        try {
                            ret = future.get(10, TimeUnit.SECONDS);
                        } catch (Exception e) {
                            LOGGER.error("DeviceProcessTask future result error", e);
                        }
                        if (CollectionUtils.isNotEmpty(ret)) {
                            result.addAll(ret);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Execute DeviceProcessTask error", e);
            }
        }

        return result == null ? Lists.<String>newArrayList() : result;
    }

    private Map<String, Object> getLogParams(String tableName, StatImeiRequest request) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("ua", request.getUa());
        params.put("startTime", DateHandler.getStartTime(request.getProcessDate()));
        params.put("endTime", DateHandler.getEndTime(request.getProcessDate()));
        params.put("deviceCode", request.getDeviceCode());
        params.put("channelId", request.getChannelId());
        params.put("type", request.getType().value);
        if (request.getActive() != null) {
            params.put("activeType", request.getActive().value);
        }
        params.put("tableName", tableName);

        return params;
    }


    private Map<String, Object> getProductParams(String tableName, StatImeiRequest request) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("ua", request.getUa());
        params.put("startTime", DateHandler.getStartTime(request.getProcessDate()));
        params.put("endTime", DateHandler.getEndTime(request.getProcessDate()));
        params.put("productId", request.getProductId());
        params.put("groupId", request.getGroupId());
        params.put("type", request.getType().value);
        if (request.getActive() != null) {
            params.put("activeType", request.getActive().value);
        }
        params.put("tableName", tableName);

        return params;
    }


    private class LogImeiTask implements Callable<List<String>> {

        private final Map<String, Object> params;

        private LogImeiTask(Map<String, Object> params) {
            this.params = params;
        }

        @Override
        public List<String> call() throws Exception {
            return dataLogAdapter.getLogImeiList(params);
        }
    }


    private class ProductImeiTask implements Callable<List<String>> {

        private final Map<String, Object> params;

        private ProductImeiTask(Map<String, Object> params) {
            this.params = params;
        }

        @Override
        public List<String> call() throws Exception {
            return dataLogAdapter.getProductImeiList(params);
        }
    }

}
