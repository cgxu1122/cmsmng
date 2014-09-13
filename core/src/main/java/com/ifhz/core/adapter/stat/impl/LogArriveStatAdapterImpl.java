package com.ifhz.core.adapter.stat.impl;

import com.ifhz.core.adapter.stat.LogArriveStatAdapter;
import com.ifhz.core.mapper.stat.LogArriveStatMapper;
import com.ifhz.core.po.stat.LogArriveStat;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:52
 */
@Repository("logArriveStatAdapter")
public class LogArriveStatAdapterImpl implements LogArriveStatAdapter {

    @Resource
    private LogArriveStatMapper logArriveStatMapper;

    @Override
    public int insert(LogArriveStat record) {
        record.setCreateTime(new Date());
        return logArriveStatMapper.insert(record);
    }

    @Override
    public int update(LogArriveStat record) {
        return logArriveStatMapper.update(record);
    }

    @Override
    public LogArriveStat getById(Long id) {
        return logArriveStatMapper.getById(id);
    }

    @Override
    public LogArriveStat getByMd5Key(String md5Key) {
        return logArriveStatMapper.getByMd5Key(md5Key);
    }
}
