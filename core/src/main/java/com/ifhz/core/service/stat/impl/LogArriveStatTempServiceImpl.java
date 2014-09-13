package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.stat.LogArriveStatTempAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.LogArriveStat;
import com.ifhz.core.po.stat.LogArriveStatTemp;
import com.ifhz.core.service.stat.LogArriveStatTempService;
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
public class LogArriveStatTempServiceImpl implements LogArriveStatTempService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogArriveStatTempServiceImpl.class);

    @Resource
    private LogArriveStatTempAdapter logArriveStatTempAdapter;

    @Override
    @Log
    public List<LogArriveStatTemp> queryByVo(Pagination pagination, LogArriveStatTemp record) {

        return null;
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
    public boolean syncLogArriveStat() {
        return false;
    }
}
