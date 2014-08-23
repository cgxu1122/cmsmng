package com.ifhz.core.service.api;

import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.api.bean.ImeiStatus;
import com.ifhz.core.vo.DeviceResultVo;

import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/20
 * Time: 0:21
 */
public interface ApiUploadService {

    public boolean saveCounterDataLog(DataLog po);

    public ImeiStatus saveDeviceDataLog(DataLog po);

    public Map<String, ImeiStatus> saveMap(Map<String, DataLog> dataLogMap);

    public List<DeviceResultVo> batchSave(Map<String, DataLog> dataLogMap);
}
