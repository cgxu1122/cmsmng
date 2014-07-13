package com.ifhz.core.utils;

import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.constants.GlobalConstants;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/10
 * Time: 0:18
 */
public class HostsHandle {

    public static String getHostPrefix() {
        return MapConfig.getString("host.prefix", GlobalConstants.GLOBAL_CONFIG, "http://www.tymng.com");
    }
}
