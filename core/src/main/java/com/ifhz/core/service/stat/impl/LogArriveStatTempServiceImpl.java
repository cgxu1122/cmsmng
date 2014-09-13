package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.stat.LogArriveStatAdapter;
import com.ifhz.core.adapter.stat.LogArriveStatTempAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.po.stat.LogArriveStat;
import com.ifhz.core.po.stat.LogArriveStatTemp;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import com.ifhz.core.service.cache.ModelInfoCacheService;
import com.ifhz.core.service.stat.LogArriveStatTempService;
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
 * Date: 2014/8/24
 * Time: 19:52
 */
@Service
public class LogArriveStatTempServiceImpl implements LogArriveStatTempService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogArriveStatTempServiceImpl.class);

    @Resource
    private LogArriveStatTempAdapter logArriveStatTempAdapter;
    @Resource
    private LogArriveStatAdapter logArriveStatAdapter;
    @Resource
    private ModelInfoCacheService modelInfoCacheService;
    @Resource(name = "channelInfoCacheService")
    private ChannelInfoCacheService channelInfoCacheService;

    @Override
    @Log
    public List<LogArriveStatTemp> queryByVo(Pagination pagination, LogArriveStatTemp record) {
        List<LogArriveStatTemp> result = logArriveStatTempAdapter.queryByVo(pagination, record);
        if (CollectionUtils.isNotEmpty(result)) {
            for (LogArriveStatTemp logArriveStatTemp : result) {
                String ua = logArriveStatTemp.getUa();
                if (StringUtils.isNotEmpty(ua)) {
                    ModelInfo modelInfo = null;
                    try {
                        modelInfo = modelInfoCacheService.getByUaAndGrouId(ua, logArriveStatTemp.getGroupId());
                    } catch (Exception e) {
                        LOGGER.error("getByUaAndGrouId error", e);
                    }
                    if (modelInfo != null) {
                        logArriveStatTemp.setModelName(modelInfo.getModelName() + "(" + ua + ")");
                    } else {
                        logArriveStatTemp.setModelName("未知(" + ua + ")");
                    }
                } else {
                    logArriveStatTemp.setModelName("未知()");
                }

                Long channelId = logArriveStatTemp.getChannelId();
                if (channelId != null) {
                    ChannelInfo channelInfo = null;
                    try {
                        channelInfo = channelInfoCacheService.getByChannelId(channelId);
                    } catch (Exception e) {
                        LOGGER.error("getByChannelId error", e);
                    }
                    if (channelInfo != null) {
                        logArriveStatTemp.setChannelName(channelInfo.getChannelName());
                    } else {
                        logArriveStatTemp.setModelName("未知");
                    }
                }
            }
        }

        return result;
    }

    @Override
    @Log
    public List<LogArriveStatTemp> querySumByVo(Pagination pagination, LogArriveStatTemp record) {
        List<LogArriveStatTemp> result = logArriveStatTempAdapter.querySumByVo(pagination, record);
        if (CollectionUtils.isNotEmpty(result)) {
            for (LogArriveStatTemp logArriveStatTemp : result) {
                String ua = logArriveStatTemp.getUa();
                if (StringUtils.isNotEmpty(ua)) {
                    ModelInfo modelInfo = null;
                    try {
                        modelInfo = modelInfoCacheService.getByUaAndGrouId(ua, logArriveStatTemp.getGroupId());
                    } catch (Exception e) {
                        LOGGER.error("getByUaAndGrouId error", e);
                    }
                    if (modelInfo != null) {
                        logArriveStatTemp.setModelName(modelInfo.getModelName() + "(" + ua + ")");
                    } else {
                        logArriveStatTemp.setModelName("未知(" + ua + ")");
                    }
                } else {
                    logArriveStatTemp.setModelName("未知()");
                }

                Long channelId = logArriveStatTemp.getChannelId();
                if (channelId != null) {
                    ChannelInfo channelInfo = null;
                    try {
                        channelInfo = channelInfoCacheService.getByChannelId(channelId);
                    } catch (Exception e) {
                        LOGGER.error("getByChannelId error", e);
                    }
                    if (channelInfo != null) {
                        logArriveStatTemp.setChannelName(channelInfo.getChannelName());
                    } else {
                        logArriveStatTemp.setModelName("未知");
                    }
                }
            }
        }

        return result;
    }

    @Override
    @Log
    public LogArriveStatTemp queryCountByVo(LogArriveStatTemp record) {
        return logArriveStatTempAdapter.queryCountByVo(record);
    }

    @Override
    @Log
    public int insert(LogArriveStat logArriveStat) {
        LogArriveStatTemp result = new LogArriveStatTemp();
        result.setId(logArriveStat.getId());
        result.setChannelId(logArriveStat.getChannelId());
        result.setGroupId(logArriveStat.getGroupId());
        result.setUa(logArriveStat.getUa());
        result.setCreateTime(logArriveStat.getCreateTime());
        result.setLaowuId(logArriveStat.getLaowuId());
        result.setStatDate(logArriveStat.getStatDate());
        result.setTotalNum(logArriveStat.getTotalNum());
        result.setValidNum(logArriveStat.getValidNum());

        return logArriveStatTempAdapter.insert(result);
    }

    @Override
    @Log
    public boolean syncLogArriveStat(Date startTime, Date endTime) {
        long totalCount = logArriveStatAdapter.queryTotalCount(startTime, endTime);
        LOGGER.info("数据总量为{}", totalCount);
        if (totalCount > 0) {
            long pageNum = StatConvertHandler.getPageNum(totalCount, GlobalConstants.PAGE_SIZE);
            LOGGER.info("数据总量为{},分页值为{}", totalCount, pageNum);
            for (int i = 0; i < pageNum; i++) {
                Pagination page = new Pagination();
                page.setPageSize(GlobalConstants.PAGE_SIZE);
                page.setCurrentPage(i + 1);
                List<LogArriveStat> logArriveStatList = logArriveStatAdapter.queryStatList(page, startTime, endTime);
                if (CollectionUtils.isNotEmpty(logArriveStatList)) {
                    for (LogArriveStat logArriveStat : logArriveStatList) {
                        try {
                            insert(logArriveStat);
                        } catch (Exception e) {
                            LOGGER.error("insert LogArriveStatTemp error", e);
                        }
                    }
                }
            }
        }

        return true;
    }
}
