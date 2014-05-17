package com.ifhz.core.service.example.impl;

import com.ifhz.core.adapter.ExampleAdapter;
import com.ifhz.core.po.Example;
import com.ifhz.core.service.example.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/17
 * Time: 21:52
 */
@Service
public class ExampleServiceImpl implements ExampleService {

    @Autowired
    private ExampleAdapter exampleAdapter;

    @Override
    public Example getById(Long id) {
        return exampleAdapter.getById(id);
    }

    @Override
    public int insert(Example po) {
        return exampleAdapter.insert(po);
    }

    @Override
    public int update(Example po) {
        return exampleAdapter.update(po);
    }

    @Override
    public int delete(Long id) {
        return exampleAdapter.delete(id);
    }
}
