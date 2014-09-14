package com.ifhz.core.service.imei.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ifhz.core.adapter.DataLogAdapter;
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
    private DataLogAdapter dataLogAdapter;

    @Override
    @Log
    public List<String> getLogInstall(List<Map<String, Object>> paramList) {
        List<String> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramList)) {
            List<LogImeiTask> taskList = Lists.newArrayList();
            for (Map<String, Object> param : paramList) {
                taskList.add(new LogImeiTask(param));
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
            List<LogImeiTask> taskList = Lists.newArrayList();
            for (Map<String, Object> param : paramList) {
                taskList.add(new LogImeiTask(param));
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
            List<LogImeiTask> taskList = Lists.newArrayList();
            for (Map<String, Object> param : paramList) {
                taskList.add(new LogImeiTask(param));
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
    public List<String> getLogArriveTemp(List<Map<String, Object>> paramList, Long totalCount) {
        List<String> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramList)) {
            for (Map<String, Object> param : paramList) {
                List<String> imeiList = dataLogAdapter.getLogImeiList(param);
                if (CollectionUtils.isNotEmpty(imeiList)) {
                    result.addAll(imeiList);
                }
            }
        }

        return result == null ? Lists.<String>newArrayList() : result;
    }

    @Override
    @Log
    public List<String> getProductInstall(List<Map<String, Object>> paramList) {
        List<String> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramList)) {
            List<ProductImeiTask> taskList = Lists.newArrayList();
            for (Map<String, Object> param : paramList) {
                taskList.add(new ProductImeiTask(param));
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
            List<ProductImeiTask> taskList = Lists.newArrayList();
            for (Map<String, Object> param : paramList) {
                taskList.add(new ProductImeiTask(param));
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
            List<ProductImeiTask> taskList = Lists.newArrayList();
            for (Map<String, Object> param : paramList) {
                taskList.add(new ProductImeiTask(param));
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
    public List<String> getProductArriveTemp(List<Map<String, Object>> paramList, Long totalCount) {
        List<String> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(paramList)) {
            for (Map<String, Object> param : paramList) {
                List<String> imeiList = dataLogAdapter.getProductImeiList(param);
                if (CollectionUtils.isNotEmpty(imeiList)) {
                    result.addAll(imeiList);
                }
            }
        }

        return result == null ? Lists.<String>newArrayList() : result;
    }


    private class LogImeiTask implements Callable<List<String>> {
        private final Map<String, Object> params;

        private LogImeiTask(Map<String, Object> params) {
            this.params = params;
        }

        @Override
        public List<String> call() throws Exception {
            LOGGER.info("params={}", JSON.toJSONString(params));
            List<String> result = null;
            if (MapUtils.isNotEmpty(params)) {
                result = dataLogAdapter.getLogImeiList(params);
            }
            return result == null ? Lists.<String>newArrayList() : result;
        }
    }


    private class ProductImeiTask implements Callable<List<String>> {
        private final Map<String, Object> params;

        private ProductImeiTask(Map<String, Object> params) {
            this.params = params;
        }

        @Override
        public List<String> call() throws Exception {
            LOGGER.info("params={}", JSON.toJSONString(params));
            List<String> result = null;
            if (MapUtils.isNotEmpty(params)) {
                result = dataLogAdapter.getProductImeiList(params);
            }
            return result == null ? Lists.<String>newArrayList() : result;
        }
    }
}
