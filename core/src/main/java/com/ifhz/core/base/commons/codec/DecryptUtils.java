package com.ifhz.core.base.commons.codec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 21:57
 */
public class DecryptUtils {

    /**
     * base64 加密字符串
     *
     * @param source
     * @return
     */
    public static String encodeByBase64(String source) {
        if (StringUtils.isNotEmpty(source)) {
            Base64 base64 = new Base64();
            byte[] bs = source.getBytes();

            return new String(base64.encode(bs));
        }

        return source;
    }


    /**
     * base64 解密字符串
     *
     * @param source
     * @return
     */
    public static String decodeByBase64(String source) {
        if (StringUtils.isNotEmpty(source)) {
            Base64 base64 = new Base64();
            byte[] bs = source.getBytes();
            return new String(base64.decode(bs));
        }

        return source;
    }
}
