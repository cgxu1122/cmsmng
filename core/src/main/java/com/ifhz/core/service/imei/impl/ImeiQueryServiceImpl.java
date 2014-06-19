package com.ifhz.core.service.imei.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.ImeiQueryAdapter;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.imei.ImeiQueryService;
import com.ifhz.core.service.imei.bean.CounterResult;
import com.ifhz.core.service.imei.bean.DeviceResult;
import com.ifhz.core.service.imei.bean.ImeiResult;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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


    private static final Executor executor = Executors.newFixedThreadPool(256);

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<ImeiResult> queryListByImeiList(List<String> imeiList) {
        Date now = new Date();


        imeiQueryAdapter.insertBatch(imeiList);

        return null;
    }


    private List<DeviceResult> queryDeviceResultList(Date date) {
        List<DeviceResult> result = Lists.newArrayList();
        List<String> deviceTableList = splitTableService.getTableListForDeviceByNow(date);

        return result;
    }

    private List<DeviceQueryTask> genDeviceTaskList(List<String> deviceTableList) {
        List<DeviceQueryTask> taskList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(deviceTableList)) {

        }
        //TODO
        return null;
    }

    private List<CounterQueryTask> genCounterTaskList(List<String> counterTableList) {
        return null;
    }

    private List<CounterResult> queryCounterResultList(Date date) {
        List<String> counterTableList = splitTableService.getTableListForCounterByNow(date);
        List<CounterResult> result = Lists.newArrayList();

        return result;
    }


    private class DeviceQueryTask implements Callable<List<DeviceResult>> {

        private String tableName;

        private DeviceQueryTask(String tableName) {
            this.tableName = tableName;
        }

        @Override
        public List<DeviceResult> call() throws Exception {
            return imeiQueryAdapter.queryListForDeviceResult(tableName);
        }
    }

    private class CounterQueryTask implements Callable<List<CounterResult>> {

        private String tableName;

        private CounterQueryTask(String tableName) {
            this.tableName = tableName;
        }

        @Override
        public List<CounterResult> call() throws Exception {
            return imeiQueryAdapter.queryListForCounterResult(tableName);
        }
    }
}
