package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.LogStatAdapter;
import com.ifhz.core.mapper.LogStatMapper;
import com.ifhz.core.po.LogStat;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 16:49
 */
@Repository("logStatAdapter")
public class LogStatAdapterImpl implements LogStatAdapter {

    @Resource(name = "logStatMapper")
    private LogStatMapper logStatMapper;

    @Override
    public int insert(LogStat record) {
        return logStatMapper.insert(record);
    }

    @Override
    public int update(LogStat record) {
        return logStatMapper.update(record);
    }

    @Override
    public LogStat getById(Long id) {
        return logStatMapper.getById(id);
    }

    @Override
    public LogStat getByMd5Key(String md5Key) {
        return logStatMapper.getByMd5Key(md5Key);
    }

    @Override
    public List<LogStat> queryListByQueryKey(String queryKey, Date startTime, Date endTime) {
        List<LogStat> list = logStatMapper.queryListByQueryKey(queryKey, startTime, endTime);
        return list == null ? Lists.<LogStat>newArrayList() : list;
    }
}
