package com.ifhz.core.service.schedule.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.adapter.CounterTempLogAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.constants.TempLogType;
import com.ifhz.core.po.CounterTempLog;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.api.DataLogApiService;
import com.ifhz.core.service.schedule.CounterTempLogService;
import com.ifhz.core.service.stat.LogInstallStatService;
import com.ifhz.core.service.stat.ProductInstallStatService;
import com.ifhz.core.service.stat.handle.StatConvertHandler;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/22
 * Time: 15:43
 */
@Service("counterTempLogService")
public class CounterTempLogServiceImpl implements CounterTempLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CounterTempLogServiceImpl.class);

    @Resource(name = "counterTempLogAdapter")
    private CounterTempLogAdapter counterTempLogAdapter;
    @Resource(name = "dataLogApiService")
    private DataLogApiService dataLogApiService;
    @Resource(name = "productInstallStatService")
    private ProductInstallStatService productInstallStatService;
    @Resource(name = "logInstallStatService")
    private LogInstallStatService logInstallStatService;

    @Override
    @Log
    public void scanCounterTempLogFoUnDo(Date startTime, Date endTime) {
        LOGGER.info("scanCounterTempLogFoUnDo -------start,[{},{}]", startTime, endTime);
        long totalCount = counterTempLogAdapter.queryTotalCount(startTime, endTime, TempLogType.UnDo.value);
        LOGGER.info("数据总量为{}", totalCount);
        if (totalCount > 0) {
            long pageNum = StatConvertHandler.getPageNum(totalCount, GlobalConstants.PAGE_SIZE);
            LOGGER.info("数据总量为{},分页值为{}", totalCount, pageNum);
            for (int i = 0; i < pageNum; i++) {
                Pagination page = new Pagination();
                page.setPageSize(GlobalConstants.PAGE_SIZE);
                page.setCurrentPage(i + 1);
                List<CounterTempLog> tempLogList = counterTempLogAdapter.queryPage(page, startTime, endTime, TempLogType.UnDo.value);
                if (CollectionUtils.isNotEmpty(tempLogList)) {
                    for (CounterTempLog tempLog : tempLogList) {
                        try {
                            processCounterTempLog(tempLog, true);
                        } catch (Exception e) {
                            LOGGER.error("scanCounterTempLogFoUnDo error", e);
                        }
                    }
                }
            }
        }
        LOGGER.info("scanCounterTempLogFoUnDo -------end,[{},{}]", startTime, endTime);
    }

    @Override
    @Log
    public void scanCounterTempLogFoUnStat(Date startTime, Date endTime) {
        LOGGER.info("scanCounterTempLogFoUnStat -------start,[{},{}]", startTime, endTime);
        long totalCount = counterTempLogAdapter.queryTotalCount(startTime, endTime, TempLogType.UnDo.value);
        LOGGER.info("数据总量为{}", totalCount);
        if (totalCount > 0) {
            long pageNum = StatConvertHandler.getPageNum(totalCount, GlobalConstants.PAGE_SIZE);
            LOGGER.info("数据总量为{},分页值为{}", totalCount, pageNum);
            for (int i = 0; i < pageNum; i++) {
                Pagination page = new Pagination();
                page.setPageSize(GlobalConstants.PAGE_SIZE);
                page.setCurrentPage(i + 1);
                List<CounterTempLog> tempLogList = counterTempLogAdapter.queryPage(page, startTime, endTime, TempLogType.UnDo.value);
                if (CollectionUtils.isNotEmpty(tempLogList)) {
                    for (CounterTempLog tempLog : tempLogList) {
                        try {
                            processCounterTempLog(tempLog, false);
                        } catch (Exception e) {
                            LOGGER.error("scanCounterTempLogFoUnStat error", e);
                        }
                    }
                }
            }
        }
        LOGGER.info("scanCounterTempLogFoUnStat -------end,[{},{}]", startTime, endTime);
    }

    @Override
    @Log
    public void batchDelete(Date startTime, Date endTime) {
        counterTempLogAdapter.batchDelete(startTime, endTime);
    }

    @Log
    private void processCounterTempLog(CounterTempLog tempLog, boolean isUpdateDataLog) {
        LOGGER.info("processCounterTempLog -------start,isUpdateDataLog={},tempLog={}", isUpdateDataLog, JSON.toJSONString(tempLog));
        if (tempLog != null) {
            DataLog dataLog = dataLogApiService.getByImei(tempLog.getImei());
            if (dataLog != null) {
                if (isUpdateDataLog) {
                    if (dataLog.getCounterUploadTime() == null) {
                        dataLog.setCounterUploadTime(new Date());
                        dataLog.setActive(tempLog.getActive());
                    }
                    //更新流水表数据
                    int num = dataLogApiService.updateCounterData(dataLog);
                    if (num == 1) {
                        //统计到达数据
                        logInstallStatService.statLogInstallForArrive(dataLog);
                        productInstallStatService.statProductInstallForArrive(dataLog);
                    }
                } else {
                    //统计到达数据
                    logInstallStatService.statLogInstallForArrive(dataLog);
                    productInstallStatService.statProductInstallForArrive(dataLog);
                }
            }
        }
        LOGGER.info("processCounterTempLog -------end,isUpdateDataLog={},tempLog={}", isUpdateDataLog, JSON.toJSONString(tempLog));
    }
}
