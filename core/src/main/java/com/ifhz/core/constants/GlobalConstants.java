package com.ifhz.core.constants;

import com.google.common.collect.ImmutableMap;
import com.ifhz.core.base.commons.MapConfig;

/**
 * 类描述
 *
 * @author chenggang.xu@qunar.com created at 13-10-22 下午12:45
 * @version 1.0.0
 */
public final class GlobalConstants {
    /**
     * 项目内默认使用的字符集
     */
    public static final String DEFAULT_CHARSET = "UTF-8";
    /**
     * 全局配置
     */
    public static final ImmutableMap<String, String> GLOBAL_CONFIG = MapConfig.pasreConf("global_config.xml");

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT_ZH = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String DATE_FORMAT_DPT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DPT_ZH = "yyyy年MM月dd日";

    private GlobalConstants() {
    }
}