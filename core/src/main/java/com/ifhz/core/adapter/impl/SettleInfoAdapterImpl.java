package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.SettleInfoAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.mapper.SettleInfoMapper;
import com.ifhz.core.po.SettleInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/16
 * Time: 21:20
 */
@Repository("settleInfoAdapter")
public class SettleInfoAdapterImpl implements SettleInfoAdapter {

    @Resource(name = "settleInfoMapper")
    private SettleInfoMapper settleInfoMapper;

    @Override
    public SettleInfo getById(Long settleId) {
        return settleInfoMapper.getById(settleId);
    }

    @Override
    public List<SettleInfo> queryByVo(Pagination page, SettleInfo record) {
        List<SettleInfo> list = settleInfoMapper.queryByVo(page, record);
        return list == null ? Lists.<SettleInfo>newArrayList() : list;
    }

    @Override
    public int insert(SettleInfo record) {
        return settleInfoMapper.insert(record);
    }

    @Override
    public int update(SettleInfo record) {
        return settleInfoMapper.update(record);
    }

    @Override
    public int delete(SettleInfo record) {
        return settleInfoMapper.delete(record);
    }
}
