package com.ifhz.core.service.common.constants;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 12:58
 */
public final class SplitTableConstants {

    public static final String KEY_SYS_INIT_DATE = "system_init_date";

    public static final Map<Integer, Integer> MonthRefSeasonConf = initSeasonConf();

    private static Map<Integer, Integer> initSeasonConf() {
        Map<Integer, Integer> conf = Maps.newConcurrentMap();
//        conf.put();

        return conf;
    }

    private SplitTableConstants() {
    }
}
