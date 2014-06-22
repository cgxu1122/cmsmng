package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.LogStatAdapter;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.service.stat.LogStatUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 23:26
 */
@Service("logStatUpdateService")
public class LogStatUpdateServiceImpl implements LogStatUpdateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogStatUpdateServiceImpl.class);

    @Resource(name = "logStatAdapter")
    private LogStatAdapter logStatAdapter;


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(LogStat record) {
        return logStatAdapter.insert(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(LogStat record) {
        return logStatAdapter.update(record);
    }

    @Override
    public LogStat getByMd5Key(String md5Key) {
        return logStatAdapter.getByMd5Key(md5Key);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStat(LogStat record) {
        return 0;
    }
}
