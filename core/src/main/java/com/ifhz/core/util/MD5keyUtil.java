/**
 * Copyright(c) 2013-2013 by Puhuifinance Inc.
 * All Rights Reserved
 */
package com.ifhz.core.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.security.MessageDigest;

/**
 * MD5加密工具类，
 *
 * @author luyujian
 */
public class MD5keyUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MD5keyUtil.class);

    /**
     * 返回加密的密文
     *
     * @param str 要加密的字符串
     * @return
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (Exception e) {
            LOGGER.error("NoSuchAlgorithmException error", e);
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString().toUpperCase();
    }

    /**
     * 对一个文件获取md5值
     *
     * @return md5串
     */
    public static String getMD5(InputStream inputStream) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(messageDigest.digest())).toLowerCase();
        } catch (Exception e) {
            LOGGER.error("getMD5 error", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }

    public static void main(String[] arg) {
        System.out.println(getMD5Str("222222"));
    }
}
