package com.ifhz.core.service.imei.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.imei.DeviceImeiQueryService;
import com.ifhz.core.service.imei.StatImeiQueryService;
import com.ifhz.core.service.imei.bean.ImeiQueryType;
import com.ifhz.core.service.imei.bean.StatImeiRequest;
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


    public List<String> queryImeiListFromLog(StatImeiRequest request) {
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

        return deviceImeiQueryService.getLogImeiList(tableNameList, request);
    }


    public List<String> queryImeiListFromProduct(StatImeiRequest request) {
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

        return deviceImeiQueryService.getProductImeiList(tableNameList, request);
    }

}
