package com.ifhz.core.service.example.impl;

import com.ifhz.core.po.Example;
import com.ifhz.core.service.example.ExampleService;
import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ExampleServiceImplTest extends BaseTest {

    @Autowired
    private ExampleService exampleService;

    @Test
    public void testGetById() throws Exception {
        log(exampleService.getById(11111L));
    }

    @Test
    public void testInsert() throws Exception {
        Example po = new Example();
        po.setName("hello,world");
        exampleService.insert(po);

        log(po);
    }

    @Test
    public void testUpdate() throws Exception {
        Example po = new Example();
        po.setId(1L);
        po.setName("hello,world + 你好");
        exampleService.update(po);

        log(po);
    }

    @Test
    public void testDelete() throws Exception {
        int l = exampleService.delete(1L);
        log(l);
    }
}