package com.ifhz.core.adapter.impl;

import com.ifhz.core.adapter.ExampleAdapter;
import com.ifhz.core.mapper.ExampleMapper;
import com.ifhz.core.po.Example;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/17
 * Time: 21:52
 */
@Repository("exampleAdapter")
public class ExampleAdapterImpl implements ExampleAdapter {

    @Resource(name = "exampleMapper")
    private ExampleMapper exampleMapper;

    @Override
    public Example getById(Long id) {
        return exampleMapper.getById(id);
    }

    @Override
    public int insert(Example po) {
        return exampleMapper.insert(po);
    }

    @Override
    public int update(Example po) {
        return exampleMapper.update(po);
    }

    @Override
    public int delete(Long id) {
        return exampleMapper.delete(id);
    }
}
