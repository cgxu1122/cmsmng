package com.ifhz.core.service.imei;

import com.ifhz.core.service.imei.bean.StatImeiRequest;
import com.ifhz.core.service.imei.bean.StatImeiResult;

import java.util.List;


public interface StatImeiQueryService {

    public List<StatImeiResult> queryImeiListFromLog(StatImeiRequest request);

    public List<StatImeiResult> queryImeiListFromProduct(StatImeiRequest request);


}
