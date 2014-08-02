package com.ifhz.core.constants;

import com.ifhz.core.base.commons.MapConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/2
 * Time: 11:07
 */
public final class InitConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitConfig.class);

    private static final String Store_Path = MapConfig.getString(GlobalConstants.KEY_STORE_DIR, GlobalConstants.GLOBAL_CONFIG, "/store/app/perm");
    private static final String Store_Temp_Path = MapConfig.getString(GlobalConstants.KEY_STORE_TEMP_DIR, GlobalConstants.GLOBAL_CONFIG, "/store/app/temp");
    private static final String Store_APK_Path = MapConfig.getString(GlobalConstants.KEY_STORE_APK_DIR, GlobalConstants.GLOBAL_CONFIG, "/upload");


    static {
        if (!initDir(Store_Path)) {
            throw new RuntimeException(" create Store_Path dir failure");
        }
        if (!initDir(Store_Temp_Path)) {
            throw new RuntimeException(" create Store_Path dir failure");
        }
        if (!initDir(Store_APK_Path)) {
            throw new RuntimeException(" create Store_Path dir failure");
        }
    }


    public static boolean initDir(String path) throws RuntimeException {
        File dir = new File(path);
        if (dir.isDirectory()) {
            return true;
        }
        if (!dir.mkdirs()) {
            throw new RuntimeException("Can't create the parent dir " + path);
        }
        return true;
    }
}
