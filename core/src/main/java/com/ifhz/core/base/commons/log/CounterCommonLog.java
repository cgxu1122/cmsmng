package com.ifhz.core.base.commons.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/22
 * Time: 13:00
 */
public final class CounterCommonLog {
    private static final Logger LOGGER = LoggerFactory.getLogger(CounterCommonLog.class);

    public static boolean isInfoEnabled() {
        return LOGGER.isInfoEnabled();
    }

    public static void info(String msg) {
        LOGGER.info(msg);
    }

    public static void info(String format, Object... arguments) {
        LOGGER.info(format, arguments);
    }

    public static void info(String msg, Throwable t) {
        LOGGER.info(msg, t);
    }
}
