package com.ifhz.core.adapter;

import com.ifhz.core.po.Example;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/17
 * Time: 21:52
 */
public interface ExampleAdapter {

    public Example getById(Long id);

    public int insert(Example po);

    public int update(Example po);

    public int delete(Long id);

}
