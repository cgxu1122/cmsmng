package com.ifhz.core.base.commons.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/22
 * Time: 13:17
 */
public class Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws Exception {
        CounterCommonLog.info("nihao|hello|fa");
        DeviceCommonLog.info("nihao|hello|fa");
        ErrorLog.error("nihao|hello|fa");
        LOGGER.info("aksdjflksjdlkfj");
        LOGGER.error("aksdjflksjdlkfj", new Exception("hello"));
    }
}
