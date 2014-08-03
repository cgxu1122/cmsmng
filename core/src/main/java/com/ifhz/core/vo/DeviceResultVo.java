package com.ifhz.core.vo;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/3
 * Time: 13:40
 */
public class DeviceResultVo implements Serializable {
    private static final long serialVersionUID = -6361071092024483709L;

    private String id;
    private boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static void main(String[] args) throws Exception {
        JSONArray array = new JSONArray();
        DeviceResultVo vo1 = new DeviceResultVo();
        vo1.setId("1");
        vo1.setActive(true);

        DeviceResultVo vo2 = new DeviceResultVo();
        vo2.setId("2");
        vo2.setActive(true);

        DeviceResultVo vo3 = new DeviceResultVo();
        vo3.setId("3");
        vo3.setActive(true);

        DeviceResultVo vo4 = new DeviceResultVo();
        vo4.setId("4");
        vo4.setActive(false);

        array.add(vo1);
        array.add(vo2);
        array.add(vo3);
        array.add(vo4);

        System.out.println(array.toJSONString());
    }

}
