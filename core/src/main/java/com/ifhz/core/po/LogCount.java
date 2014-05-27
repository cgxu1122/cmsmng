package com.ifhz.core.po;

import java.util.Date;

/**
 * Created by lm on 14-5-15.
 * liming 流水统计表PO
 */
public class LogCount {
    private Long id;
    private String modleName;
    private Long channelId;
    private String deviceCode;
    private Long groupId;
    private String batchCode;
    private Date countTime;
    private String processKey;
    private Long laowuId;
    private Long processDayCount;
    private Long deviceUploadDayCount;
    private Long counterUploadDayCount;
    private Long allCount;
    private Long activeCount;
    private Long nonActiveCount;
    private Long nonActiveUninstallCount;
    private Long nonActiveReplaceCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModleName() {
        return modleName;
    }

    public void setModleName(String modleName) {
        this.modleName = modleName;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Date getCountTime() {
        return countTime;
    }

    public void setCountTime(Date countTime) {
        this.countTime = countTime;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public Long getLaowuId() {
        return laowuId;
    }

    public void setLaowuId(Long laowuId) {
        this.laowuId = laowuId;
    }

    public Long getProcessDayCount() {
        return processDayCount;
    }

    public void setProcessDayCount(Long processDayCount) {
        this.processDayCount = processDayCount;
    }

    public Long getDeviceUploadDayCount() {
        return deviceUploadDayCount;
    }

    public void setDeviceUploadDayCount(Long deviceUploadDayCount) {
        this.deviceUploadDayCount = deviceUploadDayCount;
    }

    public Long getCounterUploadDayCount() {
        return counterUploadDayCount;
    }

    public void setCounterUploadDayCount(Long counterUploadDayCount) {
        this.counterUploadDayCount = counterUploadDayCount;
    }

    public Long getAllCount() {
        return allCount;
    }

    public void setAllCount(Long allCount) {
        this.allCount = allCount;
    }

    public Long getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(Long activeCount) {
        this.activeCount = activeCount;
    }

    public Long getNonActiveCount() {
        return nonActiveCount;
    }

    public void setNonActiveCount(Long nonActiveCount) {
        this.nonActiveCount = nonActiveCount;
    }

    public Long getNonActiveUninstallCount() {
        return nonActiveUninstallCount;
    }

    public void setNonActiveUninstallCount(Long nonActiveUninstallCount) {
        this.nonActiveUninstallCount = nonActiveUninstallCount;
    }

    public Long getNonActiveReplaceCount() {
        return nonActiveReplaceCount;
    }

    public void setNonActiveReplaceCount(Long nonActiveReplaceCount) {
        this.nonActiveReplaceCount = nonActiveReplaceCount;
    }
}

