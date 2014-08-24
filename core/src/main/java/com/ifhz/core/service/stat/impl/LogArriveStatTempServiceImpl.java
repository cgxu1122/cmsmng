package com.ifhz.core.service.stat.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.adapter.stat.LogArriveStatTempAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.stat.LogArriveStat;
import com.ifhz.core.po.stat.LogArriveStatTemp;
import com.ifhz.core.service.stat.LogArriveStatTempService;
import com.ifhz.core.service.stat.handle.ArriveStatConvertHandler;
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

    @Log
    @Override
    public boolean asyncData(LogArriveStat record, boolean isInsert) {
        if (record != null) {
            SyncDataTask task = new SyncDataTask(record, isInsert);
            taskExecutor.execute(task);
        }
        return true;
    }

    private class SyncDataTask implements Runnable {

        private final LogArriveStat logArriveStat;
        private final boolean isInsert;

        private SyncDataTask(LogArriveStat logArriveStat, boolean isInsert) {
            this.logArriveStat = logArriveStat;
            this.isInsert = isInsert;
        }

        @Override
        public void run() {
            LOGGER.info("isInsert={},LogArriveStat = {}", isInsert, JSON.toJSONString(logArriveStat));
            LogArriveStatTemp logArriveStatTemp = ArriveStatConvertHandler.convertLogArriveStatTemp(logArriveStat);
            int count = 0;
            while (true) {
                count++;
                int num = 0;
                if (isInsert) {
                    try {
                        num = logArriveStatTempAdapter.insert(logArriveStatTemp);
                    } catch (Exception e) {
                        LOGGER.error("LogArriveStat insert error", e);
                        continue;
                    }
                    if (num == 1) {
                        LOGGER.info("LogArriveStat insert success, LogArriveStat={}", JSON.toJSONString(logArriveStatTemp));
                        break;
                    }
                } else {
                    LogArriveStatTemp temp = logArriveStatTempAdapter.getById(logArriveStatTemp.getId());
                    LOGGER.info("数据库中数据为：LogArriveStatTemp={}", JSON.toJSONString(temp));
                    if (temp != null && temp.getVersion() < logArriveStatTemp.getVersion()) {
                        num = logArriveStatTempAdapter.update(logArriveStatTemp);
                        if (num == 1) {
                            LOGGER.info("LogArriveStat update success, LogArriveStat={}", JSON.toJSONString(logArriveStatTemp));
                            break;
                        }
                    } else {
                        LOGGER.info("LogArriveStat not found or version lessthan DB data version, LogArriveStat={}", JSON.toJSONString(logArriveStatTemp));
                        break;
                    }
                }
                if (count == 10) {
                    LOGGER.info("LogArriveStat save failure, LogArriveStat={}", JSON.toJSONString(logArriveStatTemp));
                    break;
                }
            }
        }
    }
}
