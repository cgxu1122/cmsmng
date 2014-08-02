package com.ifhz.core.base.commons.codec;

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
        String xml = FileUtils.readFileToString(new File("E://log.dat"));
        String str = OtherCodecUtils.decode(xml);
        System.out.println(str);
    }

    private OtherCodecUtils() {
    }
}
