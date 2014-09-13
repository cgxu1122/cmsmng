package com.ifhz.core.adapter.stat.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.stat.LogArriveStatAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.stat.LogArriveStatMapper;
import com.ifhz.core.po.stat.LogArriveStat;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    @Override
    public List<LogArriveStat> queryByVo(Pagination pagination, LogArriveStat record) {
        List<LogArriveStat> result = logArriveStatMapper.queryByVo(pagination, record);
        return result == null ? Lists.<LogArriveStat>newArrayList() : result;
    }

    @Override
    public LogArriveStat queryCountByVo(LogArriveStat record) {
        return logArriveStatMapper.queryCountByVo(record);
    }

    @Override
    public long queryTotalCount(Date startTime, Date endTime) {
        return logArriveStatMapper.queryTotalCount(startTime, endTime);
    }

    @Override
    public List<LogArriveStat> queryStatList(Date startTime, Date endTime) {
        return logArriveStatMapper.queryStatList(startTime, endTime);
    }
}
