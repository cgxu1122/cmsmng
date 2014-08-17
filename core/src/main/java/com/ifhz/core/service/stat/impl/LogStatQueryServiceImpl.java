package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.LogStatAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import com.ifhz.core.service.cache.ModelInfoCacheService;
import com.ifhz.core.service.stat.LogStatQueryService;
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
 * Date: 2014/6/21
 * Time: 17:16
 */
@Service("logStatQueryService")
public class LogStatQueryServiceImpl implements LogStatQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogStatQueryServiceImpl.class);
    @Resource(name = "logStatAdapter")
    private LogStatAdapter logStatAdapter;
    @Resource(name = "channelInfoCacheService")
    private ChannelInfoCacheService channelInfoCacheService;
    @Resource(name = "modelInfoCacheService")
    private ModelInfoCacheService modelInfoCacheService;

    @Override
    @Log
    public List<LogStat> queryByVo(Pagination page, LogStat record) {
        List<LogStat> logStatList = logStatAdapter.queryByVO(page, record);
        if (CollectionUtils.isNotEmpty(logStatList)) {
            for (LogStat logStat : logStatList) {
                String ua = logStat.getUa();
                if (StringUtils.isNotEmpty(ua)) {
                    ModelInfo modelInfo = null;
                    try {
                        modelInfo = modelInfoCacheService.getByUaAndGrouId(ua, logStat.getGroupId());
                    } catch (Exception e) {
                        LOGGER.error("getByUaAndGrouId error", e);
                    }
                    if (modelInfo != null) {
                        logStat.setModelName(modelInfo.getModelName() + "(" + ua + ")");
                    } else {
                        logStat.setModelName("未知(" + ua + ")");
                    }
                } else {
                    logStat.setModelName("未知()");
                }
                Long channelId = logStat.getChannelId();
                if (channelId != null) {
                    ChannelInfo channelInfo = null;
                    try {
                        channelInfo = channelInfoCacheService.getByChannelId(channelId);
                    } catch (Exception e) {
                        LOGGER.error("getByChannelId error", e);
                    }
                    if (channelInfo != null) {
                        logStat.setChannelName(channelInfo.getChannelName());
                    } else {
                        logStat.setModelName("未知");
                    }
                }
            }
        }

        return logStatList;
    }

    @Override
    public List<LogStat> queryHzfListByVo(Pagination page, LogStat record) {
        List<LogStat> logStatList = logStatAdapter.queryHzfListByVo(page, record);
        if (CollectionUtils.isNotEmpty(logStatList)) {
            for (LogStat logStat : logStatList) {
                String ua = logStat.getUa();
                if (StringUtils.isNotEmpty(ua)) {
                    ModelInfo modelInfo = null;
                    try {
                        modelInfo = modelInfoCacheService.getByUaAndGrouId(ua, logStat.getGroupId());
                    } catch (Exception e) {
                        LOGGER.error("getByUaAndGrouId error", e);
                    }
                    if (modelInfo != null) {
                        logStat.setModelName(modelInfo.getModelName() + "(" + ua + ")");
                    } else {
                        logStat.setModelName("未知(" + ua + ")");
                    }
                } else {
                    logStat.setModelName("未知()");
                }
                Long channelId = logStat.getChannelId();
                if (channelId != null) {
                    ChannelInfo channelInfo = null;
                    try {
                        channelInfo = channelInfoCacheService.getByChannelId(channelId);
                    } catch (Exception e) {
                        LOGGER.error("getByChannelId error", e);
                    }
                    if (channelInfo != null) {
                        logStat.setChannelName(channelInfo.getChannelName());
                    } else {
                        logStat.setModelName("未知");
                    }
                }
            }
        }

        return logStatList;
    }

    @Override
    @Log
    public List<LogStat> querySumByVo(Pagination page, LogStat record) {
        List<LogStat> logStatList = logStatAdapter.querySumByVO(page, record);
        if (CollectionUtils.isNotEmpty(logStatList)) {
            for (LogStat logStat : logStatList) {
                String ua = logStat.getUa();
                if (StringUtils.isNotEmpty(ua)) {
                    ModelInfo modelInfo = null;
                    try {
                        modelInfo = modelInfoCacheService.getByUaAndGrouId(ua, logStat.getGroupId());
                    } catch (Exception e) {
                        LOGGER.error("getByUaAndGrouId error", e);
                    }
                    if (modelInfo != null) {
                        logStat.setModelName(modelInfo.getModelName() + "(" + ua + ")");
                    } else {
                        logStat.setModelName("未知(" + ua + ")");
                    }
                } else {
                    logStat.setModelName("未知()");
                }
                Long channelId = logStat.getChannelId();
                if (channelId != null) {
                    ChannelInfo channelInfo = null;
                    try {
                        channelInfo = channelInfoCacheService.getByChannelId(channelId);
                    } catch (Exception e) {
                        LOGGER.error("getByChannelId error", e);
                    }
                    if (channelInfo != null) {
                        logStat.setChannelName(channelInfo.getChannelName());
                    } else {
                        logStat.setModelName("未知");
                    }
                }
            }
        }

        return logStatList;
    }

    @Override
    @Log
    public LogStat queryCountByVo(LogStat record) {
        return logStatAdapter.queryCountByVO(record);
    }
}
