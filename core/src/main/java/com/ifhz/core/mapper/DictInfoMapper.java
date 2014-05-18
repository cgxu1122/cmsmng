package com.ifhz.core.mapper;

import com.ifhz.core.po.DictInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 12:13
 */
public interface DictInfoMapper {

    public void insert(DictInfo po);

    public DictInfo getByKeyCode(@Param("keyCode") String keyCode);
}
