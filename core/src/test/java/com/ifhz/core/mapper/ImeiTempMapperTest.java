package com.ifhz.core.mapper;

import com.google.common.collect.Lists;
import com.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class ImeiTempMapperTest extends BaseTest {

    @Resource(name = "imeiTempMapper")
    private ImeiTempMapper imeiTempMapper;

    @Test
    public void testInsertBatch() throws Exception {
        List<String> list = Lists.newArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        int i = imeiTempMapper.insertBatch(list);
        System.out.println(i);
    }
}