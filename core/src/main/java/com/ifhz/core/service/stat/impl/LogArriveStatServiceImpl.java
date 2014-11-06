package com.ifhz.core.service.stat.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.adapter.stat.LogArriveStatAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GroupEnums;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.po.stat.LogArriveStat;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import com.ifhz.core.service.cache.ModelInfoCacheService;
import com.ifhz.core.service.stat.LogArriveStatService;
import com.ifhz.core.service.stat.constants.CounterActive;
import com.ifhz.core.service.stat.handle.ArriveStatConvertHandler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
@Service
public class LogArriveStatServiceImpl implements LogArriveStatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogArriveStatServiceImpl.class);
    @Resource
    private LogArriveStatAdapter logArriveStatAdapter;
    @Resource
    private ModelInfoCacheService modelInfoCacheService;
    @Resource(name = "channelInfoCacheService")
    private ChannelInfoCacheService channelInfoCacheService;

    @Override
    @Log
    public List<LogArriveStat> queryByVo(Pagination pagination, LogArriveStat record) {
        List<LogArriveStat> result = logArriveStatAdapter.queryByVo(pagination, record);
        if (CollectionUtils.isNotEmpty(result)) {
            for (LogArriveStat logArriveStat : result) {
                String ua = logArriveStat.getUa();
                if (StringUtils.isNotEmpty(ua)) {
                    ModelInfo modelInfo = null;
                    try {
                        modelInfo = modelInfoCacheService.getByUaAndGrouId(ua, logArriveStat.getGroupId());
                    } catch (Exception e) {
                        LOGGER.error("getByUaAndGrouId error", e);
                    }
                    if (modelInfo != null) {
                        logArriveStat.setModelName(modelInfo.getModelName() + "(" + ua + ")");
                    } else {
                        logArriveStat.setModelName("未知(" + ua + ")");
                    }
                } else {
                    logArriveStat.setModelName("未知()");
                }
                if (logArriveStat.getGroupId() != null) {
                    logArriveStat.setGroupName(GroupEnums.fromByValue(logArriveStat.getGroupId()).name);
                }

                Long channelId = logArriveStat.getChannelId();
                if (channelId != null) {
                    ChannelInfo channelInfo = null;
                    try {
                        channelInfo = channelInfoCacheService.getByChannelId(channelId);
                    } catch (Exception e) {
                        LOGGER.error("getByChannelId error", e);
                    }
                    if (channelInfo != null) {
                        logArriveStat.setChannelName(channelInfo.getChannelName());
                    } else {
                        logArriveStat.setModelName("未知");
                    }
                }
            }
        }

        return result;
    }

    @Override
    public LogArriveStat queryCountByVo(LogArriveStat record) {
        return logArriveStatAdapter.queryCountByVo(record);
    }

    @Log
    @Override
    public boolean statLogArrive(DataLog record) {
        LOGGER.info("statLogArrive Stat ---------开始");
        if (record != null) {
            String md5Key = ArriveStatConvertHandler.getMd5KeyForLogArriveStat(record);
            LOGGER.info("LogArriveStat md5Key={}", md5Key);
            int count = 0;
            while (true) {
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
