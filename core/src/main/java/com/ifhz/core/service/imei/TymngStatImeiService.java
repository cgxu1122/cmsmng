package com.ifhz.core.service.imei;

import com.ifhz.core.service.imei.bean.StatImeiRequest;
import com.ifhz.core.service.imei.bean.StatImeiResult;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/13
 * Time: 22:05
 */
public interface TymngStatImeiService {

    public List<StatImeiResult> queryImeiListFromLog(StatImeiRequest request);

    public List<StatImeiResult> queryImeiListFromProduct(StatImeiRequest request);
}
