package com.ifhz.core.adapter.impl;

import com.ifhz.core.adapter.DictInfoAdapter;
import com.ifhz.core.mapper.DictInfoMapper;
import com.ifhz.core.po.DictInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 12:31
 */
@Repository("dictInfoAdapter")
public class DictInfoAdapterImpl implements DictInfoAdapter {

    @Resource(name = "dictInfoMapper")
    private DictInfoMapper dictInfoMapper;

    @Override
    public void insert(DictInfo po) {
        dictInfoMapper.insert(po);
    }

    @Override
    public DictInfo getByKeyCode(String keyCode) {
        if (StringUtils.isBlank(keyCode)) {
            return null;
        }
        return dictInfoMapper.getByKeyCode(keyCode);
    }
}
