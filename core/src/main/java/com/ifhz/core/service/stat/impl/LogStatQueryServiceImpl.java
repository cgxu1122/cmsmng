package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.ChannelInfoAdapter;
import com.ifhz.core.adapter.LogStatAdapter;
import com.ifhz.core.adapter.ModelInfoAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.service.stat.LogStatQueryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
    @Resource(name = "logStatAdapter")
    private LogStatAdapter logStatAdapter;
    @Resource(name = "channelInfoAdapter")
    private ChannelInfoAdapter channelInfoAdapter;
    @Resource(name = "modelInfoAdapter")
    private ModelInfoAdapter modelInfoAdapter;

    @Override
    public List<LogStat> queryByVo(Pagination page, LogStat record) {
        List<LogStat> logStatList = logStatAdapter.queryByVO(page, record);
        if (CollectionUtils.isNotEmpty(logStatList)) {
            for (LogStat logStat : logStatList) {
                String ua = logStat.getUa();
                if (StringUtils.isNotEmpty(ua)) {
                    ModelInfo modelInfo = modelInfoAdapter.getByGroupIdAndUa(logStat.getGroupId(), ua);
                    if (modelInfo != null) {
                        logStat.setModelName(modelInfo.getModelName());
                    }
                }
                Long channelId = logStat.getChannelId();
                if (channelId != null) {
                    ChannelInfo channelInfo = channelInfoAdapter.getById(channelId);
                    if (channelInfo != null) {
                        logStat.setChannelName(channelInfo.getChannelName());
                    }
                }
            }
        }
        return logStatList;
    }
}
