package com.ifhz.core.base.commons.codec;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.base.commons.xml.JAXBUtil;
import com.ifhz.core.service.imei.bean.PreInstall;
import com.ifhz.core.service.imei.bean.PreInstallSet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/19
 * Time: 0:13
 */
public final class OtherCodecUtils {
    private static byte KEY = 0x16;


    public static String decode(String source) {
        if (StringUtils.isNotBlank(source)) {
            char[] charArray = source.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                charArray[i] ^= KEY;
            }
            return new String(charArray);
        }

        return source;
    }

    public static void main(String[] args) throws Exception {
        String log = "D:\\datalog\\log1.dat";
        String xml = FileUtils.readFileToString(new File(log));
        String str = OtherCodecUtils.decode(xml);
        System.out.println(str);

        Object obj;//= JAXBUtil.unmarshal(Object.class, log);

//        System.out.println(obj);
        String Xml_Prefix = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><list>";
        String Xml_Suffix = "</list>";
        str = Xml_Prefix + str + Xml_Suffix;
        System.out.println(str);

        PreInstallSet preInstallSet = JAXBUtil.unmarshalXmlStr(PreInstallSet.class, str);
        System.out.println(JSON.toJSONString(preInstallSet));

        for (PreInstall install : preInstallSet.getPreInstallSet()) {
            System.out.println(install.getImei());
        }


    }

    private OtherCodecUtils() {
    }
}
