package com.ifhz.core.service.stat.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.adapter.LogStatAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.enums.GroupType;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import com.ifhz.core.service.stat.LogInstallStatService;
import com.ifhz.core.service.stat.constants.CounterActive;
import com.ifhz.core.service.stat.handle.StatConvertHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 23:53
 */
@Service("logInstallStatService")
public class LogInstallStatServiceImpl implements LogInstallStatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogInstallStatServiceImpl.class);

    @Resource(name = "logStatAdapter")
    private LogStatAdapter logStatAdapter;
    @Resource(name = "channelInfoCacheService")
    private ChannelInfoCacheService channelInfoCacheService;

    @Override
    @Log
    public boolean statLogInstall(DataLog record) {
        LOGGER.info("statLogInstall Stat ---------开始");
        if (record != null) {
            String md5Key = StatConvertHandler.getMd5KeyForLogStat(record);
            LOGGER.info("LogStat md5Key={}", md5Key);
            if (StringUtils.isNotBlank(md5Key)) {
                int count = 0;
                while (true) {
                    count++;
                    int num = 0;
                    LogStat logStat = logStatAdapter.getByMd5Key(md5Key);
                    LOGGER.info("source logStat={}", JSON.toJSONString(logStat));
                    if (logStat == null) {
                        logStat = StatConvertHandler.initDataStat(record);
                        logStat.setMd5Key(md5Key);
                        logStat.setDevicePrsDayNum(logStat.getDevicePrsDayNum() + 1);
                        if (logStat.getGroupId() == GroupType.DB.VALUE || logStat.getGroupId() == GroupType.LW.VALUE) {
                            ChannelInfo channelInfo = null;
                            try {
                                channelInfo = channelInfoCacheService.getByChannelId(logStat.getChannelId());
                            } catch (Exception e) {
                                LOGGER.error("getByChannelId error", e);
                            }
                            if (channelInfo != null && channelInfo.getLaowuId() != null) {
                                logStat.setLaowuId(channelInfo.getLaowuId());
                            }
                        }

                        try {
                            LOGGER.info("target logStat={}", JSON.toJSONString(logStat));
                            num = logStatAdapter.insert(logStat);
                        } catch (Exception e) {
                            LOGGER.error("LogStat insert error", e);
                            continue;
                        }
                    } else {
                        logStat.setDevicePrsDayNum(logStat.getDevicePrsDayNum() + 1);
                        LOGGER.info("target logStat={}", JSON.toJSONString(logStat));
                        num = logStatAdapter.update(logStat);
                    }
                    if (num == 1) {
                        LOGGER.info("LogStat save success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                        break;
                    }
                    if (count == 10) {
                        LOGGER.info("LogStat save failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                        break;
                    }
                }
            }
        }
        LOGGER.info("statLogInstall Stat ---------结束");
        return true;
    }

    @Log
    @Override
    public boolean statLogInstallForArrive(DataLog record) {
        LOGGER.info("statLogInstallForArrive Stat ---------开始");
        boolean result = true;
        if (record != null) {
            String md5Key = StatConvertHandler.getMd5KeyForLogStat(record);
            LOGGER.info("LogStat md5Key={}", md5Key);
            if (StringUtils.isNotBlank(md5Key)) {
                int count = 0;
                while (true) {
                    count++;
                    LogStat logStat = logStatAdapter.getByMd5Key(md5Key);
                    LOGGER.info("source logStat={}", JSON.toJSONString(logStat));
                    if (logStat == null) {
                        LOGGER.info("LogStat update failure LogStat not found,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                        break;
                    }
                    logStat.setPrsActiveTotalNum(logStat.getPrsActiveTotalNum() + 1);
                    if (record.getActive() == CounterActive.Valid.value) {
                        logStat.setPrsActiveValidNum(logStat.getPrsActiveValidNum() + 1);
                    } else if (record.getActive() == CounterActive.Invalid_Replace.value) {
                        logStat.setPrsActiveInvalidNum(logStat.getPrsActiveInvalidNum() + 1);
                        logStat.setPrsInvalidReplaceNum(logStat.getPrsInvalidReplaceNum() + 1);
                    } else if (record.getActive() == CounterActive.Invalid_Uninstall.value) {
                        logStat.setPrsActiveInvalidNum(logStat.getPrsActiveInvalidNum() + 1);
                        logStat.setPrsInvalidUninstallNum(logStat.getPrsInvalidUninstallNum() + 1);
                    } else if (record.getActive() == CounterActive.Invalid_Re_And_Un.value) {
                        logStat.setPrsActiveInvalidNum(logStat.getPrsActiveInvalidNum() + 1);
                        logStat.setPrsInvalidUnAndReNum(logStat.getPrsInvalidUnAndReNum() + 1);
                    }
                    LOGGER.info("target logStat={}", JSON.toJSONString(logStat));
                    int num = logStatAdapter.update(logStat);
                    if (num == 1) {
                        LOGGER.info("LogStat update success,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                        break;
                    } else {
                        LOGGER.info("LogStat update failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                    }
                    if (count == 10) {
                        LOGGER.info("LogStat update failure,  md5Key={}, dataLog={}", md5Key, JSON.toJSONString(record));
                        break;
                    }
                }
            }
        }
        LOGGER.info("statLogInstallForArrive Stat ---------结束");
        return false;
    }
}
