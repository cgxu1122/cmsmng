package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.LogCountAdapter;
import com.ifhz.core.mapper.LogCountMapper;
import com.ifhz.core.po.LogCount;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lm on 14-5-15.
 */
@Repository("logCountAdapter")
public class LogCountAdapterImpl implements LogCountAdapter {


    @Resource(name = "logCountMapper")
    private LogCountMapper logCountMapper;

    @Override
    public LogCount getById(Long id) {
        return logCountMapper.getById(id);
    }

    @Override
    public List<LogCount> queryByVo(LogCount record) {
        List<LogCount> result = logCountMapper.queryByVo(record);
        return result == null ? Lists.<LogCount>newArrayList() : result;
    }

    @Override
    public int insert(LogCount record) {
        return logCountMapper.insert(record);
    }

    @Override
    public int update(LogCount record) {
        return logCountMapper.update(record);
    }

    @Override
    public int delete(LogCount record) {
        return logCountMapper.delete(record);
    }

    @Override
    public void batchInsert(List<LogCount> list) {
        logCountMapper.batchInsert(list);
    }

    @Override
    public LogCount getByProcessKey(String processKey) {
        return logCountMapper.getByProcessKey(processKey);
    }


}
