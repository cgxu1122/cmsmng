package com.ifhz.core.service.schedule.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.adapter.CounterTempLogAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.CounterTempLog;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.api.DataLogApiService;
import com.ifhz.core.service.schedule.CounterTempLogService;
import com.ifhz.core.service.stat.StatCounterService;
import com.ifhz.core.service.stat.handle.StatConvertHandler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
    @Resource(name = "statCounterService")
    private StatCounterService statCounterService;

    @Override
    public void scanCounterTempLog(Date startTime, Date endTime) {
        LOGGER.info("scanCounterTempLog -------start,[{},{}]", startTime, endTime);
        long totalCount = counterTempLogAdapter.queryTotalCount(startTime, endTime);
        if (totalCount > 0) {
            long pageNum = StatConvertHandler.getPageNum(totalCount, GlobalConstants.PAGE_SIZE);
            for (int i = 0; i < pageNum; i++) {
                Pagination page = new Pagination();
                page.setPageSize(GlobalConstants.PAGE_SIZE);
                page.setCurrentPage(i + 1);
                List<CounterTempLog> tempLogList = counterTempLogAdapter.queryPage(page, startTime, endTime);
                if (CollectionUtils.isNotEmpty(tempLogList)) {
                    for (CounterTempLog tempLog : tempLogList) {
                        try {
                            processCounterTempLog(tempLog);
                        } catch (Exception e) {
                            LOGGER.error("processCounterTempLog error", e);
                        }
                    }
                }
            }
        }
        LOGGER.info("scanCounterTempLog -------end,[{},{}]", startTime, endTime);
    }


    private void processCounterTempLog(CounterTempLog tempLog) {
        LOGGER.info("scanCounterTempLog -------start,tempLog={}", JSON.toJSONString(tempLog));
        if (tempLog != null) {
            DataLog dataLog = dataLogApiService.getByImei(tempLog.getImei());
            if (dataLog != null) {
                if (dataLog.getActive() == null && dataLog.getCounterUploadTime() == null) {
                    dataLog.setCounterUploadTime(tempLog.getCreateTime());
                    dataLog.setActive(tempLog.getActive());
                    if (StringUtils.isBlank(dataLog.getUa()) && StringUtils.isNotBlank(tempLog.getUa())) {
                        dataLog.setUa(tempLog.getUa());
                    }
                    String pmd5Key = StatConvertHandler.getMd5KeyByLogDataForProductStat(dataLog);
                    dataLog.setPmd5Key(pmd5Key);
                    //更新流水表数据
                    dataLogApiService.updateCounterData(dataLog);
                    //统计到达数据
                    statCounterService.updateStat(dataLog);
                }
            }
        }
        LOGGER.info("scanCounterTempLog -------end,tempLog={}", JSON.toJSONString(tempLog));
    }
}
