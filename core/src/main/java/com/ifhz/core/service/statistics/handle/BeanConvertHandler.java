package com.ifhz.core.service.statistics.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ifhz.core.po.CounterUploadLog;
import com.ifhz.core.po.DeviceProcessLog;
import com.ifhz.core.service.statistics.bean.CounterUploadBean;
import com.ifhz.core.service.statistics.bean.DeviceProcessBean;
import com.ifhz.core.service.statistics.constants.CounterActive;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/20
 * Time: 0:58
 */
public final class BeanConvertHandler {

    public static DeviceProcessBean convertDeviceBean(DeviceProcessLog log) throws Exception {
        DeviceProcessBean bean = new DeviceProcessBean();
        bean.setImei(log.getImei());
        bean.setModelName(log.getModelName());
        bean.setChannelId(Long.parseLong(log.getChannelId()));
        bean.setGroupId(log.getGroupId());
        bean.setDeviceCode(log.getDeviceCode());
        bean.setBatchCode(log.getBatchCode());
        bean.setProcessTime(new Date(Long.parseLong(log.getProcessTime())));
        bean.setCreateTime(log.getCreateTime());

        return bean;
    }

    public static CounterUploadBean convertCounterBean(CounterUploadLog log) throws Exception {
        CounterUploadBean bean = new CounterUploadBean();
        bean.setImei(log.getImei());
        bean.setModelName(log.getModelName());
        bean.setChannelId(Long.parseLong(log.getChannelId()));
        bean.setGroupId(log.getGroupId());
        bean.setDeviceCode(log.getDeviceCode());
        bean.setBatchCode(log.getBatchCode());
        bean.setProcessTime(new Date(Long.parseLong(log.getProcessTime())));
        bean.setCreateTime(log.getCreateTime());
        bean.setActive(CounterActive.getTypeByValue(log.getActive()));

        return bean;
    }

    public static void main(String[] args) throws Exception {
        /*
        libDetail	libName	String	是	apk包的名称
        version	    String	是	包版本
        model	    String	是	机型，包适用的机型名称
        apkDetail	apkName	String	是	apk名称
        apkVersion	String	是	apk版本
        apkSize	    long	是	apk文件大小
        md5Value	String	是	apk文件md5sum值
        path	    String	是	apk文件ftp路径
        creatIcon	boolean	是	是否创建快捷方式，true创建桌面快捷方式，false不创建
        autoRun	boolean	是	是否自动启动，true自启动，false不自启动

         */
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 0);
        jsonObject.put("version", new Date().getTime() + "");
        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("name", "H30-HY#1830//安装包名称");
        item.put("batchCode", "1830//批次号");
        item.put("type", "1//1:apk更新；2：机型更新");

        JSONArray apkList = new JSONArray();
        JSONObject apk = new JSONObject();
        apk.put("autoRun", "0//0:开机自启动；1:开机不自启动");
        apk.put("shortcut", "0//创建快捷方式，1：不创建快捷方式");
        apk.put("sort", 1);
        apk.put("counterApp", "0//0:此应用为计数器 1:不是计数器");
        apk.put("path", "/xxxxx/xxx/xxx.apk");
        JSONObject apk2 = new JSONObject();
        apk2.put("autoRun", "0//0:开机自启动；1:开机不自启动");
        apk2.put("shortcut", "1//创建快捷方式，1：不创建快捷方式");
        apk2.put("sort", 2);
        apk2.put("counterApp", "1//0:此应用为计数器 1:不是计数器");
        apk2.put("path", "/xxxxx/xxx/xxx.apk");
        JSONObject apk3 = new JSONObject();
        apk3.put("autoRun", "1//0:开机自启动；1:开机不自启动");
        apk3.put("shortcut", "0//创建快捷方式，1：不创建快捷方式");
        apk3.put("sort", 3);
        apk3.put("counterApp", "1//0:此应用为计数器 1:不是计数器");
        apk3.put("path", "/xxxxx/xxx/xxx.apk");

        apkList.add(apk);
        apkList.add(apk2);
        apkList.add(apk3);
        item.put("apkList", apkList);

        JSONArray modelList = new JSONArray();
        JSONObject model = new JSONObject();
        model.put("ua", "H30_HS");
        JSONObject model2 = new JSONObject();
        model2.put("ua", "H50_HS");
        JSONObject model3 = new JSONObject();
        model3.put("ua", "SAMSUNG_S4");

        modelList.add(model);
        modelList.add(model2);
        modelList.add(model3);
        item.put("modelList", modelList);
        item.put("commonLib", "Y//通用包标识");

        array.add(item);
        array.add(item);
        jsonObject.put("packageList", array);

        System.out.println(JSON.toJSONString(jsonObject));

    }

    private BeanConvertHandler() {
    }
}
