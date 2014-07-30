package com.ifhz.core.adapter.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.adapter.SysUserProductRefAdapter;
import com.ifhz.core.mapper.SysUserProductRefMapper;
import com.ifhz.core.po.auth.SysUserProductRef;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 11:47
 */
@Repository("userProductRefAdapter")
public class SysUserProductRefAdapterImpl implements SysUserProductRefAdapter {

    @Resource(name = "sysUserProductRefMapper")
    private SysUserProductRefMapper sysUserProductRefMapper;

    @Override
    public int insert(SysUserProductRef record) {
        record.setCreateTime(new Date());
        return sysUserProductRefMapper.insert(record);
    }

    @Override
    public int delete(SysUserProductRef record) {
        return sysUserProductRefMapper.delete(record);
    }

    @Override
    public List<SysUserProductRef> queryListByUserId(Long userId) {
        List<SysUserProductRef> result = sysUserProductRefMapper.queryListByUserId(userId);
        return result == null ? Lists.<SysUserProductRef>newArrayList() : result;
    }


}
