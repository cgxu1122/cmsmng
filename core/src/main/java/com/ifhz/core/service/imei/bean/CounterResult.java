package com.ifhz.core.service.imei.bean;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/16
 * Time: 22:49
 */
public class CounterResult {

    private String imei;
    private String modelName;
    private String active;
    private String channelId;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }


    public static void main(String[] args) throws Exception {
        String sql = "insert into TY_COUNTER_UPLOAD_LOG(COUNTER_ID,IMEI,UA) values(SEQ_COUNTER_UPLOAD_LOG.NEXTVAL ,\'%s\',\'%s\');";
        sql = "insert into temp_test values(\'%s\');";
        for (int i = 0; i < 5000; i++) {
            Integer[] ts = new Integer[]{i};
//            String t = MessageFormat.format(sql, ts);
            String t = String.format(sql, ts);
            System.out.println(t);
        }

    }
}
