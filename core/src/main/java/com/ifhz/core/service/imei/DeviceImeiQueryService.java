package com.ifhz.core.service.imei;

import com.ifhz.core.service.imei.bean.StatImeiRequest;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 17:23
 */
public interface DeviceImeiQueryService {


    public List<String> getLogImeiList(List<String> tableNameList, StatImeiRequest request);

    public List<String> getProductImeiList(List<String> tableNameList, StatImeiRequest request);
}
