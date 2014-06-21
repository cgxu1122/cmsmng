package com.ifhz.core.service.imei.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.ImeiQueryAdapter;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.imei.ImeiQueryService;
import com.ifhz.core.service.imei.bean.DataLogResult;
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


    private List<DataLogResult> queryDeviceResultList(Date date) {
        List<DataLogResult> result = Lists.newArrayList();
        List<String> deviceTableList = splitTableService.getTableNameList(date);

        return result;
    }

    private List<DeviceQueryTask> genDeviceTaskList(List<String> deviceTableList) {
        List<DeviceQueryTask> taskList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(deviceTableList)) {

        }
        //TODO
        return null;
    }


    private class DeviceQueryTask implements Callable<List<DataLogResult>> {

        private String tableName;

        private DeviceQueryTask(String tableName) {
            this.tableName = tableName;
        }

        @Override
        public List<DataLogResult> call() throws Exception {
            return imeiQueryAdapter.queryListForDeviceResult(tableName);
        }
    }

}
