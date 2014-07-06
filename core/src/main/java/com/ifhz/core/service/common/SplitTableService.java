package com.ifhz.core.service.common;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 12:47
 */
public interface SplitTableService {

    /**
     * 获取流水数据当前插入的表名称
     *
     * @param now
     * @return
     */
    public String getCurrentTableName(Date now);

    /**
     * 流水数据 验证数据需要查询的表名列表
     *
     * @param now
     * @return
     */
    public List<String> getTableNameList(Date now);


    /**
     * 流水数据 从指定日期到当前日期的表明列表
     *
     * @param date
     * @return
     */
    public List<String> getListFromDate2Now(Date date);
}
