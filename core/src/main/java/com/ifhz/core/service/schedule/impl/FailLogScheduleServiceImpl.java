package com.ifhz.core.service.schedule.impl;

import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.po.CounterFailLog;
import com.ifhz.core.po.CounterUploadLog;
import com.ifhz.core.po.DeviceProcessLog;
import com.ifhz.core.service.api.CounterFailLogService;
import com.ifhz.core.service.api.DeviceProcessLogService;
import com.ifhz.core.service.schedule.FailLogScheduleService;
import com.ifhz.core.utils.DateTimeHandle;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 16:18
 */
@Service("failLogScheduleService")
public class FailLogScheduleServiceImpl implements FailLogScheduleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FailLogScheduleServiceImpl.class);
    private static final long PAGESIZE = 10000;

    private CounterFailLogService counterFailLogService;
    private DeviceProcessLogService deviceProcessLogService;

    @Override
    public void action() {
        Date now = new Date();
        Date yesterday = DateFormatUtils.addDay(now, -1);
        Date agoDay = DateFormatUtils.addMonth(yesterday, -3);
        Date startTime = DateTimeHandle.getBeginTime(agoDay);
        Date endTime = DateTimeHandle.getEndTime(yesterday);
        long totalCount = counterFailLogService.queryTotalCount(startTime, endTime);
        LOGGER.info("totalCount = {}", totalCount);
        long pageCount;
        if (totalCount % PAGESIZE == 0) {
            pageCount = totalCount / PAGESIZE;
        } else {
            pageCount = totalCount / PAGESIZE + 1;
        }
        for (int i = 0; i < pageCount; i++) {
            //todo 分页查询
            List<CounterFailLog> pageList = null;
            for (CounterFailLog log : pageList) {
                try {
                    process(log);
                } catch (Exception e) {
                    LOGGER.error("process CounterFailLog error", e);
                }
            }
        }
    }

    private void process(CounterFailLog log) {
        if (StringUtils.isBlank(log.getImei())) {
            LOGGER.error("CounterFailLog unable to process, cause : imei is null,{}", log);
            return;
        }
        DeviceProcessLog deviceProcessLog = deviceProcessLogService.queryHasImei(log.getImei());
        if (deviceProcessLog != null) {
            CounterUploadLog po = new CounterUploadLog();
            po.setImei(deviceProcessLog.getImei());
            po.setUa(log.getUa());
            //todo 处理单条数据

            //todo 保存 计数器流水表
        }
    }

}
