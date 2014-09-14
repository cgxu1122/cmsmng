package com.ifhz.core.service.imei.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.imei.InstallImeiQueryService;
import com.ifhz.core.service.imei.StatImeiService;
import com.ifhz.core.service.imei.bean.ImeiQueryType;
import com.ifhz.core.service.imei.bean.StatImeiRequest;
import com.ifhz.core.service.imei.bean.StatImeiResult;
import com.ifhz.core.service.stat.handle.DateHandler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/14
 * Time: 10:06
 */
@Service
public class StatImeiServiceImpl implements StatImeiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatImeiServiceImpl.class);

    @Resource(name = "splitTableService")
    private SplitTableService splitTableService;
    @Resource
    private InstallImeiQueryService installImeiQueryService;

    @Override
    @Log
    public List<StatImeiResult> queryImeiList(StatImeiRequest request) {
        Preconditions.checkArgument(request != null, "StatImeiRequest must be not null");
        Preconditions.checkArgument(StringUtils.isNotBlank(request.getUa()), "StatImeiRequest.UA must be not null");
        Preconditions.checkArgument(request.getProcessDate() != null, "StatImeiRequest.processDate must be not null");
        List<String> tableNameList = splitTableService.getListFromDate2Now(request.getProcessDate());
        List<Map<String, Object>> paramList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(tableNameList)) {
            for (String tableName : tableNameList) {
                Map<String, Object> param = getMapParams(tableName, request);
                paramList.add(param);
            }
        }
        List<String> imeiList = null;
        if (request.getType() == ImeiQueryType.Log_Install) {
            imeiList = installImeiQueryService.getLogInstall(paramList);
        } else if (request.getType() == ImeiQueryType.Log_Install_Arrive) {
            imeiList = installImeiQueryService.getLogInstallArrive(paramList);
        } else if (request.getType() == ImeiQueryType.Log_Arrive) {
            imeiList = installImeiQueryService.getLogArrive(paramList);
        } else if (request.getType() == ImeiQueryType.Log_Arrive_Temp) {
            imeiList = installImeiQueryService.getLogArriveTemp(paramList, request.getTotalCount());
        } else if (request.getType() == ImeiQueryType.Product_Install) {
            imeiList = installImeiQueryService.getProductInstall(paramList);
        } else if (request.getType() == ImeiQueryType.Product_Install_Arrive) {
            imeiList = installImeiQueryService.getProductInstallArrive(paramList);
        } else if (request.getType() == ImeiQueryType.Product_Arrive) {
            imeiList = installImeiQueryService.getProductArrive(paramList);
        } else if (request.getType() == ImeiQueryType.Product_Arrive_Temp) {
            imeiList = installImeiQueryService.getProductArriveTemp(paramList, request.getTotalCount());
        }
        List<StatImeiResult> resultList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(imeiList)) {
            int index;
            if (request.isExportQuery()) {
                index = imeiList.size();
            } else {
                index = imeiList.size() > 1000 ? 1000 : imeiList.size();
            }
            if (index > 0) {
                for (int i = 0; i < index; i++) {
                    String imei = imeiList.get(i);
                    StatImeiResult result = new StatImeiResult();
                    result.setChannelName(request.getChannelName());
                    result.setDeviceCode(request.getDeviceCode());
                    result.setProcessDate(request.getProcessDate());
                    result.setGroupName(request.getGroupName());
                    result.setProductName(request.getProductName());
                    result.setModelName(request.getModelName());

                    result.setImei(imei);
                    resultList.add(result);
                }
            }
        }
        LOGGER.info("returnObj.size={}", JSON.toJSONString(resultList.size()));
        return resultList;
    }


    @Log
    private Map<String, Object> getMapParams(String tableName, StatImeiRequest request) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("tableName", tableName);
        params.put("ua", request.getUa());
        params.put("startTime", DateHandler.getStartTime(request.getProcessDate()));
        params.put("endTime", DateHandler.getEndTime(request.getProcessDate()));
        params.put("deviceCode", request.getDeviceCode());
        params.put("productId", request.getProductId());
        params.put("channelId", request.getChannelId());
        params.put("groupId", request.getGroupId());
        params.put("type", request.getType().value);
        params.put("totalCount", request.getTotalCount());
        if (request.getActive() != null) {
            params.put("activeType", request.getActive().value);
        }

        return params;
    }
}
