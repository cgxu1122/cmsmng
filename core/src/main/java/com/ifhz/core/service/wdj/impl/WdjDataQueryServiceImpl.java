package com.ifhz.core.service.wdj.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.base.commons.http.client.HttpClientService;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.enums.GroupType;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.api.ApiUploadService;
import com.ifhz.core.service.api.handle.ModelHandler;
import com.ifhz.core.service.wdj.WdjDataQueryService;
import com.ifhz.core.service.wdj.WdjDataResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/24
 * Time: 17:01
 */
@Service("wdjDataQueryService")
public class WdjDataQueryServiceImpl implements WdjDataQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WdjDataQueryServiceImpl.class);

    private final String WDJ_URL = MapConfig.getString("wdj.query.url", GlobalConstants.GLOBAL_CONFIG, "http://115.29.46.109/interface/export?upload_dt=");

    @Resource(name = "commonHttpClientService")
    private HttpClientService httpClientService;

    @Resource(name = "apiUploadService")
    private ApiUploadService apiUploadService;


    public void queryDataList(Date date) {
        String uploadDateStr = DateFormatUtils.formatDate(date, GlobalConstants.DATE_FORMAT_DPT);
        String url = WDJ_URL + uploadDateStr;
        String result = invokeWdjInterface(url);
        LOGGER.info("result={}", result);
        if (StringUtils.isNotBlank(result)) {
            List<WdjDataResult> dataResultList = JSON.parseArray(result, WdjDataResult.class);
            List<DataLog> dataLogList = getDataLogList(dataResultList);
            apiUploadService.batchSave(dataLogList);
        }
    }


    private List<DataLog> getDataLogList(List<WdjDataResult> dataResultList) {
        Map<String, DataLog> map = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(dataResultList)) {
            for (WdjDataResult dataResult : dataResultList) {
                LOGGER.info("WdjDataResult={}", JSON.toJSONString(dataResult));
                DataLog dataLog = translateDataLog(dataResult);
                if (dataLog != null && StringUtils.isNotBlank(dataLog.getImei())) {
                    if (!map.containsKey(dataLog.getImei())) {
                        map.put(dataLog.getImei(), dataLog);
                    }
                }
            }
        }
        if (MapUtils.isNotEmpty(map)) {
            return Lists.newArrayList(map.values());
        }

        return Lists.<DataLog>newArrayList();
    }

    private DataLog translateDataLog(WdjDataResult dataResult) {
        try {
            if (dataResult != null) {
                if (dataResult.isResult()) {
                    DataLog dataLog = new DataLog();
                    dataLog.setImei(dataResult.getImei());
                    dataLog.setBatchCode(dataResult.getBatchNo());
                    dataLog.setDeviceCode("豌豆荚");
                    dataLog.setGroupId(GroupType.QT.VALUE);
                    dataLog.setUa(ModelHandler.translateUa(dataResult.getModel()));
                    dataLog.setDeviceUploadTime(new Date());
                    if (StringUtils.isNotBlank(dataResult.getDeptId())) {
                        dataLog.setChannelId(Long.parseLong(dataResult.getDeptId()));
                    }
                    if (StringUtils.isNotBlank(dataResult.getInstallDate())) {
                        dataLog.setProcessTime(DateFormatUtils.parse(dataResult.getInstallDate(), GlobalConstants.DATE_FORMAT_DPT));
                    }

                    return dataLog;
                }
            }
        } catch (Exception e) {
            LOGGER.info("translateDataLog error", e);
        }
        return null;
    }


    private String invokeWdjInterface(String url) {
        try {
            LOGGER.info("url={}", url);
            return httpClientService.sendGet(url, null, null);
        } catch (IOException e) {
            LOGGER.error("invokeWdjInterface error", e);
        }
        return null;
    }


}
