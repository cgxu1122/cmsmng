package com.ifhz.core.service.stat.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.adapter.stat.LogArriveStatAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.stat.LogArriveStat;
import com.ifhz.core.service.stat.LogArriveStatService;
import com.ifhz.core.service.stat.LogArriveStatTempService;
import com.ifhz.core.service.stat.constants.CounterActive;
import com.ifhz.core.service.stat.handle.ArriveStatConvertHandler;
import com.ifhz.core.service.stat.handle.StatConvertHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
public class LogArriveStatServiceImpl implements LogArriveStatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogArriveStatServiceImpl.class);


    private LogArriveStatAdapter logArriveStatAdapter;
    private LogArriveStatTempService logArriveStatTempService;

    @Log
    @Override
    public boolean statLogArrive(DataLog record) {
        LOGGER.info("statLogArrive Stat ---------开始");
        if (record != null) {
            //TODO md5Key 新规则
            String md5Key = StatConvertHandler.getMd5KeyForLogStat(record);
            LOGGER.info("LogArriveStat md5Key={}", md5Key);
            int count = 0;
            while (true) {
                boolean isInsert = false;
                count++;
                int num = 0;
                LogArriveStat logArriveStat = logArriveStatAdapter.getByMd5Key(md5Key);
                LOGGER.info("source logArriveStat={}", JSON.toJSONString(logArriveStat));
                if (logArriveStat == null) {
                    logArriveStat = ArriveStatConvertHandler.initLogArriveStat(record);
                    logArriveStat.setMd5Key(md5Key);
                    LOGGER.info("LogArriveStat not found,  md5Key={}, logArriveStat={}", md5Key, JSON.toJSONString(logArriveStat));
                    logArriveStat.setTotalNum(logArriveStat.getTotalNum() + 1);
                    if (record.getActive() == CounterActive.Valid.value) {
                        logArriveStat.setValidNum(logArriveStat.getValidNum() + 1);
                    } else if (record.getActive() == CounterActive.Invalid_Replace.value) {
                        logArriveStat.setInvalidNum(logArriveStat.getInvalidNum() + 1);
                        logArriveStat.setReplaceNum(logArriveStat.getReplaceNum() + 1);
                    } else if (record.getActive() == CounterActive.Invalid_Uninstall.value) {
                        logArriveStat.setInvalidNum(logArriveStat.getInvalidNum() + 1);
                        logArriveStat.setUninstallNum(logArriveStat.getUninstallNum() + 1);
                    } else if (record.getActive() == CounterActive.Invalid_Re_And_Un.value) {
                        logArriveStat.setInvalidNum(logArriveStat.getInvalidNum() + 1);
                        logArriveStat.setUnAndReNum(logArriveStat.getUnAndReNum() + 1);
                    }
                    isInsert = true;
                    try {
                        LOGGER.info("target logArriveStat={}", JSON.toJSONString(logArriveStat));
                        num = logArriveStatAdapter.insert(logArriveStat);
                    } catch (Exception e) {
                        LOGGER.error("LogArriveStat insert error", e);
                        continue;
                    }
                } else {
                    logArriveStat.setTotalNum(logArriveStat.getTotalNum() + 1);
                    if (record.getActive() == CounterActive.Valid.value) {
                        logArriveStat.setValidNum(logArriveStat.getValidNum() + 1);
                    } else if (record.getActive() == CounterActive.Invalid_Replace.value) {
                        logArriveStat.setInvalidNum(logArriveStat.getInvalidNum() + 1);
                        logArriveStat.setReplaceNum(logArriveStat.getReplaceNum() + 1);
                    } else if (record.getActive() == CounterActive.Invalid_Uninstall.value) {
                        logArriveStat.setInvalidNum(logArriveStat.getInvalidNum() + 1);
                        logArriveStat.setUninstallNum(logArriveStat.getUninstallNum() + 1);
                    } else if (record.getActive() == CounterActive.Invalid_Re_And_Un.value) {
                        logArriveStat.setInvalidNum(logArriveStat.getInvalidNum() + 1);
                        logArriveStat.setUnAndReNum(logArriveStat.getUnAndReNum() + 1);
                    }
                    LOGGER.info("target logArriveStat={}", JSON.toJSONString(logArriveStat));
                    num = logArriveStatAdapter.update(logArriveStat);
                }

                if (num == 1) {
                    LOGGER.info("LogArriveStat update success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                    logArriveStatTempService.asyncData(logArriveStat, isInsert);
                    break;
                } else {
                    LOGGER.info("LogArriveStat update failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                }
                if (count == 10) {
                    LOGGER.info("LogArriveStat update failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                    break;
                }
            }
        }
        LOGGER.info("statLogArrive Stat ---------结束");

        return true;
    }
}
