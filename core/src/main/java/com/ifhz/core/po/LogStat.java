package com.ifhz.core.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 0:57
 */
public class LogStat implements Serializable {
    private static final long serialVersionUID = -6324608077983844094L;

    private Long id;
    private String ua;
    private String modelName;
    private Long groupId;
    private Long channelId;
    private String batchCode;
    private String deviceCode;
    private Date processDate;
    private Long laowuId;

    private Long devicePrsDayNum;
    private Long deviceUpdDayNum;
    private Long prsActiveTotalNum;
    private Long prsActiveValidNum;
    private Long prsActiveInvalidNum;
    private Long prsInvalidReplaceNum;
    private Long prsInvalidUninstallNum;
    private Long counterUpdDayNum;

    private String md5Key;
    private String dataLogMd5Key;
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDataLogMd5Key() {
        return dataLogMd5Key;
    }

    public void setDataLogMd5Key(String dataLogMd5Key) {
        this.dataLogMd5Key = dataLogMd5Key;
    }

    //查询条件
    private Date startDate;
    private Date endDate;
    private Integer processType;
    //页面返回值
    private String channelName;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getProcessType() {
        return processType;
    }

    public void setProcessType(Integer processType) {
        this.processType = processType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public Long getLaowuId() {
        return laowuId;
    }

    public void setLaowuId(Long laowuId) {
        this.laowuId = laowuId;
    }

    public Long getDevicePrsDayNum() {
        return devicePrsDayNum;
    }

    public void setDevicePrsDayNum(Long devicePrsDayNum) {
        this.devicePrsDayNum = devicePrsDayNum;
    }

    public Long getDeviceUpdDayNum() {
        return deviceUpdDayNum;
    }

    public void setDeviceUpdDayNum(Long deviceUpdDayNum) {
        this.deviceUpdDayNum = deviceUpdDayNum;
    }

    public Long getPrsActiveTotalNum() {
        return prsActiveTotalNum;
    }

    public void setPrsActiveTotalNum(Long prsActiveTotalNum) {
        this.prsActiveTotalNum = prsActiveTotalNum;
    }

    public Long getPrsActiveValidNum() {
        return prsActiveValidNum;
    }

    public void setPrsActiveValidNum(Long prsActiveValidNum) {
        this.prsActiveValidNum = prsActiveValidNum;
    }

    public Long getPrsActiveInvalidNum() {
        return prsActiveInvalidNum;
    }

    public void setPrsActiveInvalidNum(Long prsActiveInvalidNum) {
        this.prsActiveInvalidNum = prsActiveInvalidNum;
    }

    public Long getPrsInvalidReplaceNum() {
        return prsInvalidReplaceNum;
    }

    public void setPrsInvalidReplaceNum(Long prsInvalidReplaceNum) {
        this.prsInvalidReplaceNum = prsInvalidReplaceNum;
    }

    public Long getPrsInvalidUninstallNum() {
        return prsInvalidUninstallNum;
    }

    public void setPrsInvalidUninstallNum(Long prsInvalidUninstallNum) {
        this.prsInvalidUninstallNum = prsInvalidUninstallNum;
    }

    public Long getCounterUpdDayNum() {
        return counterUpdDayNum;
    }

    public void setCounterUpdDayNum(Long counterUpdDayNum) {
        this.counterUpdDayNum = counterUpdDayNum;
    }
}
