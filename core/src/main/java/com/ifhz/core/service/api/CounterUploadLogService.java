package com.ifhz.core.service.api;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.CounterUploadLog;

import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 16:28
 */
public interface CounterUploadLogService {


    public void insert(CounterUploadLog po);

    public CounterUploadLog queryHasImei(String imei);


    public List<Map<String, Object>> queryArriveImeiByPage(Pagination page, Map record);

    public List<Map<String, Object>> queryArriveImei(Map record);

    public List<Map<String, Object>> queryUploadImeiByPage(Pagination page, Map record);

    public List<Map<String, Object>> queryUploadImei(Map record);
}
