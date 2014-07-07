package com.ifhz.core.service.imei;

import java.io.File;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/7
 * Time: 15:30
 */
public interface ImeiUploadService {

    public boolean processCsvData(File csvFile);


    public boolean processZipData(File zipFile);
}
