package com.ifhz.core.service.imei;

import com.ifhz.core.service.imei.bean.StatImeiRequest;
import com.ifhz.core.service.imei.bean.StatImeiResult;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 16:38
 */
public interface StatImeiQueryService {

    public List<StatImeiResult> queryImeiListFromLog(StatImeiRequest request);

    public List<StatImeiResult> queryImeiListFromProduct(StatImeiRequest request);
}
