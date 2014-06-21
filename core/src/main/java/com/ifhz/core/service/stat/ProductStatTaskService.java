package com.ifhz.core.service.stat;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 17:17
 */
public interface ProductStatTaskService {

    /**
     * 产品统计[startTime,endTime]内流水数据表，不允许跨天
     *
     * @param startTime 开始时间 不允许跨天
     * @param endTime   结束时间 不允许跨天
     */
    public void scanDataLog(Date startTime, Date endTime);
}
