package com.ifhz.core.base.commons.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static boolean isCharOrNum(String str) {
        try {
            // 只允许字母和数字
            String regEx = "[a-z|A-Z|0-9]*";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            return m.matches();
        } catch (Exception e) {
            return false;
        }
    }

}
