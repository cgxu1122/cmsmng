package com.ifhz.core.service.api.impl;

import com.ifhz.core.po.*;
import com.ifhz.core.service.api.ApiUploadService;
import com.ifhz.core.service.api.CounterFailLogService;
import com.ifhz.core.service.api.CounterUploadLogService;
import com.ifhz.core.service.api.DeviceProcessLogService;
import com.ifhz.core.service.channel.ChannelInfoService;
import com.ifhz.core.service.model.ModelInfoService;
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
 * Date: 2014/5/20
 * Time: 0:22
 */
@Service("apiUploadService")
public class ApiUploadServiceImpl implements ApiUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiUploadServiceImpl.class);
    @Resource(name = "counterUploadLogService")
    private CounterUploadLogService counterUploadLogService;
    @Resource(name = "deviceProcessLogService")
    private DeviceProcessLogService deviceProcessLogService;
    @Resource(name = "channelInfoService")
    private ChannelInfoService channelInfoService;
    @Resource(name = "modelInfoService")
    private ModelInfoService modelInfoService;
    @Resource(name = "counterFailLogService")
    private CounterFailLogService counterFailLogService;


    //#手机imei|手机ua|渠道id|加工设备编码|批次号|手机加工时间戳|手机到达状态
    @Override
    public void save(CounterUploadLog po) {
        if (po != null) {
            //脏数据进入脏数据表
            if (StringUtils.isBlank(po.getChannelId()) || StringUtils.isBlank(po.getProcessTime()) || StringUtils.isBlank(po.getBatchCode())) {
                if (StringUtils.isNotBlank(po.getImei())) {//必须有imei
                    CounterFailLog failLog = translate(po);
                    counterFailLogService.insert(failLog);
                }
            }
            // 通过渠道id 获取渠道组id
            ChannelInfo channelInfo = channelInfoService.getById(Long.parseLong(po.getChannelId()));
            if (channelInfo != null) {
                String modelName = "未知";
                Long groupId = channelInfo.getGroupId();
                String usTemp = StringUtils.replace(po.getUa(), " +", " ");
                po.setUa(StringUtils.replace(usTemp, " ", "_"));
                if (StringUtils.isNotBlank(po.getUa())) {
                    ModelInfo modelInfo = modelInfoService.getByGroupIdAndUa(groupId, po.getUa());
                    modelName = modelInfo.getModelName();
                }
                po.setGroupId(groupId);
                po.setModelName(modelName);

                counterUploadLogService.insert(po);
            } else {
                LOGGER.error("渠道id[{}]--不在系统范围内", po.getChannelId());
            }
        }
    }

    @Override
    public void save(DeviceProcessLog po) {
        if (po != null) {
            //抛弃脏数据
            if (StringUtils.isBlank(po.getChannelId()) || StringUtils.isBlank(po.getProcessTime()) || StringUtils.isBlank(po.getBatchCode())) {
                LOGGER.info("DeviceProcessLog validate error , {}", po);
                return;
            }
            // 通过渠道id 获取渠道组id
            ChannelInfo channelInfo = channelInfoService.getById(Long.parseLong(po.getChannelId()));
            if (channelInfo != null) {
                String modelName = "未知";
                Long groupId = channelInfo.getGroupId();
                String usTemp = StringUtils.replace(po.getUa(), " +", " ");
                po.setUa(StringUtils.replace(usTemp, " ", "_"));
                if (StringUtils.isNotBlank(po.getUa())) {
                    ModelInfo modelInfo = modelInfoService.getByGroupIdAndUa(groupId, po.getUa());
                    modelName = modelInfo.getModelName();
                }
                po.setGroupId(groupId);
                po.setModelName(modelName);

                deviceProcessLogService.insert(po);
            } else {
                LOGGER.error("渠道id[{}]--不在系统范围内", po.getChannelId());
            }
        }
    }


    @Override
    public void batchSave(List<DeviceProcessLog> processLogList) {
        if (CollectionUtils.isNotEmpty(processLogList)) {
            for (DeviceProcessLog log : processLogList) {
                save(log);
            }
        }
    }


    private CounterFailLog translate(CounterUploadLog source) {
        CounterFailLog ret = new CounterFailLog();
        ret.setCreateTime(ret.getCreateTime());
        ret.setImei(source.getImei());
        ret.setBatchCode(source.getBatchCode());
        ret.setDeviceCode(source.getDeviceCode());
        ret.setChannelId(source.getChannelId());
        ret.setProcessTime(source.getProcessTime());
        ret.setUa(source.getUa());

        return ret;
    }
}
