package com.ifhz.core.service.imei.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.imei.DeviceImeiQueryService;
import com.ifhz.core.service.imei.StatImeiQueryService;
import com.ifhz.core.service.imei.bean.ImeiQueryType;
import com.ifhz.core.service.imei.bean.StatImeiRequest;
import com.ifhz.core.service.imei.bean.StatImeiResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 16:38
 */
@Service("statImeiQueryService")
public class StatImeiQueryServiceImpl implements StatImeiQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatImeiQueryServiceImpl.class);

    @Resource(name = "splitTableService")
    private SplitTableService splitTableService;

    @Resource(name = "deviceImeiQueryService")
    private DeviceImeiQueryService deviceImeiQueryService;


    public List<StatImeiResult> queryImeiListFromLog(StatImeiRequest request) {
        Preconditions.checkArgument(request != null, "StatImeiRequest must be not null");
        Preconditions.checkArgument(StringUtils.isNotBlank(request.getUa()), "StatImeiRequest.UA must be not null");
        Preconditions.checkArgument(request.getProcessDate() != null, "StatImeiRequest.processDate must be not null");
        Preconditions.checkArgument(request.getChannelId() != null, "StatImeiRequest.channelId must be not null");
        List<String> tableNameList = Lists.newArrayList();
        if (request.getType() == ImeiQueryType.Day_Device_Process) {
            tableNameList = splitTableService.getListFromDate2Now(request.getProcessDate());
        } else if (request.getType() == ImeiQueryType.Day_Device_Upload) {
            String tableName = splitTableService.getCurrentTableName(request.getProcessDate());
            tableNameList = Lists.newArrayList(tableName);
        } else if (request.getType() == ImeiQueryType.Day_Counter_Upload) {
            tableNameList = splitTableService.getListFromDate2Now(request.getProcessDate());
        }
        LOGGER.info("StatImeiRequest={},tableNameList={}", JSON.toJSONString(request), JSON.toJSONString(tableNameList));
        List<String> imeiList = deviceImeiQueryService.getLogImeiList(tableNameList, request);
        List<StatImeiResult> resultList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(imeiList)) {
            for (String imei : imeiList) {
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
        LOGGER.info("returnObj={}", JSON.toJSONString(resultList));
        return resultList;
    }


    public List<StatImeiResult> queryImeiListFromProduct(StatImeiRequest request) {
        Preconditions.checkArgument(request != null, "StatImeiRequest must be not null");
        Preconditions.checkArgument(StringUtils.isNotBlank(request.getUa()), "StatImeiRequest.UA must be not null");
        Preconditions.checkArgument(request.getProcessDate() != null, "StatImeiRequest.processDate must be not null");
        Preconditions.checkArgument(request.getProductId() != null, "StatImeiRequest.productId must be not null");
        Preconditions.checkArgument(request.getGroupId() != null, "StatImeiRequest.groupId must be not null");

        List<String> tableNameList = Lists.newArrayList();
        if (request.getType() == ImeiQueryType.Day_Device_Process) {
            tableNameList = splitTableService.getListFromDate2Now(request.getProcessDate());
        } else if (request.getType() == ImeiQueryType.Day_Device_Upload) {
            String tableName = splitTableService.getCurrentTableName(request.getProcessDate());
            tableNameList = Lists.newArrayList(tableName);
        } else if (request.getType() == ImeiQueryType.Day_Counter_Upload) {
            tableNameList = splitTableService.getListFromDate2Now(request.getProcessDate());
        }
        LOGGER.info("StatImeiRequest={},tableNameList={}", JSON.toJSONString(request), JSON.toJSONString(tableNameList));
        List<String> imeiList = deviceImeiQueryService.getProductImeiList(tableNameList, request);
        List<StatImeiResult> resultList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(imeiList)) {
            for (String imei : imeiList) {
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
        LOGGER.info("returnObj={}", JSON.toJSONString(resultList));

        return resultList;
    }

}