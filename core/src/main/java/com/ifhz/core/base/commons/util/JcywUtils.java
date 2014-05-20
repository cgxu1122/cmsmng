package com.ifhz.core.base.commons.util;

/**
 * 类描述
 *
 * @author yangjian
 */
public final class JcywUtils {

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
