package com.ifhz.core.service.api.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ifhz.core.adapter.CounterTempLogAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.commons.log.CounterCommonLog;
import com.ifhz.core.base.commons.log.DeviceCommonLog;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.constants.TempLogType;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.CounterTempLog;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.api.ApiUploadService;
import com.ifhz.core.service.api.DataLogApiService;
import com.ifhz.core.service.api.bean.ImeiStatus;
import com.ifhz.core.service.api.handle.ModelHandler;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import com.ifhz.core.service.stat.*;
import com.ifhz.core.service.stat.constants.CounterActive;
import com.ifhz.core.vo.DeviceResultVo;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    @Resource(name = "statCounterService")
    private StatCounterService statCounterService;
    @Resource(name = "statDeviceService")
    private StatDeviceService statDeviceService;
    @Resource(name = "logInstallStatService")
    private LogInstallStatService logInstallStatService;
    @Resource(name = "productInstallStatService")
    private ProductInstallStatService productInstallStatService;
    @Resource
    private LogArriveStatService logArriveStatService;
    @Resource
    private ProductArriveStatService productArriveStatService;

    //手机imei|手机ua|手机到达状态
    @Override
    @Log
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean saveCounterDataLog(DataLog po) {
        if (po != null) {
            //抛弃非法数据
            if (StringUtils.isBlank(po.getImei()) || po.getActive() == null) {
                LOGGER.info("非法数据，校验不通过 ： {}", JSON.toJSONString(po));
                CounterCommonLog.info("{}", JSON.toJSONString(po));
                return true;
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
                    CounterTempLog counterTempLog = counterTempLogAdapter.queryByImei(po.getImei());
                    LOGGER.info("CounterTempLog中没有找到对应数据，CounterTempLog={},DataLog={}", JSON.toJSONString(counterTempLog), JSON.toJSONString(po));
                    if (counterTempLog == null) {
                        CounterTempLog tempLog = new CounterTempLog();
                        tempLog.setImei(po.getImei());
                        tempLog.setUa(ModelHandler.translateUa(po.getUa()));
                        tempLog.setCreateTime(po.getCounterUploadTime());
                        tempLog.setActive(po.getActive());
                        tempLog.setType(type.value);

                        int num = counterTempLogAdapter.insert(tempLog);
                        if (num == 1) {
                            LOGGER.info("CounterTempLog保存成功-DataLog={}", JSON.toJSONString(dataLog));
                            if (type == TempLogType.UnStat) {
                                //统计到达数据
                                logInstallStatService.statLogInstallForArrive(dataLog);
                                logArriveStatService.statLogArrive(dataLog);
                                productInstallStatService.statProductArrive(dataLog);
                                productArriveStatService.statProductArrive(dataLog);
                                counterTempLogAdapter.update(dataLog.getImei(), TempLogType.Done.value);
                            }
                        } else {
                            LOGGER.info("CounterTempLog保存失败-DataLog={}", JSON.toJSONString(po));
                            return false;
                        }
                    } else {
                        LOGGER.info("CounterTempLog中已经存在,忽略此数据-DataLog={}", JSON.toJSONString(po));
                    }
                } else {
                    LOGGER.info("计数器数据已经到达过，程序结束");
                }
            }
        }

        return true;
    }

    @Override
    @Log
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ImeiStatus saveDeviceDataLog(DataLog po) {
        LOGGER.info("执行DataLog保存操作 --- 开始");
        if (po != null) {
            //验证非法数据
            if (po.getChannelId() == null || StringUtils.isBlank(po.getImei()) || po.getProcessTime() == null || StringUtils.isBlank(po.getBatchCode())) {
                LOGGER.info("非法数据，校验不通过 , {}", JSON.toJSONString(po));
                DeviceCommonLog.info("{}", JSON.toJSONString(po));
                return ImeiStatus.Invalid;
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
                    po.setActive(CounterActive.None.value);
                    LOGGER.info("dataLogApiService.insertDeviceData");
                    int num = dataLogApiService.insertDeviceData(po);
                    if (num == 1) {
                        logInstallStatService.statLogInstall(po);
                        productInstallStatService.statProductInstall(po);
                        return ImeiStatus.Success;
                    } else {
                        return ImeiStatus.Failure;
                    }
                } else {
                    LOGGER.info("渠道id[{}]--不在系统范围内", po.getChannelId());
                    return ImeiStatus.Invalid;
                }
            } else {
                LOGGER.info("{} 记录已经存在", po.getImei());
                return ImeiStatus.Repeat;
            }
        }
        LOGGER.info("执行DataLog插入操作 --- 结束");
        return ImeiStatus.Failure;
    }

    @Override
    @Log
    public Map<String, ImeiStatus> saveMap(Map<String, DataLog> dataLogMap) {
        Map<String, ImeiStatus> result = Maps.newHashMap();
        if (MapUtils.isNotEmpty(dataLogMap)) {
            for (Map.Entry<String, DataLog> entry : dataLogMap.entrySet()) {
                String key = entry.getKey();
                DataLog dataLog = entry.getValue();
                try {
                    ImeiStatus status = this.saveDeviceDataLog(dataLog);
                    result.put(key, status);
                } catch (Exception e) {
                    result.put(key, ImeiStatus.Failure);
                    LOGGER.error("saveDeviceDataLog error", e);
                }
            }
        }
        return result;
    }

    @Override
    @Log
    public List<DeviceResultVo> batchSave(Map<String, DataLog> dataLogMap) {
        List<DeviceResultVo> result = Lists.newArrayList();
        if (MapUtils.isNotEmpty(dataLogMap)) {
            Map<String, ImeiStatus> tempMap = saveMap(dataLogMap);
            for (Map.Entry<String, ImeiStatus> entry : tempMap.entrySet()) {
                String key = entry.getKey();
                ImeiStatus value = entry.getValue();
                DeviceResultVo vo = new DeviceResultVo();
                vo.setId(key);
                if (ImeiStatus.Failure == value) {
                    vo.setActive(false);
                } else {
                    vo.setActive(true);
                }
                result.add(vo);
            }
        }

        return result;
    }


    private class StatRunnable implements Runnable {

        private final DataLog dataLog;

        private StatRunnable(DataLog dataLog) {
            this.dataLog = dataLog;
        }

        @Override
        @Log
        public void run() {
            try {
                statCounterService.updateStat(dataLog);
            } catch (Exception e) {
                LOGGER.error("updateStat error:{}", JSON.toJSONString(dataLog));
                LOGGER.error("updateStat error", e);
            }
        }
    }

    private class StatDeviceRunnable implements Runnable {

        private final DataLog dataLog;

        private StatDeviceRunnable(DataLog dataLog) {
            this.dataLog = dataLog;
        }

        @Override
        @Log
        public void run() {
            try {
                statDeviceService.updateStat(dataLog);
            } catch (Exception e) {
                LOGGER.error("statDeviceService updateStat error:{}", JSON.toJSONString(dataLog));
                LOGGER.error("statDeviceService updateStat error", e);
            }
        }
    }
}
