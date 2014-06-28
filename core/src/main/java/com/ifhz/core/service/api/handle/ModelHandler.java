package com.ifhz.core.service.api.handle;

import org.apache.commons.lang.StringUtils;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/24
 * Time: 18:33
 */
public final class ModelHandler {


    public static String translateUa(String ua) {
        if (StringUtils.isNotBlank(ua)) {
            String uaTemp = StringUtils.replace(ua, " +", " ");
            return StringUtils.replace(uaTemp, " ", "_");
        }

        return ua;
    }
}
