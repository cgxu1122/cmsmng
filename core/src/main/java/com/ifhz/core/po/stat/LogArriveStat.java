package com.ifhz.core.po.stat;

import java.io.Serializable;
import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/23
 * Time: 15:23
 */
public class LogArriveStat implements Serializable {
    private static final long serialVersionUID = -1433623945960045448L;
    private Long id;
    private String ua;
    private Long groupId;
    private Long channelId;
    private Date statDate;
    private Long laowuId;
    private Date createTime;


    /**
     * 设备当天到达总数
     */
    private Long totalNum = 0L;
    /**
     * 设备当天到达-有效总数
     */
    private Long validNum = 0L;
    /**
     * 设备当天到达-无效总数
     */
    private Long invalidNum = 0L;
    /**
     * 设备当天到达-替换总数
     */
    private Long replaceNum = 0L;
    /**
     * 设备当天到达-卸载总数
     */
    private Long uninstallNum = 0L;
    /**
     * 设备当天到达-无效卸载+替换总数
     */
    private Long unAndReNum = 0L;

    /**
     * 设备当天到达-扣量有效总数
     */
    private Long deductionValidNum = 0L;
    /**
     * 设备当天到达-扣量无效总数
     */
    private Long deductionInvalidNum = 0L;


    private String md5Key;
    private Integer version;


    private String modelName;
    private String channelName;
    private String groupName;
    private String channelIdCondition;
    private Date startDate;
    private Date endDate;

    public Long getDeductionValidNum() {
        return deductionValidNum;
    }

    public void setDeductionValidNum(Long deductionValidNum) {
        this.deductionValidNum = deductionValidNum;
    }

    public Long getDeductionInvalidNum() {
        return deductionInvalidNum;
    }

    public void setDeductionInvalidNum(Long deductionInvalidNum) {
        this.deductionInvalidNum = deductionInvalidNum;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public Date getStatDate() {
        return statDate;
    }

    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }

    public Long getLaowuId() {
        return laowuId;
    }

    public void setLaowuId(Long laowuId) {
        this.laowuId = laowuId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Long getValidNum() {
        return validNum;
    }

    public void setValidNum(Long validNum) {
        this.validNum = validNum;
    }

    public Long getInvalidNum() {
        return invalidNum;
    }

    public void setInvalidNum(Long invalidNum) {
        this.invalidNum = invalidNum;
    }

    public Long getReplaceNum() {
        return replaceNum;
    }

    public void setReplaceNum(Long replaceNum) {
        this.replaceNum = replaceNum;
    }

    public Long getUninstallNum() {
        return uninstallNum;
    }

    public void setUninstallNum(Long uninstallNum) {
        this.uninstallNum = uninstallNum;
    }

    public Long getUnAndReNum() {
        return unAndReNum;
    }

    public void setUnAndReNum(Long unAndReNum) {
        this.unAndReNum = unAndReNum;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getChannelIdCondition() {
        return channelIdCondition;
    }

    public void setChannelIdCondition(String channelIdCondition) {
        this.channelIdCondition = channelIdCondition;
    }

    @Override
    public String toString() {
        return "LogArriveStat{" +
                "id=" + id +
                ", ua='" + ua + '\'' +
                ", groupId=" + groupId +
                ", channelId=" + channelId +
                ", statDate=" + statDate +
                ", laowuId=" + laowuId +
                ", createTime=" + createTime +
                ", totalNum=" + totalNum +
                ", validNum=" + validNum +
                ", invalidNum=" + invalidNum +
                ", replaceNum=" + replaceNum +
                ", uninstallNum=" + uninstallNum +
                ", unAndReNum=" + unAndReNum +
                ", deductionValidNum=" + deductionValidNum +
                ", deductionInvalidNum=" + deductionInvalidNum +
                ", md5Key='" + md5Key + '\'' +
                ", version=" + version +
                ", modelName='" + modelName + '\'' +
                ", channelName='" + channelName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", channelIdCondition='" + channelIdCondition + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
