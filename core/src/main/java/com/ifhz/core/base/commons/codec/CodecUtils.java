package com.ifhz.core.base.commons.codec;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/19
 * Time: 0:13
 */
public final class CodecUtils {
    private static byte KEY = 0x12;

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

        String str = decode("864545023839805|MI MU|7|C4:46:19:73:37:A7|TY2|" + new Date().getTime());
        JSONArray array = new JSONArray();
        array.add(str);
        System.out.println(array.toJSONString());
        System.out.println(decode("864545023839805|MI MU|7|C4:46:19:73:37:A7|TY2|" + new Date().getTime()));
        System.out.println(decode("*$&'&'\" !*!+*\"'n_[2_Gn%nQ&(&$(#+(%!(!%(S%nFK n#&\"&'!'%+%%$&"));
    }

    private CodecUtils() {
    }
}
