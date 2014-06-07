package com.ifhz.core.service.api.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.CounterUploadLogAdapter;
import com.ifhz.core.base.page.Pagination;
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
import java.util.*;
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

    @Override
    public List<Map<String, Object>> queryArriveImeiByPage(Pagination page, Map record) {
        return null;
    }


    /*
        装机到达数量查询为动态数据，统计计数器加工数量，需根据到达状态动态显示
         */
    public List<Map<String, Object>> queryArriveImei(Map record) {
        List<Map<String, Object>> result = new ArrayList();
        Date now = new Date();
        List<Future<List<Map<String, Object>>>> futures = Lists.newArrayList();
        CompletionService<List<Map<String, Object>>> ecs = new ExecutorCompletionService<List<Map<String, Object>>>(executor);
        try {
            int count = 0;
            // List<String> tableList = splitTableService.getTableListForCounterByNow(now);
            List<String> tableList = new ArrayList();
            tableList.add("TY_COUNTER_UPLOAD_LOG_20142");
            // tableList.add("TY_COUNTER_UPLOAD_LOG_20143");

            for (String tableName : tableList) {
                record.put("tableName", tableName);
                QueryArriveImeiTask task = new QueryArriveImeiTask(record);
                Future<List<Map<String, Object>>> future = ecs.submit(task);
                futures.add(future);
                count++;
            }
            for (int i = 0; i < count; i++) {
                try {
                    Future<List<Map<String, Object>>> future = ecs.poll(60, TimeUnit.SECONDS);
                    if (future != null) {
                        List<Map<String, Object>> log = future.get();
                        if (log.size() > 0) {
                            result.addAll(log);
                            log.clear();
                        }
                    }
                } catch (ExecutionException ignore) {
                    LOGGER.warn("queryArriveImei Future error", ignore);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("queryArriveImei error", e);
        }
        return result;
    }

    /*
    装机到达数量查询任务，与累计到达数量有两点不同，一是查询时间使用加工时间，二是需要考虑到达状态
     */
    private class QueryArriveImeiTask implements Callable<List<Map<String, Object>>> {
        private Map pars;

        private QueryArriveImeiTask(Map<String, Object> m) {
            //复制一个查询条件，莫用同一个对象
            this.pars = new HashMap();
            pars.put("groupId", m.get("groupId"));
            pars.put("modleName", m.get("modleName"));
            pars.put("processTime", m.get("processTime"));
            pars.put("channelId", m.get("channelId"));
            pars.put("active", m.get("active"));
            pars.put("tableName", m.get("tableName"));

        }

        @Override
        public List<Map<String, Object>> call() throws Exception {
            try {
                if (StringUtils.isNotBlank((String) pars.get("tableName"))) {
                    return counterUploadLogAdapter.queryArriveImei(pars);
                }
            } catch (Exception e) {
                LOGGER.warn("QueryArriveImeiTask[{},{}] error", pars.get("tableName"), e);
            }

            return new ArrayList();
        }
    }

    public List<Map<String, Object>> queryUploadImeiByPage(Pagination page, Map record) {
        return null;
    }

    /*
           累计到达数量查询，显示某天计数器的上传数据，数据为静态数据，此天一过数据将不会变化
            */
    public List<Map<String, Object>> queryUploadImei(Map record) {
        List<Map<String, Object>> result = new ArrayList();
        Date now = new Date();
        List<Future<List<Map<String, Object>>>> futures = Lists.newArrayList();
        CompletionService<List<Map<String, Object>>> ecs = new ExecutorCompletionService<List<Map<String, Object>>>(executor);
        try {
            int count = 0;
            // List<String> tableList = splitTableService.getTableListForCounterByNow(now);
            List<String> tableList = new ArrayList();
            tableList.add("TY_COUNTER_UPLOAD_LOG_20142");
            // tableList.add("TY_COUNTER_UPLOAD_LOG_20143");

            for (String tableName : tableList) {
                record.put("tableName", tableName);
                QueryUploadImeiTask task = new QueryUploadImeiTask(record);
                Future<List<Map<String, Object>>> future = ecs.submit(task);
                futures.add(future);
                count++;
            }
            for (int i = 0; i < count; i++) {
                try {
                    Future<List<Map<String, Object>>> future = ecs.poll(60, TimeUnit.SECONDS);
                    if (future != null) {
                        List<Map<String, Object>> log = future.get();
                        if (log.size() > 0) {
                            result.addAll(log);
                            log.clear();
                        }
                    }
                } catch (ExecutionException ignore) {
                    LOGGER.warn("queryUploadImei Future error", ignore);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("queryArriveImei error", e);
        }
        return result;
    }

    /*
    累计到达数量查询任务，与装机到达数量有两点不同，一是查询时间使用上传时间，二是不考虑到达状态
     */
    private class QueryUploadImeiTask implements Callable<List<Map<String, Object>>> {
        private Map pars;

        private QueryUploadImeiTask(Map<String, Object> m) {
            //复制一个查询条件，莫用同一个对象
            this.pars = new HashMap();
            pars.put("groupId", m.get("groupId"));
            pars.put("modleName", m.get("modleName"));
            //这里与装机到达数量查询不一样，累计到达数量查询使用上传时间createTime
            pars.put("createTime", m.get("createTime"));
            pars.put("channelId", m.get("channelId"));
            //注意这里，累计到达数量为静态数据，是到达状态无关，不需添加此参数
            //pars.put("active",m.get("active"));
            pars.put("tableName", m.get("tableName"));

        }

        @Override
        public List<Map<String, Object>> call() throws Exception {
            try {
                if (StringUtils.isNotBlank((String) pars.get("tableName"))) {
                    return counterUploadLogAdapter.queryUploadImei(pars);
                }
            } catch (Exception e) {
                LOGGER.warn("QueryArriveImeiTask[{},{}] error", pars.get("tableName"), e);
            }

            return new ArrayList();
        }
    }
}
