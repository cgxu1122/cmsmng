package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.ImeiQueryAdapter;
import com.ifhz.core.mapper.ImeiTempMapper;
import com.ifhz.core.service.imei.bean.DataLogResult;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/16
 * Time: 23:54
 */
@Repository("imeiQueryAdapter")
public class ImeiQueryAdapterImpl implements ImeiQueryAdapter {

    @Resource(name = "imeiTempMapper")
    private ImeiTempMapper imeiTempMapper;

    @Override
    public int insertBatch(Set<String> list) {
        return imeiTempMapper.insertBatch(list);
    }

    @Override
    public List<DataLogResult> queryListByImeiList(String tableName) {
        List<DataLogResult> list = imeiTempMapper.queryListByImeiList(tableName);
        return list == null ? Lists.<DataLogResult>newArrayList() : list;
    }

    @Override
    public List<String> queryImeiList() {
        return imeiTempMapper.queryImeiList();
    }
}
