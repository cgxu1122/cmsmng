package com.ifhz.core.service.imei;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/7
 * Time: 15:30
 */
public interface ImeiUploadService {

    public boolean processCsvData(String filePath);


    public boolean processZipData(String filePath);
}
