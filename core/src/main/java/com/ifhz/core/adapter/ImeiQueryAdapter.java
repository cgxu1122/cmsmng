package com.ifhz.core.adapter;

import com.ifhz.core.service.imei.bean.DataLogResult;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/16
 * Time: 23:54
 */
public interface ImeiQueryAdapter {

    public int insertBatch(List<String> list);

    public List<DataLogResult> queryListByImeiList(String tableName);
}
