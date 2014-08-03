package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.DataLogAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.stat.DataLogQueryService;
import com.ifhz.core.service.stat.bean.DataLogRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 23:00
 */
@Service("dataLogQueryService")
public class DataLogQueryServiceImpl implements DataLogQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLogQueryServiceImpl.class);

    @Resource(name = "dataLogAdapter")
    private DataLogAdapter dataLogAdapter;
    @Resource(name = "splitTableService")
    private SplitTableService splitTableService;

    @Override
    @Log
    public long queryDeviceUpdDayNum(DataLogRequest dataLogRequest) {
        long deviceUpdDayNum = 0L;
        String tableName = splitTableService.getCurrentTableName(dataLogRequest.getDate());
        if (StringUtils.isNotBlank(tableName)) {
            dataLogRequest.setTableName(tableName);
            deviceUpdDayNum = dataLogAdapter.queryDeviceUpdDayNum(dataLogRequest);
        }

        return deviceUpdDayNum;
    }

    @Override
    @Log
    public long queryProductUpdDayNum(DataLogRequest dataLogRequest) {
        long productUpdDayNum = 0L;
        String tableName = splitTableService.getCurrentTableName(dataLogRequest.getDate());
        if (StringUtils.isNotBlank(tableName)) {
            dataLogRequest.setTableName(tableName);
            productUpdDayNum = dataLogAdapter.queryProductUpdDayNum(dataLogRequest);
        }

        return productUpdDayNum;
    }
}
