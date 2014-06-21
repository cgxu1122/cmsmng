package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.ChannelInfoAdapter;
import com.ifhz.core.adapter.DataLogAdapter;
import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.stat.DataLogQueryService;
import com.ifhz.core.service.stat.LogStatUpdateService;
import com.ifhz.core.service.stat.ProductStatTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 23:45
 */
@Service("productStatTaskService")
public class ProductStatTaskServiceImpl implements ProductStatTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductStatTaskServiceImpl.class);

    @Resource(name = "logStatUpdateService")
    private LogStatUpdateService logStatUpdateService;
    @Resource(name = "splitTableService")
    private SplitTableService splitTableService;
    @Resource(name = "dataLogAdapter")
    private DataLogAdapter dataLogAdapter;
    @Resource(name = "channelInfoAdapter")
    private ChannelInfoAdapter channelInfoAdapter;
    @Resource(name = "dataLogQueryService")
    private DataLogQueryService dataLogQueryService;
    //统计时分页处理条目，默认10000条
    public final int pageSize = MapConfig.getInt("stat.pageSize", GlobalConstants.GLOBAL_CONFIG, 10000);


    @Override
    public void scanDataLog(Date startTime, Date endTime) {

    }
}
