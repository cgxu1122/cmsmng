package com.ifhz.api.utils;

import com.alibaba.fastjson.JSONObject;
import com.ifhz.api.constants.ResultType;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/19
 * Time: 23:30
 */
public class ApiJsonHandler {


    public static JSONObject genJsonRet(ResultType type) {
        JSONObject ret = new JSONObject();
        if (type == ResultType.SuccNonUpgrade) {
            ret.put("status", type.VALUE);
        } else {
            ret.put("status", type.VALUE);
        }

        return ret;
    }
}
