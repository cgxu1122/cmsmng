package com.ifhz.core.service.cache.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.io.ByteStreams;
import com.google.common.io.InputSupplier;
import com.google.common.io.OutputSupplier;
import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.utils.FileHandle;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/10
 * Time: 23:41
 */
@Service("localDirCacheService")
public class LocalDirCacheServiceImpl implements LocalDirCacheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDirCacheServiceImpl.class);

    /**
     * 目录的缓存
     */
    private final Cache<String, Boolean> dirs = createDirCache();

    private static final String Store_Temp_Path = MapConfig.getString(GlobalConstants.KEY_STORE_TEMP_DIR, GlobalConstants.GLOBAL_CONFIG, "/data/app/temp");
    private static final String Store_Path = MapConfig.getString(GlobalConstants.KEY_STORE_DIR, GlobalConstants.GLOBAL_CONFIG, "/data/app/store");


    /**
     * 文件存储的父目录,格式为YYYY-MM-dd
     */
    private static final String PARENT_DIR_PATTERN = "yyyy-MM-dd";


    public String preStore(final InputStream in, String localFileName, boolean isTempFile) throws Exception {
        LOGGER.info("localFileName = {},isTempFile={}", localFileName, isTempFile);
        String storePath;
        if (isTempFile) {
            storePath = Store_Temp_Path;
        } else {
            storePath = Store_Path;
        }
        String parentDir = getParentDir();
        if (StringUtils.isNotBlank(parentDir)) {
            storePath = storePath + File.separator + parentDir;
        }
        final File storeFile = new File(storePath, localFileName);
        //确保目录存在
        String key = storeFile.getParentFile().getAbsolutePath();
        dirs.get(key, new DirCacheLoader(key));
        LOGGER.info("Begin write file to {}", storeFile);
        InputSupplier<InputStream> ins = new InputSupplier<InputStream>() {
            @Override
            public InputStream getInput() throws IOException {
                if (in instanceof BufferedInputStream) {
                    return in;
                } else {
                    return new BufferedInputStream(in);
                }
            }
        };
        OutputSupplier<OutputStream> ous = new OutputSupplier<OutputStream>() {
            @Override
            public OutputStream getOutput() throws IOException {
                FileOutputStream fout = new FileOutputStream(storeFile);
                return new BufferedOutputStream(fout);
            }
        };
        ByteStreams.copy(ins, ous);
        LOGGER.info("Finish write file to {}", storeFile);

        return storeFile.getAbsolutePath();
    }

    public String getParentDir() {
        // 只根据时间生成目录，与文件名无关
        return DateFormatUtils.formatDate(new Date(), PARENT_DIR_PATTERN);
    }

    /**
     * 创建一个目录的缓存
     *
     * @return
     */
    private Cache<String, Boolean> createDirCache() {
        return CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(60, TimeUnit.MINUTES).build();
    }

    @Override
    public String storeTempFile(InputStream in, String localFileName) throws Exception {
        return preStore(in, localFileName, true);
    }

    @Override
    public String storeFile(InputStream in, String localFileName) throws Exception {
        return preStore(in, localFileName, false);
    }

    @Override
    public String getLocalFileName(String originFileName) {
        String fileExt = FileHandle.getFileExt(originFileName);
        String prefix = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
        return prefix.toLowerCase() + "." + fileExt.toLowerCase();
    }

    @Override
    public String getExcelTempPath() throws Exception {
        String storePath = Store_Temp_Path;
        String parentDir = getParentDir();
        if (StringUtils.isNotBlank(parentDir)) {
            storePath = storePath + File.separator + parentDir;
        }
        final File storeFile = new File(storePath);
        String key = storeFile.getAbsolutePath();
        dirs.get(key, new DirCacheLoader(key));
        String prefix = StringUtils.replace(UUID.randomUUID().toString(), "-", "");
        return storePath + File.separator + prefix.toLowerCase() + ".xlsx";
    }


    private static class DirCacheLoader implements Callable<Boolean> {

        private String key;

        private DirCacheLoader(String key) {
            this.key = key;
        }

        @Override
        public Boolean call() throws Exception {
            File dir = new File(key);
            if (dir.isDirectory()) {
                return true;
            }
            if (!dir.mkdirs()) {
                throw new RuntimeException("Can't create the parend dir " + key);
            }
            return true;
        }
    }
}
