package com.ifhz.core.service.stat;

import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.stat.ProductArriveStat;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:51
 */
public interface ProductArriveStatTempService {

    public boolean statTempProductArrive(DataLog record);

    public boolean asyncData(ProductArriveStat record, boolean isInsert);
}
