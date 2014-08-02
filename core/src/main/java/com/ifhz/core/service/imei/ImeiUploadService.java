package com.ifhz.core.service.imei;

import com.ifhz.core.service.api.bean.ImeiStatus;

import java.util.Date;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/7
 * Time: 15:30
 */
public interface ImeiUploadService {

    public Map<ImeiStatus, Integer> processCsvData(String filePath, Long channelId, Date processDate);

    public Map<ImeiStatus, Integer> processZipData(String filePath, Long channelId, Date processDate);

    public void asyncProcessCsvData(String filePath, Long channelId, Date processDate);
}
