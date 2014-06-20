package com.ifhz.core.service.api.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.adapter.CounterTempLogAdapter;
import com.ifhz.core.base.commons.log.CounterCommonLog;
import com.ifhz.core.base.commons.log.DeviceCommonLog;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.CounterTempLog;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.service.api.ApiUploadService;
import com.ifhz.core.service.api.DataLogApiService;
import com.ifhz.core.service.channel.ChannelInfoService;
import com.ifhz.core.service.model.ModelInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Resource(name = "counterTempLogAdapter")
    private CounterTempLogAdapter counterTempLogAdapter;
    @Resource(name = "dataLogApiService")
    private DataLogApiService dataLogApiService;
    @Resource(name = "channelInfoService")
    private ChannelInfoService channelInfoService;
    @Resource(name = "modelInfoService")
    private ModelInfoService modelInfoService;

    //手机imei|手机ua|手机到达状态
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void saveCounterDataLog(DataLog po) {
        if (po != null) {
            //抛弃非法数据
            if (StringUtils.isBlank(po.getImei()) || po.getActive() == null) {
                LOGGER.info("非法数据，校验不通过 ： {}", JSON.toJSONString(po));
                CounterCommonLog.info("{}", JSON.toJSONString(po));
                return;
            }
            // 查询imei是否已经到达过
            DataLog dataLog = dataLogApiService.getByImei(po.getImei());
            LOGGER.info("DataLog接口数据：{},DataLog数据库数据：{}", JSON.toJSONString(po), JSON.toJSONString(dataLog));
            if (dataLog != null) {
                if (dataLog.getActive() == null && dataLog.getCounterUploadTime() == null) {
                    dataLog.setCounterUploadTime(po.getCounterUploadTime());
                    dataLog.setActive(po.getActive());

                    dataLogApiService.updateCounterData(dataLog);
                }
            } else {
                CounterTempLog counterTempLog = counterTempLogAdapter.queryByImei(po.getImei());
                LOGGER.info("DataLog中没有找到对应数据，CounterTempLog查询数据为：{}", JSON.toJSONString(counterTempLog));
                if (counterTempLog == null) {
                    CounterTempLog tempLog = new CounterTempLog();
                    tempLog.setImei(po.getImei());
                    tempLog.setCreateTime(po.getCounterUploadTime());
                    tempLog.setActive(po.getActive());
                    counterTempLogAdapter.insert(tempLog);
                } else {
                    LOGGER.info("DataLog中没有找到对应数据，CounterTempLog中已经存在。");
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void saveDeviceDataLog(DataLog po) {
        if (po != null) {
            //验证非法数据
            if (po.getChannelId() == null || StringUtils.isBlank(po.getImei()) || po.getProcessTime() == null || StringUtils.isBlank(po.getBatchCode())) {
                LOGGER.info("非法数据，校验不通过 , {}", JSON.toJSONString(po));
                DeviceCommonLog.info("{}", JSON.toJSONString(po));
                return;
            }
            // 通过渠道id 获取渠道组id
            ChannelInfo channelInfo = channelInfoService.getById(po.getChannelId());
            if (channelInfo != null) {
                Long groupId = channelInfo.getGroupId();
                po.setGroupId(groupId);
                po.setModelName(getModelName(groupId, po.getUa()));

                dataLogApiService.insertDeviceData(po);
            } else {
                LOGGER.error("渠道id[{}]--不在系统范围内", po.getChannelId());
            }
        }
    }


    private String getModelName(Long groupId, String ua) {
        String result = "未知";
        if (StringUtils.isNotBlank(ua) && groupId != null) {
            //空格 或者多个连续空格 用下划线代替
            String uaTemp = StringUtils.replace(ua, " +", " ");
            uaTemp = StringUtils.replace(uaTemp, " ", "_");
            ModelInfo modelInfo = modelInfoService.getByGroupIdAndUa(groupId, uaTemp);
            if (modelInfo != null) {
                result = modelInfo.getModelName();
            }
        }

        return result;
    }

    @Override
    public void batchSave(List<DataLog> processLogList) {
        if (CollectionUtils.isNotEmpty(processLogList)) {
            for (DataLog log : processLogList) {
                saveDeviceDataLog(log);
            }
        }
    }
}
