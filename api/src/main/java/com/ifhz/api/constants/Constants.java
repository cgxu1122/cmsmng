package com.ifhz.api.constants;

import com.alibaba.fastjson.JSONArray;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/19
 * Time: 23:32
 */
public class Constants {


    public static void main(String[] args) throws Exception {
        JSONArray array = new JSONArray();
        array.add("11111|xxxx|1|deviceCode|123|1391000000");
        array.add("22222|xxxx|1|deviceCode|123|1391000000");
        array.add("33333|xxxx|1|deviceCode|123|1391000000");
        array.add("44444|xxxx|1|deviceCode|123|1391000000");
        array.add("55555|xxxx|1|deviceCode|123|1391000000");


        System.out.println(array.toJSONString());
    }
}
