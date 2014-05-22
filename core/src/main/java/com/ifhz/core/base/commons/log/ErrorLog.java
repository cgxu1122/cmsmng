package com.ifhz.core.base.commons.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/22
 * Time: 13:13
 */
public final class ErrorLog {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorLog.class);

    public static boolean isErrorEnabled() {
        return LOGGER.isErrorEnabled();
    }

    public static void error(String msg) {
        LOGGER.error(msg);
    }

    public static void error(String format, Object... arguments) {
        LOGGER.error(format, arguments);
    }

    public static void error(String msg, Throwable t) {
        LOGGER.error(msg, t);
    }
}
