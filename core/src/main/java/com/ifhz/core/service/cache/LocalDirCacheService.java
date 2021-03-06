package com.ifhz.core.service.cache;

import java.io.InputStream;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/10
 * Time: 23:40
 */
public interface LocalDirCacheService {

    public String storeTempFile(final InputStream in, String localFileName) throws Exception;

    public String storeFile(final InputStream in, String localFileName) throws Exception;

    public String getLocalFileName(String originFileName);

    public String getExcelTempPath() throws Exception;

    public String storeApkFile(final InputStream in, String localFileName) throws Exception;
}
