package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.stat.LogArriveStatTempAdapter;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.stat.LogArriveStatTempService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public class LogArriveStatTempServiceImpl implements LogArriveStatTempService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogArriveStatTempServiceImpl.class);

    private TaskExecutor taskExecutor;
    private LogArriveStatTempAdapter logArriveStatTempAdapter;

    @Override
    public boolean statTempLogArrive(DataLog record) {
        //TODO
        return false;
    }
}
