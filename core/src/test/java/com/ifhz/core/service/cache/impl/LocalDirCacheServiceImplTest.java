package com.ifhz.core.service.cache.impl;

import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.test.BaseTest;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class LocalDirCacheServiceImplTest extends BaseTest {

    @Autowired
    private LocalDirCacheService localDirCacheService;

    private static final String Store_APK_Path = MapConfig.getString(GlobalConstants.KEY_STORE_DIR, GlobalConstants.GLOBAL_CONFIG, "/upload");


    @Test
    public void testStoreApkFile() throws Exception {

        InputStream inputStream = new FileInputStream(new File("D:\\ApkMonitor-release.apk"));
        String storeLocalFilePath = localDirCacheService.storeApkFile(inputStream, "ApkMonitor-release.apk");

        String path = StringUtils.replace(storeLocalFilePath, "\\\\+", "\\");
        path = StringUtils.replace(path, "\\", "/");
        path = StringUtils.replace(path, Store_APK_Path, "");
        path = StringUtils.replace(path, "D:/upload", "");

        System.out.println(path);
    }
}