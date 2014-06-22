package com.ifhz.core.service.api.impl;

import com.ifhz.core.adapter.CounterTempLogAdapter;
import com.ifhz.core.service.api.CounterTempLogApiService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/20
 * Time: 20:52
 */
@Service("counterTempLogApiService")
public class CounterTempLogApiServiceImpl implements CounterTempLogApiService {

    @Resource(name = "counterTempLogAdapter")
    private CounterTempLogAdapter counterTempLogAdapter;

}
