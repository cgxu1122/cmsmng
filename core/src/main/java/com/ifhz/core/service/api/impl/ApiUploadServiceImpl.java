package com.ifhz.core.service.api.impl;

import com.alibaba.fastjson.JSON;
import com.ifhz.core.adapter.CounterTempLogAdapter;
import com.ifhz.core.base.commons.log.CounterCommonLog;
import com.ifhz.core.base.commons.log.DeviceCommonLog;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.constants.TempLogType;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.CounterTempLog;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.service.api.ApiUploadService;
import com.ifhz.core.service.api.DataLogApiService;
import com.ifhz.core.service.api.handle.ModelHandler;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import com.ifhz.core.service.cache.ModelInfoCacheService;
import com.ifhz.core.service.stat.StatCounterService;
import com.ifhz.core.service.stat.constants.CounterActive;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
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
    @Resource(name = "channelInfoCacheService")
    private ChannelInfoCacheService channelInfoCacheService;
    @Resource(name = "modelInfoCacheService")
    private ModelInfoCacheService modelInfoCacheService;
    @Resource(name = "statCounterService")
    private StatCounterService statCounterService;
    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

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
            TempLogType type = TempLogType.UnDo;
            LOGGER.info("DataLog接口数据：{},DataLog数据库数据：{}", JSON.toJSONString(po), JSON.toJSONString(dataLog));
            if (dataLog != null) {
                if (dataLog.getCounterUploadTime() == null) {
                    dataLog.setCounterUploadTime(po.getCounterUploadTime());
                    dataLog.setActive(po.getActive());
                    LOGGER.info("计数器数据执行更新操作");
                    dataLogApiService.updateCounterData(dataLog);
                    type = TempLogType.UnStat;
                } else {
                    LOGGER.info("计数器数据已经到达过，程序结束");
                }
            }
            CounterTempLog counterTempLog = counterTempLogAdapter.queryByImei(po.getImei());
            LOGGER.info("CounterTempLog中没有找到对应数据，CounterTempLog查询数据为：{}", JSON.toJSONString(counterTempLog));
            if (counterTempLog == null) {
                CounterTempLog tempLog = new CounterTempLog();
                tempLog.setImei(po.getImei());
                tempLog.setUa(ModelHandler.translateUa(po.getUa()));
                tempLog.setCreateTime(po.getCounterUploadTime());
                tempLog.setActive(po.getActive());
                tempLog.setType(type.value);

                counterTempLogAdapter.insert(tempLog);
            } else {
                LOGGER.info("CounterTempLog中已经存在,忽略此数据");
            }

            if (type == TempLogType.UnStat) {
                //异步统计到达数据
                statCounterService.updateStat(dataLog);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void saveDeviceDataLog(DataLog po) {
        LOGGER.info("执行DataLog保存操作 --- 开始");
        if (po != null) {
            //验证非法数据
            if (po.getChannelId() == null || StringUtils.isBlank(po.getImei()) || po.getProcessTime() == null) {
                LOGGER.info("非法数据，校验不通过 , {}", JSON.toJSONString(po));
                DeviceCommonLog.info("{}", JSON.toJSONString(po));
                return;
            }
            // 查询imei是否已经到达过
            DataLog dataLog = dataLogApiService.getByImei(po.getImei());
            LOGGER.info("DataLog接口数据：{},DataLog数据库数据：{}", JSON.toJSONString(po), JSON.toJSONString(dataLog));
            if (dataLog == null) {
                // 通过渠道id 获取渠道组id
                ChannelInfo channelInfo = channelInfoCacheService.getByChannelId(po.getChannelId());
                if (channelInfo != null) {
                    Long groupId = channelInfo.getGroupId();
                    po.setGroupId(groupId);
                    if (StringUtils.isBlank(po.getUa())) {
                        po.setUa(GlobalConstants.DEFAULT_UA);
                    } else {
                        po.setUa(ModelHandler.translateUa(po.getUa()));
                    }
                    if (po.getBatchCode() == null) {
                        po.setBatchCode("");
                    }
                    if (StringUtils.isBlank(po.getDeviceCode())) {
                        po.setDeviceCode("未知");
                    }
                    po.setActive(CounterActive.None.value);
                    LOGGER.info("dataLogApiService.insertDeviceData");
                    dataLogApiService.insertDeviceData(po);
                } else {
                    LOGGER.info("渠道id[{}]--不在系统范围内", po.getChannelId());
                }
            } else {
                LOGGER.info("{} 记录已经存在", po.getImei());
            }
        }
        LOGGER.info("执行DataLog插入操作 --- 结束");
    }


    private String getModelName(Long groupId, String ua) {
        String result = "未知";
        if (StringUtils.isNotBlank(ua) && groupId != null) {
            //空格 或者多个连续空格 用下划线代替
            String uaTemp = ModelHandler.translateUa(ua);
            ModelInfo modelInfo = modelInfoCacheService.getByUaAndGrouId(uaTemp, groupId);
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
                try {
                    saveDeviceDataLog(log);
                } catch (Exception e) {
                    LOGGER.error("saveDeviceDataLog error", e);
                    DeviceCommonLog.info("{}", JSON.toJSONString(log));
                }
            }
        }
    }


    private class StatRunnable implements Runnable {

        private final DataLog dataLog;

        private StatRunnable(DataLog dataLog) {
            this.dataLog = dataLog;
        }

        @Override
        public void run() {
            try {
                statCounterService.updateStat(dataLog);
            } catch (Exception e) {
                LOGGER.error("updateStat error:{}", JSON.toJSONString(dataLog));
                LOGGER.error("updateStat error", e);
            }
        }
    }
}
