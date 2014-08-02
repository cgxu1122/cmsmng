package com.ifhz.core.service.imei.bean;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/2
 * Time: 16:26
 */
@XStreamAlias("PreInstall")
public class XmlBean implements Serializable {
    private static final long serialVersionUID = -3034369441265242367L;

    @XStreamAlias("Model")
    private String ua;
    @XStreamAlias("IMEI")
    private String imei;
    @XStreamAlias("Time")
    private String time;
    @XStreamAlias("copySuc")
    private String apkSuccName;
    @XStreamAlias("copyFail")
    private String apkFailName;


    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getApkSuccName() {
        return apkSuccName;
    }

    public void setApkSuccName(String apkSuccName) {
        this.apkSuccName = apkSuccName;
    }

    public String getApkFailName() {
        return apkFailName;
    }

    public void setApkFailName(String apkFailName) {
        this.apkFailName = apkFailName;
    }

    public static void main(String[] args) throws Exception {
        XmlBean bean = new XmlBean();
        bean.setUa("1");
        bean.setImei("1");
        bean.setApkSuccName("1");

        String xml = "<PreInstall>\n" +
                "\t<Model>Coolpad 8729</Model>\n" +
                "\t<IMEI>864545020212949</IMEI>\n" +
                "\t<Time> </Time>\n" +
                "\t<copySuc>oupeng-XX_2_182_538_1_10_1.apk</copySuc>\n" +
                "\t<copyFail> </copyFail>\n" +
                "</PreInstall>\n" +
                "<PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>TencentMobileManager3.8.6_android_build0601_673351.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>tadu_book_mark_1190.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>tadu_android_v2.36_1.6_997-sumsangI9008L.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>oupeng-XX_2_182_538_1_10_1.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>TencentMobileManager3.8.6_android_build0601_673351.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>tadu_book_mark_1190.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>tadu_android_v2.36_1.6_997-sumsangI9008L.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>kwplayer_ar_4.0.0.6_tianyin03.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>oupeng-XX_2_182_538_1_10_1.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>TencentMobileManager3.8.6_android_build0601_673351.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>tadu_book_mark_1190.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>tadu_android_v2.36_1.6_997-sumsangI9008L.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>oupeng-XX_2_182_538_1_10_1.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>TencentMobileManager3.8.6_android_build0601_673351.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>tadu_book_mark_1190.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>tadu_android_v2.36_1.6_997-sumsangI9008L.apk</copySuc><copyFail> </copyFail></PreInstall><PreInstall><Model>Coolpad 8729</Model><IMEI>864545020212949</IMEI><Time> </Time><copySuc>kwplayer_ar_4.0.0.6_tianyin03.apk</copySuc><copyFail> </copyFail></PreInstall>\u001B\u001C\n";

        XStream xStream = new XStream();
        xStream.processAnnotations(new Class[]{List.class, XmlBean.class});
        Object list = xStream.fromXML(xml);
        System.out.println(JSON.toJSONString(list));
    }
}
