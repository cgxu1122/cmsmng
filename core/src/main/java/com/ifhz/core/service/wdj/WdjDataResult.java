package com.ifhz.core.service.wdj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/24
 * Time: 17:58
 */
public class WdjDataResult implements Serializable {
    private static final long serialVersionUID = -4902115632994530807L;
    /*
    {
        "info": "未知专题",
        "account": "dimengjie",
        "version": "1.0.2.80",
        "result": true,
        "batch_no": "",
        "imei": "862384020279837",
        "model": "TCL Y910",
        "install_dt": "2014-05-15"
    }
     */

    private String info;
    private String account;
    private String version;
    private boolean result;
    @JSONField(name = "batch_no")
    private String batchNo;
    private String imei;
    private String model;
    @JSONField(name = "install_dt")
    private String installDate;
    private String deptId;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public static void main(String[] args) throws Exception {
        String str = "{\n" +
                "        \"info\": \"未知专题\",\n" +
                "        \"account\": \"dimengjie\",\n" +
                "        \"version\": \"1.0.2.80\",\n" +
                "        \"result\": true,\n" +
                "        \"batch_no\": \"\",\n" +
                "        \"imei\": \"862384020279837\",\n" +
                "        \"model\": \"TCL Y910\",\n" +
                "        \"install_dt\": \"2014-05-15\"\n" +
                "    }";
        WdjDataResult result = JSON.parseObject(str, WdjDataResult.class);

        System.out.println(JSON.toJSONString(result));
    }
}
