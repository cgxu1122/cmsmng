package com.ifhz.core.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 9:24
 */
public final class PatternUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatternUtils.class);
    private static final String LoginNameRegex = "[A-Za-z0-9]+";
    private static final String ApkSoftNameRegex = "[A-Za-z0-9\\._-]+";
    /**
     * 特殊字符 校验正则 "."没有做校验
     */
    private static final String REGEX = "[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";


    public static boolean verifyLoginName(String input) {
        LOGGER.info("input={}", input);
        boolean result = false;
        if (StringUtils.isNotBlank(input)) {
            Pattern pattern = Pattern.compile(LoginNameRegex);
            Matcher matcher = pattern.matcher(input);
            result = matcher.matches();
        }
        LOGGER.info("input={}, returnObj={}", input, result);
        return result;
    }

    public static boolean verifyApkSoftName(String input) {
        LOGGER.info("input={}", input);
        boolean result = false;
        if (StringUtils.isNotBlank(input)) {
            Pattern pattern = Pattern.compile(ApkSoftNameRegex);
            Matcher matcher = pattern.matcher(input);
            result = matcher.matches();
        }
        LOGGER.info("input={}, returnObj={}", input, result);
        return result;
    }

    public static boolean verifyWithinSpeChar(String input) {
        LOGGER.info("input={}", input);
        boolean result = false;
        if (StringUtils.isNotBlank(input)) {
            Pattern pattern = Pattern.compile(REGEX);
            Matcher matcher = pattern.matcher(input);
            return matcher.find();
        }

        LOGGER.info("input={}, returnObj={}", input, result);
        return result;
    }


    public static void main(String[] args) throws Exception {
        boolean b = false;
        String str = "ADBsbc1234_";
        b = verifyLoginName(str);
        System.out.println(b);


        str = "123sbc_.-[..apk";
        b = verifyApkSoftName(str);
        System.out.println(b);

        str = "123sbc_.-..[]apk";
        b = verifyWithinSpeChar(str);
        System.out.println(b);

    }

}
