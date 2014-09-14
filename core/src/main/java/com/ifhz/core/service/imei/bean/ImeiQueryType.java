package com.ifhz.core.service.imei.bean;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 16:58
 */
public enum ImeiQueryType {

//    Day_Device_Process(1),
//    Day_Counter_Upload(3),

    Log_Install(10),
    Log_Install_Arrive(11),
    Log_Arrive(12),
    Log_Arrive_Temp(13),

    Product_Install(14),
    Product_Install_Arrive(15),
    Product_Arrive(16),
    Product_Arrive_Temp(17);

    public final int value;

    ImeiQueryType(int value) {
        this.value = value;
    }
}
