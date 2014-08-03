package com.ifhz.core.vo;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/3
 * Time: 13:39
 */
public class DeviceRequestVo implements Serializable {
    private static final long serialVersionUID = -4060643911355668597L;
    private String id;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static void main(String[] args) throws Exception {
        JSONArray array = new JSONArray();
        DeviceRequestVo vo1 = new DeviceRequestVo();
        vo1.setId("1");
        vo1.setContent("加密数据");

        DeviceRequestVo vo2 = new DeviceRequestVo();
        vo2.setId("2");
        vo2.setContent("加密数据2");

        DeviceRequestVo vo3 = new DeviceRequestVo();
        vo3.setId("3");
        vo3.setContent("加密数据3");

        DeviceRequestVo vo4 = new DeviceRequestVo();
        vo4.setId("4");
        vo4.setContent("加密数据4");

        array.add(vo1);
        array.add(vo2);
        array.add(vo3);
        array.add(vo4);


        System.out.println(array.toJSONString());
    }
}
