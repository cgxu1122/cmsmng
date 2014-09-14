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
public interface StatImeiService {

    public List<StatImeiResult> queryImeiList(StatImeiRequest request);
}
