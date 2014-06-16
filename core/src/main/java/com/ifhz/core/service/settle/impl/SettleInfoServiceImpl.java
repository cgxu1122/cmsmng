package com.ifhz.core.service.settle.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.SettleInfoAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.SettleInfo;
import com.ifhz.core.service.settle.SettleInfoService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/16
 * Time: 21:20
 */
@Repository("settleInfoService")
public class SettleInfoServiceImpl implements SettleInfoService {

    @Resource(name = "settleInfoAdapter")
    private SettleInfoAdapter settleInfoAdapter;

    @Override
    public SettleInfo getById(Long settleId) {
        return settleInfoAdapter.getById(settleId);
    }

    @Override
    public List<SettleInfo> queryByVo(Pagination page, SettleInfo record) {
        List<SettleInfo> list = settleInfoAdapter.queryByVo(page, record);
        return list == null ? Lists.<SettleInfo>newArrayList() : list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(SettleInfo record) {
        return settleInfoAdapter.insert(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(SettleInfo record) {
        return settleInfoAdapter.update(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(SettleInfo record) {
        return settleInfoAdapter.delete(record);
    }
}
