package com.ifhz.core.adapter;

import com.ifhz.core.service.imei.bean.DataLogResult;

import java.util.List;
import java.util.Set;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/16
 * Time: 23:54
 */
public interface ImeiQueryAdapter {

    public int insertBatch(Set<String> set);

    public List<DataLogResult> queryListByImeiList(String tableName);

    public List<String> queryImeiList();
}
