package com.ifhz.core.service.imei.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ifhz.core.adapter.DataLogImeiAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.service.imei.InstallImeiQueryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
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
@Service
public class InstallImeiQueryServiceImpl implements InstallImeiQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstallImeiQueryServiceImpl.class);

    private static final ExecutorService executor = Executors.newFixedThreadPool(256);

    @Resource
    private DataLogImeiAdapter dataLogImeiAdapter;

    @Override
    @Log
    public List<String> getLogInstall(List<Map<String, Object>> paramList) {
        List<String> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramList)) {
            List<LogInstallTask> taskList = Lists.newArrayList();
            for (Map<String, Object> param : paramList) {
                taskList.add(new LogInstallTask(param));
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

    @Override
    @Log
    public List<String> getLogInstallArrive(List<Map<String, Object>> paramList) {
        List<String> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramList)) {
            List<LogInstallArriveTask> taskList = Lists.newArrayList();
            for (Map<String, Object> param : paramList) {
                taskList.add(new LogInstallArriveTask(param));
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

    @Override
    @Log
    public List<String> getLogArrive(List<Map<String, Object>> paramList) {
        List<String> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramList)) {
            for (Map<String, Object> param : paramList) {
                List<String> imeiList = dataLogImeiAdapter.getLogArriveTemp(param);
                if (CollectionUtils.isNotEmpty(imeiList)) {
                    result.addAll(imeiList);
                }
            }
        }

        return result == null ? Lists.<String>newArrayList() : result;
    }

    @Override
    @Log
    public List<String> getLogArriveTemp(List<Map<String, Object>> paramList, Long totalCount) {
        List<String> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramList)) {
            for (Map<String, Object> param : paramList) {
                List<String> imeiList = dataLogImeiAdapter.getLogArriveTemp(param);
                if (CollectionUtils.isNotEmpty(imeiList)) {
                    result.addAll(imeiList);
                }
            }
        }
        //截断
        if (CollectionUtils.isNotEmpty(result)) {
            int size = result.size();
            if (totalCount > 0 && size > totalCount.intValue()) {
                result = result.subList(0, totalCount.intValue());
            }
        }

        return result == null ? Lists.<String>newArrayList() : result;
    }

    @Override
    @Log
    public List<String> getProductInstall(List<Map<String, Object>> paramList) {
        List<String> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramList)) {
            List<ProductInstallTask> taskList = Lists.newArrayList();
            for (Map<String, Object> param : paramList) {
                taskList.add(new ProductInstallTask(param));
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

    @Override
    @Log
    public List<String> getProductInstallArrive(List<Map<String, Object>> paramList) {
        List<String> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramList)) {
            List<ProductInstallArriveTask> taskList = Lists.newArrayList();
            for (Map<String, Object> param : paramList) {
                taskList.add(new ProductInstallArriveTask(param));
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

    @Override
    @Log
    public List<String> getProductArrive(List<Map<String, Object>> paramList) {
        List<String> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramList)) {
            for (Map<String, Object> param : paramList) {
                List<String> imeiList = dataLogImeiAdapter.getProductArrive(param);
                if (CollectionUtils.isNotEmpty(imeiList)) {
                    result.addAll(imeiList);
                }
            }
        }

        return result == null ? Lists.<String>newArrayList() : result;
    }

    @Override
    @Log
    public List<String> getProductArriveTemp(List<Map<String, Object>> paramList, Long totalCount) {
        List<String> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramList)) {
            for (Map<String, Object> param : paramList) {
                List<String> imeiList = dataLogImeiAdapter.getProductArriveTemp(param);
                if (CollectionUtils.isNotEmpty(imeiList)) {
                    result.addAll(imeiList);
                }
            }
        }
        //截断
        if (CollectionUtils.isNotEmpty(result)) {
            int size = result.size();
            if (totalCount > 0 && size > totalCount.intValue()) {
                result = result.subList(0, totalCount.intValue());
            }
        }

        return result == null ? Lists.<String>newArrayList() : result;
    }


    private class LogInstallTask implements Callable<List<String>> {
        private final Map<String, Object> params;

        private LogInstallTask(Map<String, Object> params) {
            this.params = params;
        }

        @Override
        public List<String> call() throws Exception {
            LOGGER.info("params={}", JSON.toJSONString(params));
            List<String> result = null;
            if (MapUtils.isNotEmpty(params)) {
                result = dataLogImeiAdapter.getLogInstall(params);
            }
            return result == null ? Lists.<String>newArrayList() : result;
        }
    }

    private class LogInstallArriveTask implements Callable<List<String>> {
        private final Map<String, Object> params;

        private LogInstallArriveTask(Map<String, Object> params) {
            this.params = params;
        }

        @Override
        public List<String> call() throws Exception {
            LOGGER.info("params={}", JSON.toJSONString(params));
            List<String> result = null;
            if (MapUtils.isNotEmpty(params)) {
                result = dataLogImeiAdapter.getLogInstallArrive(params);
            }
            return result == null ? Lists.<String>newArrayList() : result;
        }
    }


    private class ProductInstallTask implements Callable<List<String>> {
        private final Map<String, Object> params;

        private ProductInstallTask(Map<String, Object> params) {
            this.params = params;
        }

        @Override
        public List<String> call() throws Exception {
            LOGGER.info("params={}", JSON.toJSONString(params));
            List<String> result = null;
            if (MapUtils.isNotEmpty(params)) {
                result = dataLogImeiAdapter.getProductInstall(params);
            }
            return result == null ? Lists.<String>newArrayList() : result;
        }
    }

    private class ProductInstallArriveTask implements Callable<List<String>> {
        private final Map<String, Object> params;

        private ProductInstallArriveTask(Map<String, Object> params) {
            this.params = params;
        }

        @Override
        public List<String> call() throws Exception {
            LOGGER.info("params={}", JSON.toJSONString(params));
            List<String> result = null;
            if (MapUtils.isNotEmpty(params)) {
                result = dataLogImeiAdapter.getLogInstallArrive(params);
            }
            return result == null ? Lists.<String>newArrayList() : result;
        }
    }
}
