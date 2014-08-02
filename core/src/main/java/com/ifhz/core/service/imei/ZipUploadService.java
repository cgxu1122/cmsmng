package com.ifhz.core.service.imei;

import com.ifhz.core.service.api.bean.ImeiStatus;

import java.util.Date;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/2
 * Time: 16:20
 */
public interface ZipUploadService {

    public Map<ImeiStatus, Integer> processFile(String filePath, Long channelId, Date processDate);
}
