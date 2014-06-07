package com.ifhz.core.adapter.impl;

import com.ifhz.core.adapter.CounterUploadLogAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.CounterUploadLogMapper;
import com.ifhz.core.po.CounterUploadLog;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 11:13
 */
@Repository("counterUploadLogAdapter")
public class CounterUploadLogAdapterImpl implements CounterUploadLogAdapter {

    @Resource(name = "counterUploadLogMapper")
    private CounterUploadLogMapper counterUploadLogMapper;

    @Override
    public void insert(String tableName, CounterUploadLog po) {
        po.setTableName(tableName);
        counterUploadLogMapper.insert(po);
    }

    @Override
    public CounterUploadLog getByImei(String tableName, String imei) {
        if (StringUtils.isBlank(imei)) {
            return null;
        }
        return counterUploadLogMapper.getByImei(tableName, imei);
    }

    @Override
    public List<CounterUploadLog> getCounterUploadLog(Map m) {
        return counterUploadLogMapper.getCounterUploadLog(m);
    }

    @Override
    public long getYesterdayLogCount(Map tableName) {
        return counterUploadLogMapper.getYesterdayLogCount(tableName);
    }

    @Override
    public long getLogCountByTime(Map m) {
        return counterUploadLogMapper.getLogCountByTime(m);
    }

    /*
    查询装机到达数量imei明细
    分页，用于页面展示
    lm
     */
    @Override
    public List<Map<String, Object>> queryArriveImeiByPage(Pagination page, Map record) {
        return counterUploadLogMapper.queryArriveImeiByPage(page, record);
    }

    /*
    查询装机到达数量imei明细
    不分页，用于导出到execl
    lm
     */
    @Override
    public List<Map<String, Object>> queryArriveImei(Map record) {
        return counterUploadLogMapper.queryArriveImei(record);
    }

    /*
        查询累计到达数量imei明细
        分页，用于页面展示
        lm
   */
    public List<Map<String, Object>> queryUploadImeiByPage(Pagination page, Map record) {
        return counterUploadLogMapper.queryUploadImeiByPage(page, record);
    }

    /*
    查询累计到达数量imei明细
    不分页，用于导出到execl
    lm
     */
    public List<Map<String, Object>> queryUploadImei(Map record) {
        return counterUploadLogMapper.queryUploadImei(record);
    }
}
