package com.ifhz.core.service.api.impl;

import com.ifhz.core.po.CounterUploadLog;
import com.ifhz.core.po.DeviceProcessLog;
import com.ifhz.core.service.api.ApiUploadService;
import com.ifhz.core.service.api.CounterUploadLogService;
import com.ifhz.core.service.api.DeviceProcessLogService;
import com.ifhz.core.service.channel.ChannelInfoService;
import com.ifhz.core.service.model.ModelInfoService;
import org.apache.commons.collections.CollectionUtils;
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

    @Resource(name = "counterUploadLogService")
    private CounterUploadLogService counterUploadLogService;
    @Resource(name = "deviceProcessLogService")
    private DeviceProcessLogService deviceProcessLogService;
    @Resource(name = "channelInfoService")
    private ChannelInfoService channelInfoService;
    @Resource(name = "modelInfoService")
    private ModelInfoService modelInfoService;


    //#手机imei|手机ua|渠道id|加工设备编码|批次号|手机加工时间戳|手机到达状态
    @Override
    public void save(CounterUploadLog po) {
        if (po != null) {
            String cidStr = po.getChannelId();
            //TODO 通过渠道id 获取渠道组id

            //TODO ua转换 将空格转换为_

            //TODO 通过ua获取机型名称

            counterUploadLogService.insert(po);
        }
    }

    @Override
    public void save(DeviceProcessLog po) {
        if (po != null) {
            String cidStr = po.getChannelId();
            //TODO 通过渠道id 获取渠道组id

            //TODO ua转换 将空格转换为_

            //TODO 通过ua获取机型名称

            deviceProcessLogService.insert(po);
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
}
